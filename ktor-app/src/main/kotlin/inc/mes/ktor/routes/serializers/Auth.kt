package inc.mes.ktor.routes.serializers

import kotlinx.serialization.*

@Serializable
data class AuthSerializer(
    val username: String,
    val password: String
)