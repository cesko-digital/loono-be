package cz.loono.backend

import javax.servlet.ServletRequest

/**
 * Shorthand for `getAttribute(name) as T`
 */
inline fun <reified T : Any> ServletRequest.getTypedAttribute(name: String): T = getAttribute(name) as T