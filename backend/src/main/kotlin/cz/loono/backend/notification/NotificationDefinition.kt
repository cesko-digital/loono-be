package cz.loono.backend.notification

import cz.loono.backend.api.dto.BadgeTypeDto
import cz.loono.backend.api.dto.ExaminationTypeDto
import cz.loono.backend.api.dto.SexDto
import cz.loono.backend.api.exception.LoonoBackendException
import cz.loono.backend.db.model.Account
import org.springframework.http.HttpStatus

object NotificationDefinition {

    private const val ONESIGNAL_APP_ID = "234d9f26-44c2-4752-b2d3-24bd93059267"
    private const val MORNING_TIME_TO_NOTIFY = "8:00AM"
    private const val EVENING_TIME_TO_NOTIFY = "6:00PM"

    fun getPreventionNotification(accounts: Set<Account>): PushNotification {
        val name = "Prevevention notification"
        val title = "Hola, hola, prevence volá!"
        val text = "Mrkni, na které preventivní prohlídky se objednat."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = MORNING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "main")
        )
    }

    fun getCompletionNotification(
        accounts: Set<Account>,
        time: String,
        examinationTypeDto: ExaminationTypeDto
    ): PushNotification {
        val name = "Complete checkup notification"
        val title = "Byl/a jsi na prohlídce?"
        val text = "Potvrď preventivní prohlídku v aplikaci a získej odměnu."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = time, // time of the past exam - reminder after 24h
            data = NotificationData(screen = "checkup", examinationType = examinationTypeDto)
        )
    }

    fun getOrderNewExam2MonthsAheadNotification(
        accounts: Set<Account>,
        examinationTypeDto: ExaminationTypeDto,
        interval: Int
    ): PushNotification {
        val name = "Order reminder 2 months ahead notification"
        val title = "Objednej se ${translateTypeToCzech(examinationTypeDto)}"
        val text = "${intervalToCzech(interval)} utekl/y jako voda, je čas se objednat na preventivní prohlídku."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = MORNING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "checkup", examinationType = examinationTypeDto)
        )
    }

    fun getOrderNewExamMonthAheadNotification(
        accounts: Set<Account>,
        examinationTypeDto: ExaminationTypeDto,
        interval: Int,
        badgeTypeDto: BadgeTypeDto
    ): PushNotification {
        val name = "Order reminder 1 month ahead notification"
        val title = "Nejvyšší čas zajít ${translateTypeToCzech(examinationTypeDto)}"
        val text =
            "Jsou/je to už ${intervalToCzech(interval)} od poslední prohlídky. Objednej se ještě dnes, ať neztratíš " +
                "${translateTypeToCzech(badgeTypeDto)}."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = MORNING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "checkup", examinationType = examinationTypeDto)
        )
    }

    fun getComingVisitNotification(
        accounts: Set<Account>,
        examinationTypeDto: ExaminationTypeDto,
        time: String
    ): PushNotification {
        val name = "Coming visit notification"
        val title = "Zítra tě čeká prohlídka"
        val text = "Za 24 hodin jdeš ${translateTypeToCzech(examinationTypeDto)} na preventivní prohlídku."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = time, // time of the coming exam - reminder 24h ahead
            data = NotificationData(screen = "checkup", examinationType = examinationTypeDto)
        )
    }

    fun getFirstSelfExamNotification(accounts: Set<Account>): PushNotification {
        val name = "First self-exam notification"
        val title = "První samovyšetření čeká"
        val text = "Zvládne ho opravu každý a nezabere to ani pět minut."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = EVENING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "self")
        )
    }

    fun getSelfExamNotification(accounts: Set<Account>, sex: SexDto): PushNotification {
        val name = "Self-exam notification"
        val title = "Je čas na samovyšetření"
        val text = "Po měsíci přišel čas si sáhnout na ${selfExamObjects(sex)[0]}."
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = EVENING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "self")
        )
    }

    fun getSelfExamIssueResultNotification(accounts: Set<Account>, sex: SexDto): PushNotification {
        val name = "Issue result of self-exam notification"
        val title = "Máš ${selfExamObjects(sex)[0]} zdravá?"
        val text = "Před časem se ti na ${selfExamObjects(sex)[1]} něco nezdálo. Jak dopadla prohlídka u lékaře?"
        return PushNotification(
            appId = ONESIGNAL_APP_ID,
            name = name,
            headings = MultipleLangString(cs = title, en = title),
            contents = MultipleLangString(cs = text, en = text),
            includeExternalUserIds = accounts.map { it.uid },
            scheduleTimeOfDay = EVENING_TIME_TO_NOTIFY,
            data = NotificationData(screen = "self")
        )
    }

    private fun translateTypeToCzech(typeDto: ExaminationTypeDto): String =
        when (typeDto) {
            ExaminationTypeDto.GENERAL_PRACTITIONER -> "k praktickému lékaři"
            ExaminationTypeDto.DENTIST -> "k zubaři"
            ExaminationTypeDto.GYNECOLOGIST -> "ke gynekologovi"
            ExaminationTypeDto.COLONOSCOPY -> "na kolonoskopii"
            ExaminationTypeDto.DERMATOLOGIST -> "k dermatologovi"
            ExaminationTypeDto.MAMMOGRAM -> "na mamograf"
            ExaminationTypeDto.TOKS -> "na TOKS"
            ExaminationTypeDto.OPHTHALMOLOGIST -> "k očnímu lékaři"
            ExaminationTypeDto.ULTRASOUND_BREAST -> "na ultrazvuk prsu"
            ExaminationTypeDto.UROLOGIST -> "k urologovi"
            ExaminationTypeDto.VENEREAL_DISEASES -> "na kontrolu"
        }

    private fun translateTypeToCzech(badgeTypeDto: BadgeTypeDto): String =
        when (badgeTypeDto) {
            BadgeTypeDto.COAT -> "plášť"
            BadgeTypeDto.SHIELD -> "štít"
            BadgeTypeDto.GLASSES -> "brýle"
            BadgeTypeDto.HEADBAND -> "čelenka"
            BadgeTypeDto.GLOVES -> "rukavice"
            BadgeTypeDto.BELT -> "pásek"
            BadgeTypeDto.SHOES -> "boty"
            BadgeTypeDto.TOP -> "brnění"
        }

    private fun intervalToCzech(interval: Int): String =
        when (interval) {
            1 -> "$interval rok"
            2, 3, 4 -> "$interval roky"
            in 5..100 -> "$interval let"
            else -> throw LoonoBackendException(HttpStatus.BAD_REQUEST)
        }

    private fun selfExamObjects(sex: SexDto): Array<String> =
        when (sex) {
            SexDto.MALE -> arrayOf("varlata", "varlatech")
            SexDto.FEMALE -> arrayOf("prsa", "prsou")
        }
}
