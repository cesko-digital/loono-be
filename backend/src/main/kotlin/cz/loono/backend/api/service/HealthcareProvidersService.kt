package cz.loono.backend.api.service

import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.data.HealthcareCSVParser
import cz.loono.backend.data.constants.CategoryValues
import cz.loono.backend.data.constants.Constants.Companion.OPEN_DATA_URL
import cz.loono.backend.db.model.Category
import cz.loono.backend.db.repository.CategoryRepository
import cz.loono.backend.db.repository.HealthcareProviderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.InputStream
import java.net.URL

@Service
class HealthcareProvidersService @Autowired constructor(
    private val healthcareProviderRepository: HealthcareProviderRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional(rollbackFor = [Exception::class])
    fun updateData() {
        val input = downloadLatestVersion()
        val providers = HealthcareCSVParser().parse(input)
        if (providers.isNotEmpty()) {
            categoryRepository.saveAll(getCategories())
            healthcareProviderRepository.saveAll(providers)
        } else {
            throw LoonoBackendException(
                HttpStatus.UNPROCESSABLE_ENTITY,
                errorCode = null,
                errorMessage = "Data update failed."
            )
        }
    }

    private fun downloadLatestVersion(): InputStream {
        return URL(OPEN_DATA_URL).openStream()
    }

    private fun getCategories(): List<Category> {
        val list = mutableListOf<Category>()
        CategoryValues.values().iterator().forEach {
            list.add(Category(it.value))
        }
        return list
    }
}
