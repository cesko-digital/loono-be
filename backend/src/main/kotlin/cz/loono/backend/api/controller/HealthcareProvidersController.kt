package cz.loono.backend.api.controller

import cz.loono.backend.api.service.HealthcareProvidersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthcareProvidersController {

    @Autowired
    private lateinit var healthCareProvidersService: HealthcareProvidersService

    @GetMapping(value = ["$DOCTORS_PATH/update"])
    fun updateData() {
        healthCareProvidersService.updateData()
    }

    @GetMapping(value = ["$DOCTORS_PATH/all"])
    fun getAll() {
        // TODO returns all providers in simple form
    }

    @PostMapping(value = ["$DOCTORS_PATH/detail"])
    fun getDetail() {
        // TODO returns a concrete details of the given doctor
    }

    companion object {
        private const val DOCTORS_PATH = "/doctors"
    }
}
