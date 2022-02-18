package cz.loono.backend.api.controller

import cz.loono.backend.api.dto.AccountDto
import cz.loono.backend.api.dto.SexDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.api.service.AccountService
import cz.loono.backend.api.service.FirebaseAuthService
import cz.loono.backend.createAccount
import cz.loono.backend.createBasicUser
import cz.loono.backend.db.repository.AccountRepository
import cz.loono.backend.db.repository.ExaminationRecordRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.http.HttpStatus

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AccountControllerTest(
    private val repo: AccountRepository,
    private val examRepo: ExaminationRecordRepository
) {

    private val firebaseAuthService: FirebaseAuthService = mock()

    @Test
    fun `getAccount with missing account`() {
        val service = mock<AccountService>()
        val controller = AccountController(service, repo)

        val ex = assertThrows<LoonoBackendException> {
            controller.getAccount(createBasicUser())
        }

        assertEquals(HttpStatus.NOT_FOUND, ex.status)
        assertNull(ex.errorCode)
        assertNull(ex.errorMessage)
    }

    @Test
    fun `getAccount with existing account`() {
        // Arrange
        val service = AccountService(repo, firebaseAuthService, examRepo)
        val controller = AccountController(service, repo)
        val basicUser = createBasicUser()
        val existingAccount = createAccount()
        repo.save(existingAccount)

        // Act
        val actualDto = controller.getAccount(basicUser)

        // Assert
        val expectedDto = AccountDto(
            uid = basicUser.uid,
            nickname = existingAccount.nickname,
            sex = existingAccount.sex.let(SexDto::valueOf),
            birthdate = existingAccount.birthdate,
            prefferedEmail = existingAccount.preferredEmail,
            profileImageUrl = existingAccount.profileImageUrl,
            leaderboardAnonymizationOptIn = existingAccount.leaderboardAnonymizationOptIn,
            appointmentReminderEmailsOptIn = existingAccount.appointmentReminderEmailsOptIn,
            newsletterOptIn = existingAccount.newsletterOptIn,
            points = existingAccount.points,
            badges = emptyList()
        )
        assertEquals(expectedDto, actualDto)
    }

    @Test
    fun `delete non-existing account`() {
        // Arrange
        val service = AccountService(repo, firebaseAuthService, examRepo)
        val controller = AccountController(service, repo)

        // Act
        assertThrows<LoonoBackendException> {
            controller.deleteAccount(createBasicUser())
        }
    }

    @Test
    fun `delete existing account`() {
        // Arrange
        val service = AccountService(repo, firebaseAuthService, examRepo)
        val controller = AccountController(service, repo)
        val basicUser = createBasicUser()
        val existingAccount = createAccount()
        repo.save(existingAccount)

        // Act
        controller.deleteAccount(basicUser)

        // Assert
        assert(repo.findByUid(basicUser.uid) == null)
        assert(repo.count() == 0L)
    }
}
