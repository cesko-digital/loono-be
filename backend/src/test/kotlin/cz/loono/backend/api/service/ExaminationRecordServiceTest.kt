package cz.loono.backend.api.service

import cz.loono.backend.api.dto.ExaminationRecordDto
import cz.loono.backend.api.dto.ExaminationStatusDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.model.ExaminationRecord
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.db.repository.ExaminationRecordRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
internal class ExaminationRecordServiceTest {

    @Autowired
    private lateinit var accountRepository: AccountRepository

    @Autowired
    private lateinit var examinationRecordRepository: ExaminationRecordRepository

    @Test
    fun `changing state for a non-existing user`() {
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)

        assertThrows<LoonoBackendException>("Account not found") {
            examinationRecordService.createOrUpdateExam(
                ExaminationRecordDto(
                    uuid = "1",
                    type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
                    status = ExaminationStatusDto.TO_BE_CONFIRMED
                ),
                "1"
            )
        }
    }

    @Test
    fun `changing state of a non-existing exam`() {
        accountRepository.save(Account(uid = "101"))
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)
        val exam = ExaminationRecordDto(
            uuid = "1",
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
            status = ExaminationStatusDto.TO_BE_CONFIRMED,
            firstExam = false,
            date = LocalDateTime.MIN
        )

        assertThrows<LoonoBackendException>("The given examination identifier not found.") {
            examinationRecordService.createOrUpdateExam(
                exam,
                "101"
            )
        }
    }

    @Test
    fun `new exam creation`() {
        accountRepository.save(Account(uid = "101"))
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)
        val exam = ExaminationRecordDto(
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER
        )

        val result = examinationRecordService.createOrUpdateExam(exam, "101")

        assert(result.type == exam.type)
        assert(result.status == exam.status)
        assert(result.firstExam == exam.firstExam)
    }

    @Test
    fun `valid changing of state`() {
        val account = accountRepository.save(Account(uid = "101"))
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)
        val exam = ExaminationRecordDto(
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER
        )
        val storedExam = examinationRecordRepository.save(ExaminationRecord(type = exam.type, account = account))
        val changedExam = ExaminationRecordDto(
            uuid = storedExam.uuid,
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
            status = ExaminationStatusDto.TO_BE_CONFIRMED,
            firstExam = false,
            date = LocalDateTime.MAX
        )

        val result = examinationRecordService.createOrUpdateExam(changedExam, "101")

        assert(result.status == changedExam.status)
        assert(result.firstExam == changedExam.firstExam)
        assert(result.date == changedExam.date)
    }

    @Test
    fun `confirm exam`() {
        val account = accountRepository.save(Account(uid = "101"))
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)
        val exam = ExaminationRecordDto(
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
            status = ExaminationStatusDto.TO_BE_CONFIRMED
        )
        val storedExam = examinationRecordRepository.save(ExaminationRecord(type = exam.type, account = account))

        val result = examinationRecordService.confirmExam(storedExam.id, "101")

        assert(result.status == ExaminationStatusDto.CONFIRMED)
    }

    @Test
    fun `cancel exam`() {
        val account = accountRepository.save(Account(uid = "101"))
        val examinationRecordService = ExaminationRecordService(accountRepository, examinationRecordRepository)
        val exam = ExaminationRecordDto(
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
            status = ExaminationStatusDto.TO_BE_CONFIRMED
        )
        val storedExam = examinationRecordRepository.save(ExaminationRecord(type = exam.type, account = account))

        val result = examinationRecordService.cancelExam(storedExam.id, "101")

        assert(result.status == ExaminationStatusDto.CANCELED)
    }
}
