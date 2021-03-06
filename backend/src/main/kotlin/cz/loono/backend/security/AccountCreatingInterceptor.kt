package cz.loono.backend.security

import cz.loono.backend.api.Attributes
import cz.loono.backend.api.BasicUser
import cz.loono.backend.api.exception.LoonoBackendException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * An interceptor that ensures an account exists
 */
@Component
class AccountCreatingInterceptor : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        when (val jwtData = request.getAttribute(Attributes.ATTR_BASIC_USER)) {
            null -> {
                val error = "HTTP request does not contain required attribute: ${Attributes.ATTR_BASIC_USER}.\n" +
                    "Probable cause: CreateUserInterceptor was ordered before authenticator."
                logger.error(error)
                throw LoonoBackendException(HttpStatus.INTERNAL_SERVER_ERROR)
            }

            !is BasicUser -> {
                val error = "Attribute '${Attributes.ATTR_BASIC_USER}' is not of type " +
                    "${BasicUser::class.qualifiedName}\n" +
                    "It is of type ${jwtData.javaClass.canonicalName}"
                logger.error(error)
                throw LoonoBackendException(HttpStatus.INTERNAL_SERVER_ERROR)
            }
        }

        return true
    }
}
