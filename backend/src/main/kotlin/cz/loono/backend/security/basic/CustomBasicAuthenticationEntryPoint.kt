package cz.loono.backend.security.basic

import cz.loono.backend.api.dto.ErrorDto
import org.apache.http.entity.ContentType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = ContentType.APPLICATION_JSON.toString()
        response.addHeader("WWW-Authenticate", "Basic realm=\"$realmName\"")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(
            ErrorDto(
                code = HttpServletResponse.SC_UNAUTHORIZED.toString(),
                message = "Unauthorized request."
            ).toString()
        )
    }

    override fun afterPropertiesSet() {
        realmName = "Loono"
        super.afterPropertiesSet()
    }
}
