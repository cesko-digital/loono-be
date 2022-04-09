package cz.loono.backend.api.v1.controller

import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class RootControllerV1Test {

    @Test
    fun redirectTest() {
        val response = RootControllerV1().index()
        assert(response.statusCode == HttpStatus.PERMANENT_REDIRECT)
        assert(response.headers.location.toString().equals("https://www.loono.cz/"))
    }
}
