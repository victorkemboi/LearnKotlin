package inc.mes.ktor.utils

import kotlinx.datetime.*
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat

/**
 * Kotlinx datetime extensions
 */
fun getLocalDateTimeNow() = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault())

fun LocalDateTime.isTokenExpired(): Boolean =
    getLocalDateTimeNow().toJodaDateTime().isAfter(this.toJodaDateTime())

fun LocalDateTime.toJodaDateTime(): DateTime =
    DateTime(year, monthNumber, dayOfMonth, hour, minute, second)


/**
 * Joda Time extensions
 */
fun DateTime.toIso(): String = ISODateTimeFormat.dateTime().print(this)

fun DateTime.toKtDateTime(): LocalDateTime? = try {
    LocalDateTime.parse(this.toIso())
} catch (e: IllegalArgumentException) {
    null
}