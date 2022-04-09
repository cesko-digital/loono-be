package cz.loono.backend.api.v1.service

import cz.loono.backend.api.dto.ExaminationTypeDto
import cz.loono.backend.api.dto.SexDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExaminationIntervalProviderV1Test {
    @Test
    fun `female 19`() {
        Assertions.assertEquals(
            listOf(
                ExaminationIntervalV1(ExaminationTypeDto.GENERAL_PRACTITIONER, 2, 1),
                ExaminationIntervalV1(ExaminationTypeDto.GYNECOLOGIST, 1, 3),
                ExaminationIntervalV1(ExaminationTypeDto.DERMATOLOGIST, 1, 6),
                ExaminationIntervalV1(ExaminationTypeDto.ULTRASOUND_BREAST, 2, 7),
                ExaminationIntervalV1(ExaminationTypeDto.DENTIST, 1, 8),
                ExaminationIntervalV1(ExaminationTypeDto.OPHTHALMOLOGIST, 2, 9),
            ),
            ExaminationIntervalProviderV1.findExaminationRequests(PatientV1(19, SexDto.FEMALE)),
        )
    }

    @Test
    fun `female 50`() {
        Assertions.assertEquals(
            listOf(
                ExaminationIntervalV1(ExaminationTypeDto.GENERAL_PRACTITIONER, 2, 1),
                ExaminationIntervalV1(ExaminationTypeDto.MAMMOGRAM, 2, 2),
                ExaminationIntervalV1(ExaminationTypeDto.GYNECOLOGIST, 1, 3),
                ExaminationIntervalV1(ExaminationTypeDto.COLONOSCOPY, 10, 4),
                ExaminationIntervalV1(ExaminationTypeDto.DERMATOLOGIST, 1, 6),
                ExaminationIntervalV1(ExaminationTypeDto.DENTIST, 1, 8),
                ExaminationIntervalV1(ExaminationTypeDto.OPHTHALMOLOGIST, 4, 9),
            ),
            ExaminationIntervalProviderV1.findExaminationRequests(PatientV1(50, SexDto.FEMALE)),
        )
    }

    @Test
    fun `male 19`() {
        Assertions.assertEquals(
            listOf(
                ExaminationIntervalV1(ExaminationTypeDto.GENERAL_PRACTITIONER, 2, 1),
                ExaminationIntervalV1(ExaminationTypeDto.DERMATOLOGIST, 1, 6),
                ExaminationIntervalV1(ExaminationTypeDto.DENTIST, 1, 8),
                ExaminationIntervalV1(ExaminationTypeDto.OPHTHALMOLOGIST, 2, 9),
            ),
            ExaminationIntervalProviderV1.findExaminationRequests(PatientV1(19, SexDto.MALE)),
        )
    }

    @Test
    fun `male 70`() {
        Assertions.assertEquals(
            listOf(
                ExaminationIntervalV1(ExaminationTypeDto.GENERAL_PRACTITIONER, 2, 1),
                ExaminationIntervalV1(ExaminationTypeDto.COLONOSCOPY, 10, 4),
                ExaminationIntervalV1(ExaminationTypeDto.UROLOGIST, 1, 5),
                ExaminationIntervalV1(ExaminationTypeDto.DERMATOLOGIST, 1, 6),
                ExaminationIntervalV1(ExaminationTypeDto.DENTIST, 1, 8),
                ExaminationIntervalV1(ExaminationTypeDto.OPHTHALMOLOGIST, 2, 9),
            ),
            ExaminationIntervalProviderV1.findExaminationRequests(PatientV1(70, SexDto.MALE)),
        )
    }
}
