package cz.loono.backend.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class LoonoTaskScheduler(
    private val examinationCancellationTask: ExaminationCancellationTask,
    private val badgeDowngradeTask: BadgeDowngradeTask
) {

    @Scheduled(cron = "\${scheduler.cron.daily-task}") // each day at 3AM
    fun executeDailyTasks() {
        examinationCancellationTask.run()
    }

    @Scheduled(cron = "\${scheduler.cron.badge-downgrade}") // each day at midnight
    fun downgradeBadges() {
        badgeDowngradeTask.run()
    }
}
