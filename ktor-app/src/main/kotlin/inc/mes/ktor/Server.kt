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
import inc.mes.ktor.data.di.dbModules
import inc.mes.ktor.routes.onBoardingRoutes
import inc.mes.ktor.routes.registerCustomerRoutes
import inc.mes.ktor.utils.models.JwtVars
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

// https://ktor.io/docs/jwt.html#jwt-settings
/***
 * The entry point to the application. Set the main execution to Netty Engine.
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/***
 * Configure Ktor Application via the [module] extension function.
 * Environment variables and other execution configs be set in the *Application.conf* file.
 */
fun Application.module(testing: Boolean = true) {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    /***
     * Set and configure serialization component here.
     * Selected library for conversion is [gson]
     */
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            disableHtmlEscaping()
        }
    }

    /***
     * Setup Authentication for the server.
     * Selected auth library is [jwt].
     */
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

    /***
     * Setup Koin and its dependency graph
     */
    install(Koin) {
        SLF4JLogger()
        val koinModules = mutableListOf<Module>().apply {
            add(
                getKoinAppModule(
                    secret, issuer, audience, myRealm
                )
            )
            addAll(dbModules)
        }
        modules(koinModules)
    }
    onBoardingRoutes()
    registerCustomerRoutes()
}

/***
 * Provide dependencies to koin from the [Application]
 */
fun Application.getKoinAppModule(
    secret: String,
    issuer: String,
    audience: String,
    myRealm: String,
): Module = org.koin.dsl.module {
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
