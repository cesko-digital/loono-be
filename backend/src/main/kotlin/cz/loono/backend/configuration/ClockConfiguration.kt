package cz.loono.backend.configuration

import java.time.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClockConfiguration {
    @Bean
    fun clockUTC(): Clock = Clock.systemUTC()
}
