package cz.loono.backend.db.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "\"server_properties\"")
data class ServerProperties(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    val superUserName: String = "loonoAdmin",

    @Column(nullable = false, columnDefinition = "TEXT")
    val superUserPassword: String = "\$2a\$10\$mqCYPoDvwtA.7H6qrhvFNeX5nlaM1n7Pt//A5gS3V9UOqiuhquu0i",

    @Column(nullable = false, columnDefinition = "TEXT")
    val updateInterval: String = "0 0 1 * *" // once per month
)
