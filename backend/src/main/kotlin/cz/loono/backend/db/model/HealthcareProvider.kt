package cz.loono.backend.db.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@IdClass(HealthcareProviderId::class)
@Table(name = "\"healthcare_provider\"")
data class HealthcareProvider(

    @Id
    @Column(name = "location_id", nullable = false, columnDefinition = "TEXT")
    val locationId: String = "",

    @Id
    @Column(name = "institution_id", nullable = false, columnDefinition = "TEXT")
    val institutionId: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val code: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val title: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val institutionType: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val city: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val postalCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val street: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val houseNumber: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val region: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val regionCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val district: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val districtCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val administrativeDistrict: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val phoneNumber: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val fax: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val email: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val website: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val ico: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val personTypeCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val lawyerFormCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val layerForm: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val personType: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqRegion: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqRegionCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqDistrict: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqDistrictCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqCity: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqPostalCode: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqStreet: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val hqHouseNumber: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val specialization: String = "",

    @ManyToMany
    @JoinTable(
        name = "healthcare_provider_category",
        joinColumns = [
            JoinColumn(name = "location_id", referencedColumnName = "location_id"),
            JoinColumn(name = "institution_id", referencedColumnName = "institution_id")
        ],
        inverseJoinColumns = [JoinColumn(name = "category", referencedColumnName = "value")]
    )
    val category: Set<Category> = mutableSetOf(),

    @Column(nullable = false, columnDefinition = "TEXT")
    val careForm: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val careType: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val substitute: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val lat: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val lng: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HealthcareProvider

        if (locationId != other.locationId) return false
        if (institutionId != other.institutionId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = locationId.hashCode()
        result = 31 * result + institutionId.hashCode()
        return result
    }
}
