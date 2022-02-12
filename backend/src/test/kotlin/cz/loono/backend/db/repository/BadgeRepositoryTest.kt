package cz.loono.backend.db.repository

import cz.loono.backend.createAccount
import cz.loono.backend.db.model.Badge
import org.junit.jupiter.api.Test
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BadgeRepositoryTest(
    private val badgeRepository: BadgeRepository,
    private val accountRepository: AccountRepository,
) {

    @Test
    fun `Should update existing badge`() {
        val account = createAccount()
        accountRepository.save(account)
        val badges = setOf(
            Badge("GLASSES", account.id, 0, account)
        )
        accountRepository.save(account.copy(badges = badges))
    }

}