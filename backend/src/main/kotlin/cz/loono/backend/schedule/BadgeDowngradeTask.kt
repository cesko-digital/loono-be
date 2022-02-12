import cz.loono.backend.api.dto.BadgeTypeDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.service.BadgesPointsProvider.BADGES_TO_EXAMS
import cz.loono.backend.api.service.ExaminationInterval
import cz.loono.backend.api.service.PreventionService
import cz.loono.backend.db.model.Account
import cz.loono.backend.db.model.Badge
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.extensions.toLocalDateTime
import cz.loono.backend.schedule.SchedulerTask
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.Temporal
import kotlin.math.abs

@Component
class BadgeDowngradeTask(
    private val accountRepository: AccountRepository,
    @Value("\${task.badge-downgrade.page-size}")
    private val pageSize: Int,
    private val preventionService: PreventionService,
    private val clock: Clock,
    @Value("\${task.badge-downgrade.tolerance-months}")
    private val toleranceMonths: Int
) : SchedulerTask {

    companion object {
        private const val BADGE_UPDATE_INTERVAL_MONTHS = 12
        private val FIELDS_TO_SORT_BY = arrayOf("id")
    }

    override fun run() {
        var page: Pageable = PageRequest.of(0, pageSize, Sort.by(*FIELDS_TO_SORT_BY))

        do {
            val accountsPage = accountRepository.findAll(page)
            val accountsFromPage = accountsPage.content
            val accountsToUpdate = accountsFromPage.mapNotNull { account ->
                val now = clock.instant().toLocalDateTime()
                val userBadges = account.badges
                // Don't do anything when no badges to downgrade
                userBadges.ifEmpty {
                    return
                }

                val confirmedExams = getPlannedExams(account)
                val examsRequests = preventionService.getExaminationRequests(account)

                val downgradedBadges = userBadges.map { badge ->
                    val exam: ExaminationTypeEnumDto = BADGES_TO_EXAMS.getValue(BadgeTypeDto.valueOf(badge.type))
                    val request = examsRequests.find { it.examinationType == exam }
                    val updatedBadge = request?.let { request ->
                        val confirmedExamsWithinYearCount = confirmedExams.count {
                            it.type == request.examinationType && differenceInMonths(
                                now,
                                // Using bang operator, since we take non-nullable plannedDates only
                                it.plannedDate!!
                            ) > BADGE_UPDATE_INTERVAL_MONTHS + toleranceMonths
                        }
                        if (shouldDowngradeBadge(badge, now, request, confirmedExamsWithinYearCount)) {
                            badge.copy(level = badge.level.dec(), lastUpdateOn = now)
                        } else {
                            badge
                        }
                    }
                    updatedBadge ?: badge
                }.toSet()
                if (downgradedBadges != account.badges) account.copy(badges = downgradedBadges) else null
            }

            accountRepository.saveAll(removeZeroLevelBadges(accountsToUpdate))
            page = accountsPage.nextPageable()
        } while (accountsPage.hasNext())
    }

    private fun removeZeroLevelBadges(accounts: List<Account>) =
        accounts.map { account -> account.copy(badges = account.badges.filter { it.level > 0 }.toSet()) }

    private fun shouldDowngradeBadge(
        badge: Badge,
        now: LocalDateTime,
        request: ExaminationInterval,
        confirmedExamsWithinYearCount: Int
    ) = differenceInMonths(now, badge.lastUpdateOn) >= BADGE_UPDATE_INTERVAL_MONTHS &&
        confirmedExamsWithinYearCount >= request.intervalYears

    private fun getPlannedExams(account: Account) = account
        .examinationRecords
        .filter { it.plannedDate != null }

    private fun differenceInMonths(from: Temporal, to: Temporal): Long = abs(ChronoUnit.MONTHS.between(from, to))
}
