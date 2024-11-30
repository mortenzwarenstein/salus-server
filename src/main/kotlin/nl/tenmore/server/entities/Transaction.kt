package nl.tenmore.server.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    val transferred: Float = 0F,
    val credit: Float = 0F,
    val rentDue: Float = 0F,
    val payback: Float = 0F,
    val dateCreated: Date = Date()
)