package cz.loono.backend.db.model

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne
import javax.persistence.Table
import org.hibernate.envers.Audited

@Entity
@Table(name = "\"badge\"")
@Audited
data class Badge(
    @Id
    val type: String,
    @Id
    val account_id: Long,
    var level: Int,
    val updatedOnDate: LocalDateTime,
    @ManyToOne(optional = false)
    @JoinColumns(JoinColumn(name = "account_id", insertable = false, updatable = false))
    val account: Account = Account(),
): Serializable