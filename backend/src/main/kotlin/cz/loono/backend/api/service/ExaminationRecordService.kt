package cz.loono.backend.api.service

import cz.loono.backend.api.dto.ExaminationRecordDto
import cz.loono.backend.api.dto.ExaminationStatusDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.model.ExaminationRecord
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.db.repository.ExaminationRecordRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ExaminationRecordService @Autowired constructor(
    private val accountRepository: AccountRepository,
    private val examinationRecordRepository: ExaminationRecordRepository,
) {

    @Synchronized
    @Transactional(rollbackFor = [Exception::class])
    fun confirmExam(examId: Long, uid: String): ExaminationRecordDto {
        return changeState(examId, uid, ExaminationStatusDto.CONFIRMED)
    }

    @Synchronized
    @Transactional(rollbackFor = [Exception::class])
    fun cancelExam(examId: Long, uid: String): ExaminationRecordDto {
        return changeState(examId, uid, ExaminationStatusDto.CANCELED)
    }

    fun createOrUpdateExam(examinationRecordDto: ExaminationRecordDto, uid: String): ExaminationRecordDto {
        val record = validateUpdateAttempt(examinationRecordDto)
        return examinationRecordRepository.save(
            ExaminationRecord(
                id = record.id,
                uuid = record.uuid,
                type = examinationRecordDto.type,
                plannedDate = examinationRecordDto.date,
                account = findAccount(uid),
                firstExam = examinationRecordDto.firstExam ?: true,
                status = examinationRecordDto.status ?: ExaminationStatusDto.NEW
            )
        ).toExaminationRecordDto()
    }

    private fun validateUpdateAttempt(examinationRecordDto: ExaminationRecordDto): ExaminationRecord {
        if (examinationRecordDto.uuid != null) {
            return examinationRecordRepository.findByUuid(examinationRecordDto.uuid)
                ?: throw LoonoBackendException(
                    HttpStatus.NOT_FOUND,
                    "404",
                    "The given examination identifier not found."
                )
        }
        return ExaminationRecord()
    }

    private fun findAccount(uid: String): Account {
        return accountRepository.findByUid(uid) ?: throw LoonoBackendException(
            HttpStatus.NOT_FOUND, "Account not found"
        )
    }

    private fun changeState(examId: Long, uid: String, state: ExaminationStatusDto): ExaminationRecordDto {
        val account = findAccount(uid)

        val exam = examinationRecordRepository.findByIdAndAccount(examId, account)
        exam.status = state

        return examinationRecordRepository.save(exam).toExaminationRecordDto()
    }

    fun ExaminationRecord.toExaminationRecordDto(): ExaminationRecordDto {
        return ExaminationRecordDto(
            uuid = uuid,
            type = type,
            date = plannedDate,
            firstExam = firstExam,
            status = status
        )
    }
}
