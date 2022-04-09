package cz.loono.backend.schedule

import cz.loono.backend.api.dto.SelfExaminationStatusDto
import cz.loono.backend.api.v1.service.PushNotificationServiceV1
import cz.loono.backend.db.repository.SelfExaminationRecordRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SelfExaminationWaitingTask(
    private val selfExaminationRecordRepository: SelfExaminationRecordRepository,
    private val notificationService: PushNotificationServiceV1
) : DailySchedulerTask {

    override fun run() {
        selfExaminationRecordRepository.findAllByStatus(SelfExaminationStatusDto.WAITING_FOR_CHECKUP).forEach {
            if (it.waitingTo == LocalDate.now()) {
                selfExaminationRecordRepository.save(
                    it.copy(
                        status = SelfExaminationStatusDto.WAITING_FOR_RESULT,
                        waitingTo = null
                    )
                )
                notificationService.sendSelfExamIssueResultNotification(
                    setOf(it.account),
                    it.account.getSexAsEnum()
                )
            }
        }
    }
}
