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
package inc.mes.ktor.routes.mappers

import inc.mes.ktor.data.models.Customer
import inc.mes.ktor.data.models.User
import inc.mes.ktor.routes.serializers.AuthSerializer
import inc.mes.ktor.routes.serializers.CustomerSerializer

fun AuthSerializer.toUser() = User(
    id = 0,
    username = this.username,
    password = this.password
)

fun CustomerSerializer.toCustomer() = Customer(
    id = 0,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email
)
