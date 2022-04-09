package cz.loono.backend.api.v1.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class SwaggerControllerV1 {

    @GetMapping(value = ["v1/api-docs"], produces = ["application/json"])
    @ResponseBody
    fun getOpenAPI(): String = javaClass.getResource("/doc/openapi.json").readText()
}
