package cz.loono.backend.api.service

import cz.loono.backend.db.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AccountServiceTest(
    private val repo: AccountRepository
) {

    private val firebaseAuthService: FirebaseAuthService = mock()

    @Test
    fun `updateSettings with missing account`() {
        val service = AccountService(repo, firebaseAuthService)
    }
}
