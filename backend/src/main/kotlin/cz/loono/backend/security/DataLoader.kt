package cz.loono.backend.security

import cz.loono.backend.db.model.ServerProperties
import cz.loono.backend.db.repository.ServerPropertiesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataLoader @Autowired constructor(
    val serverPropertiesRepository: ServerPropertiesRepository
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        if (serverPropertiesRepository.findAll().isEmpty()) {
            serverPropertiesRepository.save(ServerProperties())
        }
    }
}
