package cz.loono.backend.api.service

import cz.loono.backend.api.dto.ExaminationStatusDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.dto.PreventionStatusDto
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.model.ExaminationRecord
import cz.loono.backend.db.model.UserAuxiliary
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.db.repository.ExaminationRecordRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class PreventionServiceTest {

    private val examinationRecordRepository: ExaminationRecordRepository = mock()
    private val accountRepository: AccountRepository = mock()
    private val preventionService = PreventionService(examinationRecordRepository, accountRepository)

    @Test
    fun `get prevention for patient`() {
        val uuid = UUID.randomUUID().toString()
        val age: Long = 45
        val now = LocalDateTime.now()
        val lastVisit = now.minusYears(1)
        val account = Account(
            userAuxiliary = UserAuxiliary(
                sex = "MALE",
                birthdate = LocalDate.now().minusYears(age)
            )
        )

        whenever(accountRepository.findByUid(uuid)).thenReturn(account)
        whenever(examinationRecordRepository.findAllByAccountOrderByDateDesc(account)).thenReturn(
            setOf(
                ExaminationRecord(date = now, type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER),
                ExaminationRecord(
                    type = ExaminationTypeEnumDto.OPHTHALMOLOGIST
                ), // is only planned
                ExaminationRecord(
                    date = lastVisit,
                    type = ExaminationTypeEnumDto.COLONOSCOPY
                ) // is not required
            )
        )

        val result = preventionService.getPreventionStatus(uuid)
        assertEquals(
            /* expected = */ listOf(
                PreventionStatusDto(
                    examinationType = ExaminationTypeEnumDto.GENERAL_PRACTITIONER,
                    intervalYears = 2,
                    firstExam = false,
                    priority = 1,
                    state = ExaminationStatusDto.NEW,
                    streak = 1,
                    lastExamDate = now
                ),
                PreventionStatusDto(
                    examinationType = ExaminationTypeEnumDto.DERMATOLOGIST,
                    intervalYears = 1,
                    lastExamDate = null,
                    firstExam = false,
                    priority = 6,
                    state = ExaminationStatusDto.NEW,
                    streak = 0
                ),
                PreventionStatusDto(
                    examinationType = ExaminationTypeEnumDto.DENTIST,
                    intervalYears = 1,
                    lastExamDate = null,
                    firstExam = false,
                    priority = 8,
                    state = ExaminationStatusDto.NEW,
                    streak = 0
                ),
                PreventionStatusDto(
                    examinationType = ExaminationTypeEnumDto.OPHTHALMOLOGIST,
                    intervalYears = 4,
                    lastExamDate = null,
                    firstExam = false,
                    priority = 9,
                    state = ExaminationStatusDto.NEW,
                    streak = 0
                ),
            ),
            /* actual = */ result
        )
    }
}
