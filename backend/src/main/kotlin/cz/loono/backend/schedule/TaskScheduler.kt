package cz.loono.backend.schedule

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component("taskScheduler")
class TaskScheduler @Autowired constructor(
    private val examinationCancellationTask: ExaminationCancellationTask
) {

    @Scheduled(cron = "0 0 3 * * ?") // each day at 3AM
    fun executeDailyTasks() {
        examinationCancellationTask.run()
    }
}
