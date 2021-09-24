package inc.mes.ktor.routes.serializers

import kotlinx.serialization.Serializable

@Serializable
data class CustomerSerializer(
    var firstName: String,
    var lastName: String,
    var email: String,
)
