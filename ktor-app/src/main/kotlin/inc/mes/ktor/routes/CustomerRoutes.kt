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

import inc.mes.ktor.data.*
import inc.mes.ktor.data.daos.CustomerDao
import inc.mes.ktor.data.daos.UserDao
import inc.mes.ktor.data.models.Customer
import inc.mes.ktor.routes.mappers.toCustomer
import inc.mes.ktor.routes.mappers.toUser
import inc.mes.ktor.routes.serializers.CustomerSerializer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.first

fun Route.customerRouting(application: Application, customerDao: CustomerDao = CustomerDao()) {
    route("/customer/") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.NotFound)
            }
        }
        get("{id}/") {
            application.log.info("get up request")
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer = customerDao.get(id.toIntOrNull() ?: 0).first()
                ?: return@get call.respondText(
                    "No customer with id: $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receiveOrNull<CustomerSerializer>()
                ?: return@post call.respond(
                    message = "Invalid request!",
                    status = HttpStatusCode.BadRequest
                )
            val savedCustomer = customer.toCustomer().apply {
                id = customerDao.insert(this)
            }
            call.respond(message = savedCustomer, status = HttpStatusCode.Created)
        }
        delete("{id}/") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val customer = customerDao.get(id.toIntOrNull() ?: 0).first()
                ?: return@delete call.respondText(
                    "No customer with id: $id",
                    status = HttpStatusCode.NotFound
                )
            customerDao.delete(customer)
            return@delete call.respondText(
                "Customer deleted successfully."
            )
        }
    }
}

fun Application.registerCustomerRoutes() {
    routing {
        customerRouting(this@registerCustomerRoutes)
    }
}
