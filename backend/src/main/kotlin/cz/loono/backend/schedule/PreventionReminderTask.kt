package cz.loono.backend.schedule

import cz.loono.backend.api.service.PreventionService
import cz.loono.backend.api.service.PushNotificationService
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.repository.AccountRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class PreventionReminderTask(
    private val accountRepository: AccountRepository,
    private val preventionService: PreventionService,
    private val notificationService: PushNotificationService
) : DailySchedulerTask {

    override fun run() {
        val today = LocalDate.now()
        val selectedAccounts = accountRepository.findAll().filter {
            Period.between(it.created, today).months % 3 == 0
        }
        val notificationAccounts = mutableSetOf<Account>()
        selectedAccounts.forEach { account ->
            val status = preventionService.getPreventionStatus(account.uid)
            status.examinations.forEach examsLoop@{ exam ->
                exam.lastConfirmedDate?.let {
                    val period = Period.between(it.toLocalDate(), today)
                    if (exam.plannedDate == null && period.years >= exam.intervalYears) {
                        notificationAccounts.add(account)
                        return@examsLoop
                    }
                }
            }
        }
        notificationService.sendPreventionNotification(notificationAccounts)
    }
}
