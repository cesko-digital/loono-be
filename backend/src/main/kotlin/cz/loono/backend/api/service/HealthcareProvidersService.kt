package cz.loono.backend.api.service

import cz.loono.backend.api.dto.HealthcareProviderDetailsDto
import cz.loono.backend.api.dto.HealthcareProviderListDto
import cz.loono.backend.api.dto.SimpleHealthcareProviderDto
import cz.loono.backend.api.dto.UpdateStatusMessageDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.data.HealthcareCSVParser
import cz.loono.backend.data.constants.CategoryValues
import cz.loono.backend.data.constants.Constants.OPEN_DATA_URL
import cz.loono.backend.db.model.HealthcareCategory
import cz.loono.backend.db.model.HealthcareProvider
import cz.loono.backend.db.model.HealthcareProviderId
import cz.loono.backend.db.repository.HealthcareCategoryRepository
import cz.loono.backend.db.repository.HealthcareProviderRepository
import io.github.reactivecircus.cache4k.Cache
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URL

@Service
class HealthcareProvidersService @Autowired constructor(
    private val healthcareProviderRepository: HealthcareProviderRepository,
    private val healthcareCategoryRepository: HealthcareCategoryRepository
) {

    private val cache = Cache.Builder().build<HealthcareProviderId, SimpleHealthcareProviderDto>()

    @Scheduled(cron = "0 0 2 1 * ?") // each the 1st day of month at 2AM
    @Synchronized
    @Transactional(rollbackFor = [Exception::class])
    fun updateData(): UpdateStatusMessageDto {
        val input = URL(OPEN_DATA_URL).openStream()
        val providers = HealthcareCSVParser().parse(input)
        if (providers.isNotEmpty()) {
            val categoryValues = CategoryValues.values().map { HealthcareCategory(value = it.value) }
            healthcareCategoryRepository.saveAll(categoryValues)
            healthcareProviderRepository.saveAll(providers)
            updateCache()
        } else {
            throw LoonoBackendException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                errorCode = HttpStatus.UNPROCESSABLE_ENTITY.value().toString(),
                errorMessage = "Data update failed."
            )
        }
        return UpdateStatusMessageDto("Data successfully updated.")
    }

    private fun updateCache() {
        cache.invalidateAll()
        val providers = healthcareProviderRepository.findAll()
        providers.forEach { provider ->
            val id = HealthcareProviderId(locationId = provider.locationId, institutionId = provider.institutionId)
            cache.put(id, provider.simplify())
        }
    }

    fun getAllSimpleData(): HealthcareProviderListDto {
        return HealthcareProviderListDto(
            healthcareProviders = cache.asMap().values.toList()
        )
    }

    fun getHealthcareProviderDetail(locationId: Long, institutionId: Long): HealthcareProviderDetailsDto {
        val providers = healthcareProviderRepository.findAllByLocationIdAndInstitutionId(locationId, institutionId)
        if (providers.isEmpty()) {
            throw LoonoBackendException(HttpStatus.NOT_FOUND)
        } else if (providers.size > 1) {
            throw LoonoBackendException(HttpStatus.BAD_REQUEST)
        }
        return providers.first().getDetails()
    }

    fun HealthcareProvider.simplify(): SimpleHealthcareProviderDto {
        return SimpleHealthcareProviderDto(
            locationId = locationId,
            institutionId = institutionId,
            title = title,
            street = street,
            houseNumber = houseNumber,
            city = city,
            postalCode = postalCode,
            category = category.map { it.value },
            specialization = specialization,
            lat = lat,
            lng = lng
        )
    }

    fun HealthcareProvider.getDetails(): HealthcareProviderDetailsDto {
        return HealthcareProviderDetailsDto(
            locationId = locationId,
            institutionId = institutionId,
            title = title,
            institutionType = institutionType,
            street = street,
            houseNumber = houseNumber,
            city = city,
            postalCode = postalCode,
            phoneNumber = phoneNumber,
            fax = fax,
            email = email,
            website = website,
            ico = ico,
            category = category.map { it.value },
            specialization = specialization,
            careForm = careForm,
            careType = careType,
            substitute = substitute,
            lat = lat,
            lng = lng
        )
    }
}
