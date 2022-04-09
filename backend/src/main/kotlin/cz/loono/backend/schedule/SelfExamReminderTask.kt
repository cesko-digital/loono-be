package cz.loono.backend.schedule

import cz.loono.backend.api.v1.service.PreventionServiceV1
import cz.loono.backend.api.v1.service.PushNotificationServiceV1
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.repository.AccountRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SelfExamReminderTask(
    private val accountRepository: AccountRepository,
    private val preventionService: PreventionServiceV1,
    private val notificationService: PushNotificationServiceV1
) : DailySchedulerTask {

    override fun run() {
        val accounts = accountRepository.findAll()
        val today = LocalDate.now()
        accounts.forEach { account ->
            val statuses = preventionService.getPreventionStatus(account.uid).selfexaminations
            statuses.forEach {
                if (account.created.dayOfMonth == today.dayOfMonth && it.plannedDate == null) {
                    notificationService.sendFirstSelfExamNotification(setOf<Account>(account), account.getSexAsEnum())
                }
                if (it.plannedDate == today) {
                    notificationService.sendSelfExamNotification(setOf<Account>(account), account.getSexAsEnum())
                }
            }
        }
    }
}
