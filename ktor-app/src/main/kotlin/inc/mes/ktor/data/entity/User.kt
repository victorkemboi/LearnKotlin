package inc.mes.ktor.data.entity

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import inc.mes.ktor.utils.getLocalDateTimeNow

@Serializable
open class BaseEntity() {
    val createdAt : LocalDateTime = getLocalDateTimeNow()
}

@Serializable
data class User(
    val id: String,
    val username: String,
    val password: String
)

@Serializable
data class  Token(
    val user: User,
    val token: String,
    val expiry: LocalDateTime
)


