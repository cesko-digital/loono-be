package cz.loono.backend.data

import cz.loono.backend.data.constants.Constants
import cz.loono.backend.data.constants.District
import cz.loono.backend.data.constants.LawyerFormList
import cz.loono.backend.data.constants.Region
import cz.loono.backend.db.model.HealthcareCategory
import cz.loono.backend.db.model.HealthcareProvider

class HealthcareProviderBuilder(private val columns: List<String>) {

    private var lawyerFormCode = ""
    private var lawyerFormName = ""
    private var lawyerPersonType = ""
    private var categories = emptySet<HealthcareCategory>()
    private var hqDistrictName = ""
    private var hqRegionName = ""

    fun build(): HealthcareProvider {
        return HealthcareProvider(
            locationId = getColumnValue("MistoPoskytovaniId", columns),
            institutionId = getColumnValue("ZdravotnickeZarizeniId", columns),
            code = getColumnValue("Kod", columns),
            title = getColumnValue("NazevZarizeni", columns),
            institutionType = getColumnValue("DruhZarizeni", columns),
            city = getColumnValue("Obec", columns),
            postalCode = getColumnValue("Psc", columns),
            street = getColumnValue("Ulice", columns),
            houseNumber = getColumnValue("CisloDomovniOrientacni", columns),
            region = getColumnValue("Kraj", columns),
            regionCode = getColumnValue("KrajCode", columns),
            district = getColumnValue("Okres", columns),
            districtCode = getColumnValue("OkresCode", columns),
            administrativeDistrict = getColumnValue("SpravniObvod", columns),
            phoneNumber = getColumnValue("PoskytovatelTelefon", columns),
            fax = getColumnValue("PoskytovatelFax", columns),
            email = getColumnValue("PoskytovatelEmail", columns),
            website = getColumnValue("PoskytovatelWeb", columns),
            ico = getColumnValue("Ico", columns),
            personTypeCode = getColumnValue("TypOsoby", columns),
            personType = lawyerPersonType,
            lawyerFormCode = lawyerFormCode,
            layerForm = lawyerFormName,
            hqRegionCode = getColumnValue("KrajCodeSidlo", columns),
            hqRegion = hqRegionName,
            hqDistrictCode = getColumnValue("OkresCodeSidlo", columns),
            hqDistrict = hqDistrictName,
            hqCity = getColumnValue("ObecSidlo", columns),
            hqPostalCode = getColumnValue("PscSidlo", columns),
            hqStreet = getColumnValue("UliceSidlo", columns),
            hqHouseNumber = getColumnValue("CisloDomovniOrientacniSidlo", columns),
            specialization = getColumnValue("OborPece", columns),
            careForm = getColumnValue("FormaPece", columns),
            careType = getColumnValue("DruhPece", columns),
            substitute = getColumnValue("OdbornyZastupce", columns),
            lat = getColumnValue("Lat", columns),
            lng = getColumnValue("Lng", columns),
            category = categories
        )
    }

    fun withLawyerForm(): HealthcareProviderBuilder {
        val lawyerFormCodeId = getColumnValue("PravniFormaKod", columns).toIntOrNull()
        if (lawyerFormCodeId != null) {
            val lawyerForm = LawyerFormList.list[lawyerFormCodeId]
            lawyerFormCode = lawyerFormCodeId.toString()
            lawyerFormName = lawyerForm!!.name
            lawyerPersonType = lawyerForm.personType.value
        }
        return this
    }

    fun withCategories(): HealthcareProviderBuilder {
        val specialization = getColumnValue("OborPece", columns)
        categories = SpecializationMapper.defineCategory(specialization)
        return this
    }

    fun withHQDistrictAndRegionName(): HealthcareProviderBuilder {
        if (getColumnValue("OkresCodeSidlo", columns) != "OkresCodeSidlo") {
            hqDistrictName = District.valueOf(getColumnValue("OkresCodeSidlo", columns)).value
            hqRegionName = Region.valueOf(getColumnValue("KrajCodeSidlo", columns)).value
        }
        return this
    }

    private fun getColumnValue(columnName: String, columns: List<String>): String {
        var value = columns[Constants.healthcareProvidersCSVHeader.indexOf(columnName)]
        value = value.replace("_Q_", "\"")
        value = value.replace("_COMMA_", ",")
        return value
    }
}
