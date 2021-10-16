package cz.loono.backend.data

import cz.loono.backend.data.constants.CategoryValues
import cz.loono.backend.db.model.HealthcareCategory
import org.slf4j.LoggerFactory

class SpecializationMapper {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun defineCategory(specialization: String): Set<HealthcareCategory> {

        val categories = mutableSetOf<HealthcareCategory>()
        val specializations = specialization.split(", ")

        specializations.forEach {
            when (it.lowercase()) {
                "adiktolog", "návykové nemoci" -> {
                    categories.add(HealthcareCategory(CategoryValues.ADDICTIONS.value))
                }
                "alergologie a klinická imunologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ALLERGOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.IMMUNOLOGY.value))
                }
                "anesteziologie a intenzivní medicína", "intenzívní medicína",
                "popáleninová medicína", "urgentní medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.ANESTHESIOLOGY_ARO.value))
                }
                "cévní chirurgie", "chirurgie", "dětská chirurgie", "plastická chirurgie",
                "hrudní chirurgie", "onkochirurgie", "kardiochirurgie", "neurochirurgie",
                "maxilofaciální chirurgie", "spondylochirurgie" -> {
                    categories.add(HealthcareCategory(CategoryValues.SURGERY.value))
                }
                "angiologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ANGIOLOGY.value))
                }
                "endokrinologie", "endokrinologie a diabetologie", "dětská endokrinologie a diabetologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ENDOCRINOLOGY.value))
                }
                "diabetologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.DIABETOLOGY.value))
                }
                "dětská dermatovenerologie", "dermatovenerologie", "korektivní dermatologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.DERMATOVENEROLOGY.value))
                }
                "dentální hygienistka" -> {
                    categories.add(HealthcareCategory(CategoryValues.DENTAL_HYGIENE.value))
                }
                "ergoterapeut" -> {
                    categories.add(HealthcareCategory(CategoryValues.OCCUPATIONAL_THERAPY.value))
                }
                "fyzioterapeut", "odborný fyzioterapeut" -> {
                    categories.add(HealthcareCategory(CategoryValues.PHYSIOTHERAPY.value))
                }
                "foniatrie" -> {
                    categories.add(HealthcareCategory(CategoryValues.PHONIATRICS.value))
                    categories.add(HealthcareCategory(CategoryValues.ENT.value))
                }
                "rehabilitační a fyzikální medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.REHABILITATION.value))
                }
                "dětská gastroenterologie a hepatologie", "gastroenterologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.GASTROENTEROLOGY.value))
                }
                "geriatrie" -> {
                    categories.add(HealthcareCategory(CategoryValues.GERIATRICS.value))
                }
                "gynekologie a porodnictví", "porodní asistentka" -> {
                    categories.add(HealthcareCategory(CategoryValues.GYNECOLOGY.value))
                }
                "gynekologie dětí a dospívajících" -> {
                    categories.add(HealthcareCategory(CategoryValues.GYNECOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.PEDIATRICIAN.value))
                }
                "onkogynekologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.GYNECOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.ONCOLOGY.value))
                }
                "urogynekologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.GYNECOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.UROLOGY.value))
                }
                "reprodukční medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.REPRODUCTIVE_MEDICINE.value))
                    categories.add(HealthcareCategory(CategoryValues.GYNECOLOGY.value))
                }
                "hematologie a transfúzní lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.HEMATOLOGY.value))
                }
                "dětská onkologie a hematologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.HEMATOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.ONCOLOGY.value))
                }
                "hygiena a epidemiologie", "epidemiologie", "hygiena výživy a předmětů běžného užívání",
                "hygiena obecná a komunální", "hygiena dětí a dorostu" -> {
                    categories.add(HealthcareCategory(CategoryValues.HYGIENE.value))
                }
                "infekční lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.INFECTIOUS_MEDICINE.value))
                }
                "vnitřní lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.INTERNAL_MEDICINE.value))
                }
                "kardiologie", "dětská kardiologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.CARDIOLOGY.value))
                }
                "klinický logoped" -> {
                    categories.add(HealthcareCategory(CategoryValues.SPEECH_THERAPY.value))
                }
                "hyperbarická a letecká medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.AERO_MEDICINE.value))
                }
                "klinická onkologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ONCOLOGY.value))
                }
                "radiační onkologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ONCOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.RADIOLOGY.value))
                }
                "klinická biochemie" -> {
                    categories.add(HealthcareCategory(CategoryValues.BIOCHEMISTRY.value))
                }
                "lékařská genetika" -> {
                    categories.add(HealthcareCategory(CategoryValues.GENETICS.value))
                }
                "lékařská mikrobiologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.MICROBIOLOGY.value))
                }
                "nefrologie", "dětská nefrologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.NEPHROLOGY.value))
                }
                "neurologie", "dětská neurologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.NEUROLOGY.value))
                }
                "neonatologie", "neonatologie perinatologie a fetomaternální medicína",
                "perinatologie a fetomaternální medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.NEONATAL.value))
                }
                "oftalmologie", "ortoptista", "optometrista", "zrakový terapeut" -> {
                    categories.add(HealthcareCategory(CategoryValues.OPHTHALMOLOGY.value))
                }
                "ortopedie a traumatologie pohybového ústrojí", "traumatologie", "ortopedická protetika",
                "ortotik-protetik", "klinická osteologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ORTHOPEDICS.value))
                }
                "otorinolaryngologie a chirurgie hlavy a krku", "dětská otorinolaryngologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ENT.value))
                }
                "algeziologie", "paliativní medicína" -> {
                    categories.add(HealthcareCategory(CategoryValues.PALLIATIVE_MEDICINE.value))
                }
                "patologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.PATHOLOGY.value))
                }
                "pneumologie a ftizeologie", "dětská pneumologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.PNEUMOLOGY.value))
                }
                "všeobecné praktické lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.GENERAL_PRACTICAL_MEDICINE.value))
                }
                "dětské lékařství", "pediatrie", "praktické lékařství pro děti a dorost", "dorostové lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.PEDIATRICIAN.value))
                }
                "psychiatrie", "dětská a dorostová psychiatrie", "gerontopsychiatrie" -> {
                    categories.add(HealthcareCategory(CategoryValues.PSYCHIATRY.value))
                }
                "klinický psycholog", "dětský klinický psycholog", "psycholog" -> {
                    categories.add(HealthcareCategory(CategoryValues.PSYCHOLOGY.value))
                }
                "pracovní lékařství", "posudkové lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.OCCUPATIONAL_MEDICINE.value))
                }
                "radiologie a zobrazovací metody", "intervenční radiologie", "neuroradiologie",
                "dětská radiologie", "nukleární medicína", "radiologický asistent",
                "vaskulární intervenční radiologie", "vaskulární inter", "radiologický technik" -> {
                    categories.add(HealthcareCategory(CategoryValues.RADIOLOGY.value))
                }
                "onkourologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ONCOLOGY.value))
                    categories.add(HealthcareCategory(CategoryValues.UROLOGY.value))
                }
                "revmatologie", "dětská revmatologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.RHEUMATOLOGY.value))
                }
                "urologie", "dětská urologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.UROLOGY.value))
                }
                "klinická výživa a intenzivní metabolická péče", "nutriční terapeut", "nutriční podpora" -> {
                    categories.add(HealthcareCategory(CategoryValues.NUTRITION.value))
                }
                "sestra pro péči v interních oborech", "sestra pro péči v psychiatrii", "dětská sestra",
                "všeobecná sestra", "sestra pro intenzivní péči", "praktická sestra" -> {
                    categories.add(HealthcareCategory(CategoryValues.NURSE.value))
                }
                "koloproktologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.COLOPROCTOLOGY.value))
                }
                "sexuologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.SEXOLOGY.value))
                }
                "soudní lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.FORENSIC_MEDICINE.value))
                }
                "tělovýchovné lékařství" -> {
                    categories.add(HealthcareCategory(CategoryValues.SPORTS_MEDICINE.value))
                }
                "zdravotně-sociální pracovník" -> {
                    categories.add(HealthcareCategory(CategoryValues.SOCIAL_WORKER.value))
                }
                "zubní lékařství", "klinická stomatologie" -> {
                    categories.add(HealthcareCategory(CategoryValues.DENTIST.value))
                }
                "orální a maxilofaciální chirurgie" -> {
                    categories.add(HealthcareCategory(CategoryValues.DENTIST.value))
                    categories.add(HealthcareCategory(CategoryValues.SURGERY.value))
                }
                "ortodoncie" -> {
                    categories.add(HealthcareCategory(CategoryValues.ORTHODONTICS.value))
                    categories.add(HealthcareCategory(CategoryValues.DENTIST.value))
                }
                "zubní technik", "zubní technik pro fixní a snímatelné náhrady", "zubní technik pro ortodoncii" -> {
                    categories.add(HealthcareCategory(CategoryValues.DENTAL_TECHNICIAN.value))
                }
                "biomedicínský technik", "biomedicínský inženýr" -> {
                    categories.add(HealthcareCategory(CategoryValues.BIOMEDICAL_TECHNICIAN.value))
                }
                "zdravotní laborant pro klinickou biochemii",
                "zdravotní laborant pro klinickou hematologii a transfuzní službu",
                "zdravotní laborant pro toxikologii", "zdravotní laborant pro cytodiagnostiku",
                "laboratorní a vyšetřovací metody ve zdravotnictví",
                "zdravotní laborant pro alergologii a klinickou imunologii",
                "zdravotní laborant pro klinickou genetiku", "zdravotní laborant",
                "zdravotní laborant pro mikrobiologii" -> {
                    categories.add(HealthcareCategory(CategoryValues.MEDICAL_LABORATORY_TECHNICIAN.value))
                }
                "zdravotnický záchranář" -> {
                    categories.add(HealthcareCategory(CategoryValues.PARAMEDIC.value))
                }
                "odborný pracovník v laboratorních  metodách a přípravě léčivých přípravků",
                "odborný pracovník v ochraně a podpoře veřejného zdraví",
                "asistent ochrany a podpory veřejného zdraví",
                "odborný pracovník v ochraně a podpoře veřejného zdraví pro hygienu a epidemiologii",
                "bioanalytik pro mikrobiologii", "bioanalytik pro klinickou genetiku" -> {
                    categories.add(HealthcareCategory(CategoryValues.SPECIALIST.value))
                }
                "farmaceutická technologie", "klinická farmacie", "praktické lékárenství", "nemocniční lékárenství",
                "klinická farmakologie", "farmaceutický asistent", "farmaceutická kontrola",
                "farmaceutický asistent pro zdravotnické prostředky", "onkologická farmacie",
                "radiofarmaka" -> {
                    categories.add(HealthcareCategory(CategoryValues.PHARMACOLOGY.value))
                }
                "medicína dlouhodobé péče" -> {
                    categories.add(HealthcareCategory(CategoryValues.LDN.value))
                    categories.add(HealthcareCategory(CategoryValues.GERIATRICS.value))
                }
                "behaviorální analytik" -> {
                    categories.add(HealthcareCategory(CategoryValues.BEHAVIORAL_ANALYST.value))
                }
                "veřejné zdravotnictví" -> {
                    categories.add(HealthcareCategory(CategoryValues.PUBLIC_HEALTHCARE.value))
                }
                "radiologický fyzik" -> {
                    categories.add(HealthcareCategory(CategoryValues.RADIOLOGICAL_PHYSICIST.value))
                }
                "psychosomatika" -> {
                    categories.add(HealthcareCategory(CategoryValues.PSYCHOSOMATICS.value))
                }
                "", "oborpece" -> {
                    // No specialization
                }
                else -> {
                    logger.warn("Unknown category $it")
                }
            }
        }

        return categories
    }
}
