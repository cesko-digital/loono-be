package cz.loono.backend.schedule

import cz.loono.backend.api.v1.service.AccountServiceV1
import cz.loono.backend.api.v1.service.PreventionServiceV1
import cz.loono.backend.api.v1.service.PushNotificationServiceV1
import cz.loono.backend.db.model.Account
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Period

@Component
class PreventionReminderTask(
    private val accountService: AccountServiceV1,
    private val preventionService: PreventionServiceV1,
    private val notificationService: PushNotificationServiceV1
) : DailySchedulerTask {

    override fun run() {
        val today = LocalDate.now()
        accountService.paginateOverAccounts { accounts ->
            val selectedAccounts = accounts.filter {
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
}
