package cz.loono.backend.notification

data class NotificationResponse(
    val id: String,
    val recipients: Int,
    val external_id: String?
)
