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
import inc.mes.ktor.data.daos.UserDao
import inc.mes.ktor.data.models.Token
import inc.mes.ktor.data.userTokenStorage
import inc.mes.ktor.routes.mappers.toUser
import inc.mes.ktor.routes.serializers.AuthSerializer
import inc.mes.ktor.utils.models.JwtVars
import inc.mes.ktor.utils.getLocalDateTimeNow
import inc.mes.ktor.utils.isBefore
import inc.mes.ktor.utils.toJavaDate
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.ktor.ext.inject
import org.slf4j.Logger

/***
 * A route to add a user to the database.
 */
fun Route.signUpRoute() {
    val userDao: UserDao by inject()
    val log: Logger by inject()
    route("/auth/signup/") {
        post {
            log.info("Sign up request")
            call.application.environment.log.info("Hello from signup")
            val user = call.receiveOrNull<AuthSerializer>()
                ?: return@post call.respond(
                    message = "Invalid request!",
                    status = HttpStatusCode.BadRequest
                )
            log.info("Sign up request: $user")
            val savedUser = user.toUser().apply {
                id = userDao.insert(this)
            }
            call.respond(status = HttpStatusCode.Created, message = savedUser)
        }
    }
}

/***
 * A route to login a user and set a JWT Bearer Token.
 */
@OptIn(ExperimentalTime::class)
fun Route.loginRoute() {
    val log: Logger by inject()
    val userDao: UserDao by inject()
    val jwtVars: JwtVars by inject()
    route("/auth/login/") {
        post {
            log.info("Login request")
            val user = call.receiveOrNull<AuthSerializer>()
                ?: return@post call.respond(
                    message = "Invalid request!",
                    status = HttpStatusCode.BadRequest
                )
            // Check username and password
            val existingUser = userDao.getByUsername(username = user.username).first()
                ?: return@post call.respond(
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
                existingToken.expiry.isBefore(getLocalDateTimeNow())
            ) {
                val tokenExpiry = (Clock.System.now() + Duration.days(1)).toLocalDateTime(
                    TimeZone.currentSystemDefault()
                )
                val token = JWT.create()
                    .withAudience(jwtVars.audience)
                    .withIssuer(jwtVars.issuer)
                    .withClaim("username", user.username)
                    .withExpiresAt(tokenExpiry.toJavaDate())
                    .sign(Algorithm.HMAC256(jwtVars.secret))
                existingToken = Token(
                    user = existingUser,
                    token = token,
                    expiry = tokenExpiry
                )
                userTokenStorage[existingUser] = existingToken
            }
            call.respond(existingToken)
        }
    }
}

@OptIn(ExperimentalTime::class)
fun Application.onBoardingRoutes() {
    routing {
        signUpRoute()
        loginRoute()
    }
}
