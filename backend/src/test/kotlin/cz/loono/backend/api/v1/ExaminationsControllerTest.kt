package cz.loono.backend.api.v1

import cz.loono.backend.api.dto.BadgeTypeDto
import cz.loono.backend.api.dto.ExaminationIdDto
import cz.loono.backend.api.dto.ExaminationRecordDto
import cz.loono.backend.api.dto.ExaminationTypeDto
import cz.loono.backend.api.service.ExaminationRecordService
import cz.loono.backend.api.service.PreventionService
import cz.loono.backend.createAccount
import cz.loono.backend.createBasicUser
import cz.loono.backend.db.model.Badge
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.extensions.toLocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(properties = ["spring.profiles.active=test"])
@Import(ExaminationRecordService::class, PreventionService::class)
class ExaminationsControllerTest(
    private val recordService: ExaminationRecordService,
    private val preventionService: PreventionService,
    private val repo: AccountRepository
) {

    @Test
    @Transactional
    fun `Should add badge and points`() {
        val controller = ExaminationsController(recordService, preventionService)
        val basicUser = createBasicUser()
        var existingAccount = createAccount(birthday = LocalDate.of(1970, 1, 1))
        val examinationRecord = ExaminationRecordDto(
            type = ExaminationTypeDto.DENTIST,
            plannedDate = LocalDateTime.now().minusYears(3),
            firstExam = true
        )
        // This is done to get assigned ID by the DB
        existingAccount = repo.save(existingAccount)
        val expectedBadge = Badge(BadgeTypeDto.HEADBAND.value, existingAccount.id, 1, existingAccount, Instant.ofEpochMilli(1644682446419L).toLocalDateTime())

        var examUUID = controller.updateOrCreate(basicUser, examinationRecord).uuid!!
        controller.confirm(basicUser, ExaminationIdDto(examUUID))

        var actual = repo.findByUid("uid")!!
        assertThat(actual.badges).containsExactly(expectedBadge)
        assertThat(actual.points).isEqualTo(300)

        controller.updateOrCreate(
            basicUser, examinationRecord.copy(plannedDate = LocalDateTime.now().minusYears(1))
        )

        // Making sure that level upgraded and points increased
        actual = repo.findByUid("uid")!!
        assertThat(actual.badges).containsExactly(expectedBadge.copy(level = 2))
        assertThat(actual.points).isEqualTo(600)

        // Creating exam of another type
        examUUID =
            controller.updateOrCreate(basicUser, examinationRecord.copy(type = ExaminationTypeDto.UROLOGIST)).uuid!!
        controller.confirm(basicUser, ExaminationIdDto(examUUID))

        assertThat(actual.badges).containsExactly(
            expectedBadge.copy(level = 2), expectedBadge.copy(type = BadgeTypeDto.BELT.value)
        )
        assertThat(actual.points).isEqualTo(900)
    }

    @TestConfiguration
    class MockedClockConfig {
        @Bean
        @Primary
        fun mockedClock() = mock<Clock>().apply {
            whenever(instant()).thenReturn(Instant.ofEpochMilli(1644682446419L))
        }
    }
}
