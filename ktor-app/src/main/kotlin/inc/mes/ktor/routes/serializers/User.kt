package inc.mes.ktor.routes.serializers

import kotlinx.serialization.*

@Serializable
data class SignUpSerializer(
    val username: String,
    val password: String
)