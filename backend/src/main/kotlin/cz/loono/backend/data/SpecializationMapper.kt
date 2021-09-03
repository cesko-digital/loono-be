package cz.loono.backend.data

import cz.loono.backend.data.constants.CategoryValues
import cz.loono.backend.db.model.Category
import org.slf4j.LoggerFactory

class SpecializationMapper {

    companion object {

        private val logger = LoggerFactory.getLogger(this::class.java)

        fun defineCategory(specialization: String): Set<Category> {

            val categories = mutableSetOf<Category>()
            val specializations = specialization.split(", ")

            specializations.forEach {
                when (it.lowercase()) {
                    "adiktolog", "návykové nemoci" -> {
                        categories.add(Category(CategoryValues.ADDICTIONS.value))
                    }
                    "alergologie a klinická imunologie" -> {
                        categories.add(Category(CategoryValues.ALLERGOLOGY.value))
                        categories.add(Category(CategoryValues.IMMUNOLOGY.value))
                    }
                    "anesteziologie a intenzivní medicína", "intenzívní medicína",
                    "popáleninová medicína", "urgentní medicína" -> {
                        categories.add(Category(CategoryValues.ANESTHESIOLOGY_ARO.value))
                    }
                    "cévní chirurgie", "chirurgie", "dětská chirurgie", "plastická chirurgie",
                    "hrudní chirurgie", "onkochirurgie", "kardiochirurgie", "neurochirurgie",
                    "maxilofaciální chirurgie", "spondylochirurgie" -> {
                        categories.add(Category(CategoryValues.SURGERY.value))
                    }
                    "angiologie" -> {
                        categories.add(Category(CategoryValues.ANGIOLOGY.value))
                    }
                    "endokrinologie", "endokrinologie a diabetologie", "dětská endokrinologie a diabetologie" -> {
                        categories.add(Category(CategoryValues.ENDOCRINOLOGY.value))
                    }
                    "diabetologie" -> {
                        categories.add(Category(CategoryValues.DIABETOLOGY.value))
                    }
                    "dětská dermatovenerologie", "dermatovenerologie", "korektivní dermatologie" -> {
                        categories.add(Category(CategoryValues.DERMATOVENEROLOGY.value))
                    }
                    "dentální hygienistka" -> {
                        categories.add(Category(CategoryValues.DENTAL_HYGIENE.value))
                    }
                    "ergoterapeut" -> {
                        categories.add(Category(CategoryValues.OCCUPATIONAL_THERAPY.value))
                    }
                    "fyzioterapeut", "odborný fyzioterapeut" -> {
                        categories.add(Category(CategoryValues.PHYSIOTHERAPY.value))
                    }
                    "foniatrie" -> {
                        categories.add(Category(CategoryValues.PHONIATRICS.value))
                        categories.add(Category(CategoryValues.ENT.value))
                    }
                    "rehabilitační a fyzikální medicína" -> {
                        categories.add(Category(CategoryValues.REHABILITATION.value))
                    }
                    "dětská gastroenterologie a hepatologie", "gastroenterologie" -> {
                        categories.add(Category(CategoryValues.GASTROENTEROLOGY.value))
                    }
                    "geriatrie" -> {
                        categories.add(Category(CategoryValues.GERIATRICS.value))
                    }
                    "gynekologie a porodnictví", "porodní asistentka" -> {
                        categories.add(Category(CategoryValues.GYNECOLOGY.value))
                    }
                    "gynekologie dětí a dospívajících" -> {
                        categories.add(Category(CategoryValues.GYNECOLOGY.value))
                        categories.add(Category(CategoryValues.PEDIATRICIAN.value))
                    }
                    "onkogynekologie" -> {
                        categories.add(Category(CategoryValues.GYNECOLOGY.value))
                        categories.add(Category(CategoryValues.ONCOLOGY.value))
                    }
                    "urogynekologie" -> {
                        categories.add(Category(CategoryValues.GYNECOLOGY.value))
                        categories.add(Category(CategoryValues.UROLOGY.value))
                    }
                    "reprodukční medicína" -> {
                        categories.add(Category(CategoryValues.REPRODUCTIVE_MEDICINE.value))
                        categories.add(Category(CategoryValues.GYNECOLOGY.value))
                    }
                    "hematologie a transfúzní lékařství" -> {
                        categories.add(Category(CategoryValues.HEMATOLOGY.value))
                    }
                    "dětská onkologie a hematologie" -> {
                        categories.add(Category(CategoryValues.HEMATOLOGY.value))
                        categories.add(Category(CategoryValues.ONCOLOGY.value))
                    }
                    "hygiena a epidemiologie", "epidemiologie", "hygiena výživy a předmětů běžného užívání",
                    "hygiena obecná a komunální", "hygiena dětí a dorostu" -> {
                        categories.add(Category(CategoryValues.HYGIENE.value))
                    }
                    "infekční lékařství" -> {
                        categories.add(Category(CategoryValues.INFECTIOUS_MEDICINE.value))
                    }
                    "vnitřní lékařství" -> {
                        categories.add(Category(CategoryValues.INTERNAL_MEDICINE.value))
                    }
                    "kardiologie", "dětská kardiologie" -> {
                        categories.add(Category(CategoryValues.CARDIOLOGY.value))
                    }
                    "klinický logoped" -> {
                        categories.add(Category(CategoryValues.SPEECH_THERAPY.value))
                    }
                    "hyperbarická a letecká medicína" -> {
                        categories.add(Category(CategoryValues.AERO_MEDICINE.value))
                    }
                    "klinická onkologie" -> {
                        categories.add(Category(CategoryValues.ONCOLOGY.value))
                    }
                    "radiační onkologie" -> {
                        categories.add(Category(CategoryValues.ONCOLOGY.value))
                        categories.add(Category(CategoryValues.RADIOLOGY.value))
                    }
                    "klinická biochemie" -> {
                        categories.add(Category(CategoryValues.BIOCHEMISTRY.value))
                    }
                    "lékařská genetika" -> {
                        categories.add(Category(CategoryValues.GENETICS.value))
                    }
                    "lékařská mikrobiologie" -> {
                        categories.add(Category(CategoryValues.MICROBIOLOGY.value))
                    }
                    "nefrologie", "dětská nefrologie" -> {
                        categories.add(Category(CategoryValues.NEPHROLOGY.value))
                    }
                    "neurologie", "dětská neurologie" -> {
                        categories.add(Category(CategoryValues.NEUROLOGY.value))
                    }
                    "neonatologie", "neonatologie perinatologie a fetomaternální medicína",
                    "perinatologie a fetomaternální medicína" -> {
                        categories.add(Category(CategoryValues.NEONATAL.value))
                    }
                    "oftalmologie", "ortoptista", "optometrista", "zrakový terapeut" -> {
                        categories.add(Category(CategoryValues.OPHTHALMOLOGY.value))
                    }
                    "ortopedie a traumatologie pohybového ústrojí", "traumatologie", "ortopedická protetika",
                    "ortotik-protetik", "klinická osteologie" -> {
                        categories.add(Category(CategoryValues.ORTHOPEDICS.value))
                    }
                    "otorinolaryngologie a chirurgie hlavy a krku", "dětská otorinolaryngologie" -> {
                        categories.add(Category(CategoryValues.ENT.value))
                    }
                    "algeziologie", "paliativní medicína" -> {
                        categories.add(Category(CategoryValues.PALLIATIVE_MEDICINE.value))
                    }
                    "patologie" -> {
                        categories.add(Category(CategoryValues.PATHOLOGY.value))
                    }
                    "pneumologie a ftizeologie", "dětská pneumologie" -> {
                        categories.add(Category(CategoryValues.PNEUMOLOGY.value))
                    }
                    "všeobecné praktické lékařství" -> {
                        categories.add(Category(CategoryValues.GENERAL_PRACTICAL_MEDICINE.value))
                    }
                    "dětské lékařství", "pediatrie", "praktické lékařství pro děti a dorost", "dorostové lékařství" -> {
                        categories.add(Category(CategoryValues.PEDIATRICIAN.value))
                    }
                    "psychiatrie", "dětská a dorostová psychiatrie", "gerontopsychiatrie" -> {
                        categories.add(Category(CategoryValues.PSYCHIATRY.value))
                    }
                    "klinický psycholog", "dětský klinický psycholog", "psycholog" -> {
                        categories.add(Category(CategoryValues.PSYCHOLOGY.value))
                    }
                    "pracovní lékařství", "posudkové lékařství" -> {
                        categories.add(Category(CategoryValues.OCCUPATIONAL_MEDICINE.value))
                    }
                    "radiologie a zobrazovací metody", "intervenční radiologie", "neuroradiologie",
                    "dětská radiologie", "nukleární medicína", "radiologický asistent",
                    "vaskulární intervenční radiologie", "vaskulární inter", "radiologický technik" -> {
                        categories.add(Category(CategoryValues.RADIOLOGY.value))
                    }
                    "onkourologie" -> {
                        categories.add(Category(CategoryValues.ONCOLOGY.value))
                        categories.add(Category(CategoryValues.UROLOGY.value))
                    }
                    "revmatologie", "dětská revmatologie" -> {
                        categories.add(Category(CategoryValues.RHEUMATOLOGY.value))
                    }
                    "urologie", "dětská urologie" -> {
                        categories.add(Category(CategoryValues.UROLOGY.value))
                    }
                    "klinická výživa a intenzivní metabolická péče", "nutriční terapeut", "nutriční podpora" -> {
                        categories.add(Category(CategoryValues.NUTRITION.value))
                    }
                    "sestra pro péči v interních oborech", "sestra pro péči v psychiatrii", "dětská sestra",
                    "všeobecná sestra", "sestra pro intenzivní péči", "praktická sestra" -> {
                        categories.add(Category(CategoryValues.NURSE.value))
                    }
                    "koloproktologie" -> {
                        categories.add(Category(CategoryValues.COLOPROCTOLOGY.value))
                    }
                    "sexuologie" -> {
                        categories.add(Category(CategoryValues.SEXOLOGY.value))
                    }
                    "soudní lékařství" -> {
                        categories.add(Category(CategoryValues.FORENSIC_MEDICINE.value))
                    }
                    "tělovýchovné lékařství" -> {
                        categories.add(Category(CategoryValues.SPORTS_MEDICINE.value))
                    }
                    "zdravotně-sociální pracovník" -> {
                        categories.add(Category(CategoryValues.SOCIAL_WORKER.value))
                    }
                    "zubní lékařství", "klinická stomatologie" -> {
                        categories.add(Category(CategoryValues.DENTIST.value))
                    }
                    "orální a maxilofaciální chirurgie" -> {
                        categories.add(Category(CategoryValues.DENTIST.value))
                        categories.add(Category(CategoryValues.SURGERY.value))
                    }
                    "ortodoncie" -> {
                        categories.add(Category(CategoryValues.ORTHODONTICS.value))
                        categories.add(Category(CategoryValues.DENTIST.value))
                    }
                    "zubní technik", "zubní technik pro fixní a snímatelné náhrady", "zubní technik pro ortodoncii" -> {
                        categories.add(Category(CategoryValues.DENTAL_TECHNICIAN.value))
                    }
                    "biomedicínský technik", "biomedicínský inženýr" -> {
                        categories.add(Category(CategoryValues.BIOMEDICAL_TECHNICIAN.value))
                    }
                    "zdravotní laborant pro klinickou biochemii",
                    "zdravotní laborant pro klinickou hematologii a transfuzní službu",
                    "zdravotní laborant pro toxikologii", "zdravotní laborant pro cytodiagnostiku",
                    "laboratorní a vyšetřovací metody ve zdravotnictví",
                    "zdravotní laborant pro alergologii a klinickou imunologii",
                    "zdravotní laborant pro klinickou genetiku", "zdravotní laborant",
                    "zdravotní laborant pro mikrobiologii" -> {
                        categories.add(Category(CategoryValues.MEDICAL_LABORATORY_TECHNICIAN.value))
                    }
                    "zdravotnický záchranář" -> {
                        categories.add(Category(CategoryValues.PARAMEDIC.value))
                    }
                    "odborný pracovník v laboratorních  metodách a přípravě léčivých přípravků",
                    "odborný pracovník v ochraně a podpoře veřejného zdraví",
                    "asistent ochrany a podpory veřejného zdraví",
                    "odborný pracovník v ochraně a podpoře veřejného zdraví pro hygienu a epidemiologii",
                    "bioanalytik pro mikrobiologii", "bioanalytik pro klinickou genetiku" -> {
                        categories.add(Category(CategoryValues.SPECIALIST.value))
                    }
                    "farmaceutická technologie", "klinická farmacie", "praktické lékárenství", "nemocniční lékárenství",
                    "klinická farmakologie", "farmaceutický asistent", "farmaceutická kontrola",
                    "farmaceutický asistent pro zdravotnické prostředky", "onkologická farmacie",
                    "radiofarmaka" -> {
                        categories.add(Category(CategoryValues.PHARMACOLOGY.value))
                    }
                    "medicína dlouhodobé péče" -> {
                        categories.add(Category(CategoryValues.LDN.value))
                        categories.add(Category(CategoryValues.GERIATRICS.value))
                    }
                    "behaviorální analytik" -> {
                        categories.add(Category(CategoryValues.BEHAVIORAL_ANALYST.value))
                    }
                    "veřejné zdravotnictví" -> {
                        categories.add(Category(CategoryValues.PUBLIC_HEALTHCARE.value))
                    }
                    "radiologický fyzik" -> {
                        categories.add(Category(CategoryValues.RADIOLOGICAL_PHYSICIST.value))
                    }
                    "psychosomatika" -> {
                        categories.add(Category(CategoryValues.PSYCHOSOMATICS.value))
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
}
