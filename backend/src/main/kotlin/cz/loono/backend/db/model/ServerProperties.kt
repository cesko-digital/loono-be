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
    val superUserPassword: String = "{noop}pass",

    @Column(nullable = false, columnDefinition = "TEXT")
    val updateInterval: String = "0 0 1 * *" // once per month
)
