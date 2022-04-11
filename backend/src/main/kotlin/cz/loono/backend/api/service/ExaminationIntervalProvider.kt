package cz.loono.backend.api.service

import cz.loono.backend.api.dto.ExaminationTypeDto
import cz.loono.backend.api.dto.SexDto

/**
 * This object in fact wraps the provided excel with code, so it's a statical set of rules
 * that return collection of required Examinations and their requested intervals.
 */
object ExaminationIntervalProviderV1 {

    fun findExaminationRequests(patient: PatientV1): List<ExaminationIntervalV1> =
        rules.mapNotNull { preventionRule ->
            val intervals = when (patient.sex) {
                SexDto.MALE -> preventionRule.intervalsMale
                SexDto.FEMALE -> preventionRule.intervalsFemale
            }

            val validInterval = intervals.find { ageInterval ->
                ageInterval.fromAge <= patient.age &&
                    (ageInterval.toAge == null || ageInterval.toAge >= patient.age)
            }

            if (validInterval != null) {
                ExaminationIntervalV1(
                    preventionRule.examinationType,
                    validInterval.intervalYears,
                    preventionRule.priority
                )
            } else null // no prevention required for patients age and sex
        }

    private val rules = listOf(
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.GENERAL_PRACTITIONER,
            intervalsMale = listOf(AgeIntervalV1(fromAge = 19, intervalYears = 2)),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 19, intervalYears = 2)),
            priority = 1
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.MAMMOGRAM,
            intervalsMale = listOf(),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 45, intervalYears = 2)),
            priority = 2
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.GYNECOLOGIST,
            intervalsMale = listOf(),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 15, intervalYears = 1)),
            priority = 3
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.COLONOSCOPY,
            intervalsMale = listOf(AgeIntervalV1(fromAge = 50, intervalYears = 10)),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 50, intervalYears = 10)),
            priority = 4
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.UROLOGIST,
            intervalsMale = listOf(AgeIntervalV1(fromAge = 50, intervalYears = 1)),
            intervalsFemale = listOf(),
            priority = 5
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.DERMATOLOGIST,
            intervalsMale = listOf(AgeIntervalV1(fromAge = 19, intervalYears = 1)),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 19, intervalYears = 1)),
            priority = 6
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.ULTRASOUND_BREAST,
            intervalsMale = listOf(),
            intervalsFemale = listOf(AgeIntervalV1(fromAge = 19, toAge = 44, intervalYears = 2)),
            priority = 7
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.DENTIST,
            intervalsMale = listOf(
                AgeIntervalV1(fromAge = 19, intervalYears = 1)
            ),
            intervalsFemale = listOf(
                AgeIntervalV1(fromAge = 19, intervalYears = 1)
            ),
            priority = 8
        ),
        PreventionRuleV1(
            examinationType = ExaminationTypeDto.OPHTHALMOLOGIST,
            intervalsMale = listOf(
                AgeIntervalV1(fromAge = 19, toAge = 44, intervalYears = 2),
                AgeIntervalV1(fromAge = 45, toAge = 61, intervalYears = 4),
                AgeIntervalV1(fromAge = 62, intervalYears = 2)
            ),
            intervalsFemale = listOf(
                AgeIntervalV1(fromAge = 19, toAge = 44, intervalYears = 2),
                AgeIntervalV1(fromAge = 45, toAge = 61, intervalYears = 4),
                AgeIntervalV1(fromAge = 62, intervalYears = 2)
            ),
            priority = 9
        )
    )
}

data class ExaminationIntervalV1(
    val examinationType: ExaminationTypeDto,
    val intervalYears: Int,
    val priority: Int
)

data class PatientV1(
    val age: Int,
    val sex: SexDto,
)

data class PreventionRuleV1(
    val examinationType: ExaminationTypeDto,
    val intervalsMale: List<AgeIntervalV1>,
    val intervalsFemale: List<AgeIntervalV1>,
    val priority: Int
)

data class AgeIntervalV1(
    val fromAge: Int,
    val toAge: Int? = null,
    val intervalYears: Int
)
