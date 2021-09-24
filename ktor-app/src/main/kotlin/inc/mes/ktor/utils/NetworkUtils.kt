package inc.mes.ktor.utils

import io.ktor.application.*
import io.ktor.request.*

suspend inline fun <reified T : Any> ApplicationCall.receiveOrNull(): T? {
    return try {
        this.receive()
    } catch (exception: ContentTransformationException) {
        null
    }
}