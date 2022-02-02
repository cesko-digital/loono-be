package cz.loono.backend.api.controller

import cz.loono.backend.api.dto.ExaminationIdDto
import cz.loono.backend.api.dto.ExaminationRecordDto
import cz.loono.backend.api.dto.ExaminationTypeEnumDto
import cz.loono.backend.api.service.ExaminationRecordService
import cz.loono.backend.api.service.PreventionService
import cz.loono.backend.createAccount
import cz.loono.backend.createBasicUser
import cz.loono.backend.db.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(value = [ExaminationRecordService::class, PreventionService::class])
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ExaminationsControllerTest {

    @Autowired
    private lateinit var recordService: ExaminationRecordService
    @Autowired
    private lateinit var preventionService: PreventionService
    @Autowired
    private lateinit var repo: AccountRepository

    @Test
    fun `Should add badge and points`() {
        val controller = ExaminationsController(recordService, preventionService)
        val basicUser = createBasicUser()
        val existingAccount = createAccount()

        val examinationRecord = ExaminationRecordDto(
            type = ExaminationTypeEnumDto.GENERAL_PRACTITIONER
        )
        repo.save(existingAccount)

        val examUUID = controller.updateOrCreate(basicUser, examinationRecord).uuid!!
        controller.confirm(basicUser, ExaminationTypeEnumDto.DENTIST.toString(), ExaminationIdDto(examUUID))
        repo.findByUid("uid")
        controller.confirm(basicUser, ExaminationTypeEnumDto.DENTIST.toString(), ExaminationIdDto(examUUID))
        repo.findByUid("uid")

    }

}