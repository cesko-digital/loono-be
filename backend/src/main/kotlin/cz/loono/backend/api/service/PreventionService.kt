package cz.loono.backend.api.service

import cz.loono.backend.api.dto.ExaminationStatusDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.dto.PreventionStatusDto
import cz.loono.backend.api.dto.SexDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.db.model.ExaminationRecord
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.db.repository.ExaminationRecordRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class PreventionService(
    private val examinationRecordRepository: ExaminationRecordRepository,
    private val accountRepository: AccountRepository
) {

    fun getPreventionStatus(accountUuid: String): List<PreventionStatusDto> {
        val account = accountRepository.findByUid(accountUuid) ?: throw LoonoBackendException(
            HttpStatus.NOT_FOUND, "Account not found"
        )

        val sex = account.userAuxiliary.sex ?: throw LoonoBackendException(
            HttpStatus.UNPROCESSABLE_ENTITY, "sex not known"
        )

        val birthDate = account.userAuxiliary.birthdate ?: throw LoonoBackendException(
            HttpStatus.UNPROCESSABLE_ENTITY, "birthdate not known"
        )
        val age = ChronoUnit.YEARS.between(birthDate, LocalDate.now()).toInt()

        val examinationRequests = ExaminationIntervalProvider.findExaminationRequests(
            Patient(age, SexDto.valueOf(sex))
        )

        val examinationTypesToRecords: Map<ExaminationTypeEnumDto, List<ExaminationRecord>> =
            examinationRecordRepository.findAllByAccountOrderByDateDesc(account)
                .groupBy { it.type }
                .mapNotNull { entry -> entry.key to entry.value }
                .toMap()

        return examinationRequests.map { examinationInterval ->
            val examsOfType = examinationTypesToRecords[examinationInterval.examinationType]
            val sortedExamsOfType = examsOfType
                ?.filter { it ->
                    it.date != null
                        || it.status != ExaminationStatusDto.CONFIRMED
                        || it.status != ExaminationStatusDto.CANCELED
                }
                ?.sortedBy { it.date } ?: listOf(ExaminationRecord())

            var streak = 0
            if (examsOfType != null) {
                streak = examsOfType.map { it.status = ExaminationStatusDto.CONFIRMED }.size
            }

            PreventionStatusDto(
                id = sortedExamsOfType[0].id,
                examinationType = examinationInterval.examinationType,
                intervalYears = examinationInterval.intervalYears,
                lastExamDate = sortedExamsOfType[0].date,
                firstExam = sortedExamsOfType[0].firstExam,
                priority = examinationInterval.priority,
                state = sortedExamsOfType[0].status,
                streak = streak
            )
        }
    }
}
