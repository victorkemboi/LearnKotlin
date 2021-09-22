package inc.mes.ktor.data

import inc.mes.ktor.data.entity.*

val customerStorage = mutableListOf<Customer>()
val userStorage = hashMapOf<String,User>()
val userTokenStorage = hashMapOf<User,Token>()
