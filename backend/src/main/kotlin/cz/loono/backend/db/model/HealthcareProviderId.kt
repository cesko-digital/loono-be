package cz.loono.backend.db.model

import java.io.Serializable

data class HealthcareProviderId(
    val locationId: String = "",
    val institutionId: String = "",
) : Serializable
