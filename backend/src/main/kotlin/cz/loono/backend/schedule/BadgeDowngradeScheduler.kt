package cz.loono.backend.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BadgeDowngradeScheduler(
    private val badgeDowngradeTask: BadgeDowngradeTask
) {

    @Scheduled(cron = "\${scheduler.cron.badge-downgrade}")
    fun downgradeBadges() {
        badgeDowngradeTask.run()
    }
}
