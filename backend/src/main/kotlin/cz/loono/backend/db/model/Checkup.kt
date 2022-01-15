package cz.loono.backend.db.model

import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
@Table(name = "\"checkup\"")
data class Checkup(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    @Column(nullable = false)
    var state: CheckupState,

    //the default checkups are initialized when the user signed up, therefore this field should also be auto-initialized
    // with the specialist the checkUp is related to
    @Column(nullable = false)
    var specialist: String = "",

    //TODO should be updated to Period, which requires a Hibernate mapping
    @Column(nullable = false)
    var visitInterval: String = "",

    @Column(nullable = true)
    val lastVisit: LocalDate? = null,

    @Column(nullable = true)
    var plannedVisit: LocalDate? = null,

    //is getting initialized once the checkup received the toNotify status
    @OneToOne
    var healthcareProvider: HealthcareProvider = HealthcareProvider(),

    @ManyToOne(optional = false)
    var account: Account = Account(),
) {
    constructor() : this(state = CheckupState.TO_CREATE)

    constructor(specialist: String, visitInterval: String, account: Account) : this() {
        this.state = CheckupState.TO_CREATE
        this.specialist = specialist;
        this.visitInterval = visitInterval;
        this.account = account;
    }

    constructor(plannedVisit: LocalDate?, healthcareProvider: HealthcareProvider) : this() {
        this.state = CheckupState.TO_REMIND;
        this.plannedVisit = plannedVisit;
        this.healthcareProvider = healthcareProvider;
    }

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , healthcareProvider = $healthcareProvider , account = $account )"
    }

}