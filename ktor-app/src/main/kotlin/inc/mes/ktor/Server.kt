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
package inc.mes.ktor

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import inc.mes.ktor.data.daos.CustomerDao
import inc.mes.ktor.data.daos.UserDao
import inc.mes.ktor.data.di.dbModules
import inc.mes.ktor.routes.onBoardingRoutes
import inc.mes.ktor.routes.registerCustomerRoutes
import inc.mes.ktor.utils.JwtVars
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

// https://ktor.io/docs/jwt.html#jwt-settings
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = true) {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    // Build app provided dependencies.
    val appModules: Module = org.koin.dsl.module {
        single {
            log
        }
        single {
            JwtVars(
                secret = secret,
                issuer = issuer,
                audience = audience,
                myRealm = myRealm
            )
        }
    }

    // Declare Koin
    install(Koin) {
        SLF4JLogger()
        val koinModules = mutableListOf<Module>().apply {
            add(appModules)
            addAll(dbModules)
        }
        modules(koinModules)
    }
    onBoardingRoutes()
    registerCustomerRoutes()
}
