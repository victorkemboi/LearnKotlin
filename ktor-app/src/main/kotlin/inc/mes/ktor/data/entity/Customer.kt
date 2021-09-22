package inc.mes.ktor.data.entity

import kotlinx.serialization.*

@Serializable
data class Customer(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
    )