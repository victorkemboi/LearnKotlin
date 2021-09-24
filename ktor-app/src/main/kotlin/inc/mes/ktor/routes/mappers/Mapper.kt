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