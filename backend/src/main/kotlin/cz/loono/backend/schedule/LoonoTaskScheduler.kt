package cz.loono.backend.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class LoonoTaskScheduler(
    private val examinationCancellationTask: ExaminationCancellationTask
) {

    @Scheduled(cron = "0 0 3 * * ?") // each day at 3AM
    fun executeDailyTasks() {
        examinationCancellationTask.run()
    }
}
