package cz.loono.backend.db.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "\"category\"")
data class Category(

    @Id
    @Column(nullable = false, columnDefinition = "TEXT")
    val value: String = "",

    @ManyToMany(mappedBy = "category")
    val healthcareProviders: Set<HealthcareProvider> = mutableSetOf()
)
