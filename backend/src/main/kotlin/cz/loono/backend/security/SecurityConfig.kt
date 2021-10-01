package cz.loono.backend.security

import cz.loono.backend.security.basic.CustomBasicAuthenticationEntryPoint
import cz.loono.backend.security.basic.SuperUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
@EnableWebSecurity
class SecurityConfig @Autowired constructor(
    private val authenticationEntryPoint: CustomBasicAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    @Bean
    override fun userDetailsService(): UserDetailsService {
        return SuperUserDetailsService()
    }

    @Autowired
    fun configureGlobal(authentication: AuthenticationManagerBuilder) {
        authentication.userDetailsService(userDetailsService())
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/doctors/update").hasRole("ADMIN")
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint)
            .and()
            .csrf()
    }
}
