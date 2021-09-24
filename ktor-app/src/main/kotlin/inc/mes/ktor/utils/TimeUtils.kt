package inc.mes.ktor.utils

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*
import kotlin.time.ExperimentalTime

/**
 * Kotlinx datetime extensions
 */
fun getLocalDateTimeNow(timezone: TimeZone = TimeZone.currentSystemDefault()) =
    Clock.System.now().toLocalDateTime(timeZone = timezone)

@OptIn(ExperimentalTime::class)
fun LocalDateTime.isAfter(other: LocalDateTime, timezone: TimeZone = TimeZone.currentSystemDefault()): Boolean = (
        other.toInstant(timeZone = timezone)
                - this.toInstant(timeZone = timezone)
        ).isNegative()

@OptIn(ExperimentalTime::class)
fun LocalDateTime.isBefore(other: LocalDateTime, timezone: TimeZone = TimeZone.currentSystemDefault()): Boolean = (
        other.toInstant(timeZone = timezone)
                - this.toInstant(timeZone = timezone)
        ).isPositive()

@OptIn(ExperimentalTime::class)
fun Instant.isAfter(other: Instant): Boolean = (other - this).isNegative()

@OptIn(ExperimentalTime::class)
fun Instant.isBefore(other: Instant): Boolean = (other - this).isPositive()

fun LocalDateTime.toJavaDate(timezone: TimeZone = TimeZone.currentSystemDefault()): Date = Date.from(
    this.toInstant(timeZone = timezone).toJavaInstant()
)