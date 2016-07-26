package io.whatthedill.pcre.telemetry.capture

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "telemetry_session")
open class Session() {
    @Id
    @Type(type="uuid-char")
    lateinit var id: UUID

    lateinit var name: String

    lateinit var created: Date

    override fun toString(): String {
        return "Session(id=$id, name='$name', created=$created)"
    }
}