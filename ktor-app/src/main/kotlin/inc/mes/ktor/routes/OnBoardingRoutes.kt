package inc.mes.ktor.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import inc.mes.ktor.data.*
import inc.mes.ktor.data.entity.Token
import inc.mes.ktor.data.entity.User
import inc.mes.ktor.utils.getLocalDateTimeNow
import inc.mes.ktor.utils.isTokenExpired
import inc.mes.ktor.utils.toKtDateTime
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.joda.time.DateTime
import java.util.*


fun Application.registerOnBoardingRoutes() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    routing {
        fun Route.onBoardingRouting() {
            route("/auth/login/") {
                post {
                    val user = call.receive<User>()
                    // Check username and password
                    val existingUser = userStorage[user.username] ?: return@post call.respond(
                        message = "User doesn't exist",
                        status = HttpStatusCode.NotFound
                    )
                    if (!existingUser.password.contentEquals(user.password)) {
                        return@post call.respond(
                            message = "Invalid credentials!",
                            status = HttpStatusCode.BadRequest
                        )
                    }
                    var existingToken = userTokenStorage[existingUser]
                    if (existingToken == null || existingToken.expiry.isTokenExpired()) {
                        val tokenExpiry = DateTime.now().plus(60000)
                        val token = JWT.create()
                            .withAudience(audience)
                            .withIssuer(issuer)
                            .withClaim("username", user.username)
                            .withExpiresAt(tokenExpiry.toDate())
                            .sign(Algorithm.HMAC256(secret))
                        existingToken = Token(
                            user = existingUser,
                            token = token,
                            expiry = tokenExpiry.toKtDateTime() ?: getLocalDateTimeNow()
                        )
                    }
                    call.respond(existingToken)
                }
            }
            route("/auth/signup/") {
                post {
                    log.info("Sign up request")
                    call.application.environment.log.info("Hello from signup")
                    val user = call.receive<User>()
                    log.info("Sign up request: $user")
                    userStorage[user.username] = user
                    call.respond(status = HttpStatusCode.Created, message = user)
                }
            }
        }
    }
}