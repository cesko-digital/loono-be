import cz.loono.backend.api.dto.BadgeTypeDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.service.BadgesPointsProvider.BADGES_TO_EXAMS
import cz.loono.backend.api.service.PreventionService
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.extensions.toLocalDateTime
import cz.loono.backend.schedule.SchedulerTask
import java.time.Clock
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class BadgeDowngradeTask(
    @Value("\${task.badge-downgrade.tolerance-months}")
    private val toleranceMonths: Long,
    @Value("\${task.badge-downgrade.page-size}")
    private val pageSize: Int,
    private val accountRepository: AccountRepository,
    private val preventionService: PreventionService,
    private val clock: Clock,
) : SchedulerTask {

    companion object {
        private val FIELDS_TO_SORT_BY = arrayOf("id")
    }

    override fun run() {
        paginateOverAccounts { nextPage ->
            val accountsToUpdate = nextPage.mapNotNull { account ->
                val now = clock.instant().toLocalDateTime()
                val userBadges = account.badges
                // Don't do anything when no badges to downgrade
                userBadges.ifEmpty {
                    return@ifEmpty
                }

                val latestExam = getLatestExam(account)
                val examsRequests = preventionService.getExaminationRequests(account)

                val downgradedBadges = userBadges.map { badge ->
                    val exam: ExaminationTypeEnumDto = BADGES_TO_EXAMS.getValue(BadgeTypeDto.valueOf(badge.type))
                    val intervalYears = examsRequests.first { it.examinationType == exam }.intervalYears
                    intervalYears.toLong().let {
                        // Using double-bang operator as we filtered only non-nullable planned dates before
                        val plannedDate = latestExam.plannedDate!!.plusYears(it).plusMonths(toleranceMonths)
                        val lastUpdatedDate = badge.lastUpdateOn
                        if (now.isAfter(lastUpdatedDate) && now.isAfter(plannedDate)) {
                            badge.copy(level = badge.level.dec(), lastUpdateOn = now.plusYears(it))
                        } else {
                            badge
                        }
                    }
                }.toSet()

                if (downgradedBadges != account.badges) account.copy(badges = downgradedBadges) else null
            }

            accountRepository.saveAll(removeZeroLevelBadges(accountsToUpdate))
        }
    }

    private fun paginateOverAccounts(transformPage: (List<Account>) -> Unit) {
        var page: Pageable = PageRequest.of(0, pageSize, Sort.by(*FIELDS_TO_SORT_BY))

        do {
            val accountsPage = accountRepository.findAll(page)
            val accountsFromPage = accountsPage.content
            transformPage(accountsFromPage)
            page = accountsPage.nextPageable()
        } while (accountsPage.hasNext())
    }

    private fun removeZeroLevelBadges(accounts: List<Account>) =
        accounts.map { account -> account.copy(badges = account.badges.filter { it.level > 0 }.toSet()) }

    private fun getLatestExam(account: Account) = account.examinationRecords.last { it.plannedDate != null }
}
