package inc.mes.ktor.routes.mappers

import inc.mes.ktor.data.models.User
import inc.mes.ktor.routes.serializers.SignUpSerializer

fun SignUpSerializer.toUser() = User(
    id = 0,
    username = this.username,
    password = this.password
)