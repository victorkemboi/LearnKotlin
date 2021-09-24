package inc.mes.ktor.utils

data class JwtVars(
    val secret: String,
    val issuer: String,
    val audience: String,
    val myRealm: String,
)
