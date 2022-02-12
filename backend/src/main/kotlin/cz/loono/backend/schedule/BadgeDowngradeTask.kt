
import cz.loono.backend.api.dto.ExaminationStatusDto
import cz.loono.backend.api.dto.SexDto
import cz.loono.backend.api.service.BadgesPointsProvider
import cz.loono.backend.api.service.ExaminationIntervalProvider
import cz.loono.backend.api.service.HealthcareProvidersService
import cz.loono.backend.api.service.Patient
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.model.ExaminationRecord
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.schedule.SchedulerTask
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class BadgeDowngradeTask(
    private val accountRepository: AccountRepository,
    @Value("\${task.badge-downgrade.page-size}")
    private val pageSize: Int,
) : SchedulerTask {

    override fun run() {
        var page: Pageable = PageRequest.of(0, pageSize, Sort.by("id"))

        do {
            val accountsPage = accountRepository.findAll(page)
            println("${accountsPage.content}")
            accountsPage.content.map { account ->
                val confirmedExams = account.examinationRecords
                    .filter { it.status == ExaminationStatusDto.CONFIRMED && it.plannedDate != null }

                val examsRequests = ExaminationIntervalProvider.findExaminationRequests(
                    Patient(60, SexDto.valueOf(account.userAuxiliary.sex!!))
                )

                val badges = examsRequests.map {
                    BadgesPointsProvider.getBadgesAndPoints(it.examinationType, SexDto.valueOf(account.userAuxiliary.sex))
                }

                val userBadges = account.badges

                userBadges.

                confirmedExams.map {

                }

                // All exams
                // examType
                // intervals in year
                // priority

                // getAllExamRequests
                // getAllBadges
                // getExistingBadges
                // getAllExistingExamRecord confirmed with date
                account.
                examRequests.map {
                    BadgesPointsProvider.getBadgesAndPoints(it.examinationType, SexDto.valueOf(account.userAuxiliary.sex))
                }

            }
            page = accountsPage.nextPageable()
        } while (accountsPage.hasNext())
    }
}
