package cz.loono.backend.data.constants

import java.time.LocalDate

data class LawyerForm(
    val code: Int,
    val name: String,
    val personType: PersonType,
    val validFrom: LocalDate? = null,
    val validTo: LocalDate? = null
)
