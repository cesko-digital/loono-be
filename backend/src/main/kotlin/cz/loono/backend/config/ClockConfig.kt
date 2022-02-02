package cz.loono.backend.config

import java.time.Clock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClockConfig {
    @Bean
    fun clockUTC(): Clock = Clock.systemUTC()
}
