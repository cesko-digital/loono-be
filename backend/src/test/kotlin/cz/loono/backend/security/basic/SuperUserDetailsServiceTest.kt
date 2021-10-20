package cz.loono.backend.security.basic

import cz.loono.backend.db.model.ServerProperties
import cz.loono.backend.db.repository.ServerPropertiesRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class SuperUserDetailsServiceTest {

    @Autowired
    private lateinit var serverPropertiesRepository: ServerPropertiesRepository

    @Test
    fun `get loonoAdmin details`() {
        serverPropertiesRepository.save(ServerProperties())
        val superUserDetailsService = SuperUserDetailsService(serverPropertiesRepository)

        val userDetails = superUserDetailsService.loadUserByUsername("loonoAdmin")

        assert(
            userDetails == SuperUserDetails(
                username = "loonoAdmin",
                password = "\$2a\$10\$mqCYPoDvwtA.7H6qrhvFNeX5nlaM1n7Pt//A5gS3V9UOqiuhquu0i"
            )
        )
    }

    @Test
    fun `server properties not set`() {
        val superUserDetailsService = SuperUserDetailsService(serverPropertiesRepository)

        assertThrows<IndexOutOfBoundsException> { superUserDetailsService.loadUserByUsername("loonoAdmin") }
    }
}
