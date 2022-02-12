package cz.loono.backend.schedule

import BadgeDowngradeTask
import cz.loono.backend.createAccount
import cz.loono.backend.db.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@Import(BadgeDowngradeTask::class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestPropertySource(
    properties = [
        "task.badge-downgrade.page-size = 2"
    ]
)
class BadgeDowngradeTaskTest(
    private val badgeDowngradeTask: BadgeDowngradeTask,
    private val accountRepository: AccountRepository,
) {

    @Test
    fun `Should paginate accounts`() {
        val accounts = listOf("uuid1", "uuid2", "uuid3", "uuid4", "uuid5", "uuid6").map(::createAccount)
        accountRepository.saveAll(accounts)

        badgeDowngradeTask.run()
    }
}
