/*
 * Copyright 2021 Mes Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package inc.mes.ktor.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import inc.mes.ktor.data.entity.Token
import inc.mes.ktor.data.entity.User
import inc.mes.ktor.data.userStorage
import inc.mes.ktor.data.userTokenStorage
import inc.mes.ktor.utils.getLocalDateTimeNow
import inc.mes.ktor.utils.isAfter
import inc.mes.ktor.utils.toJavaDate
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalTime::class)
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
                    if (existingToken == null ||
                        existingToken.expiry.isAfter(getLocalDateTimeNow())
                    ) {
                        val tokenExpiry = (Clock.System.now() + Duration.days(1)).toLocalDateTime(
                            TimeZone.currentSystemDefault()
                        )
                        val token = JWT.create()
                            .withAudience(audience)
                            .withIssuer(issuer)
                            .withClaim("username", user.username)
                            .withExpiresAt(tokenExpiry.toJavaDate())
                            .sign(Algorithm.HMAC256(secret))
                        existingToken = Token(
                            user = existingUser,
                            token = token,
                            expiry = tokenExpiry
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
