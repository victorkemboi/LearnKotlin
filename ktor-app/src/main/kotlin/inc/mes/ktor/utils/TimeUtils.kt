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
package inc.mes.ktor.utils

import java.util.*
import kotlin.time.ExperimentalTime
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone

/**
 * Kotlinx datetime extensions
 */
fun getLocalDateTimeNow(timezone: TimeZone = TimeZone.currentSystemDefault()) =
    Clock.System.now().toLocalDateTime(timeZone = timezone)

@OptIn(ExperimentalTime::class)
fun LocalDateTime.isAfter(
    other: LocalDateTime,
    timezone: TimeZone = TimeZone.currentSystemDefault()
): Boolean = (
    other.toInstant(timeZone = timezone) -
        this.toInstant(timeZone = timezone)
    ).isNegative()

@OptIn(ExperimentalTime::class)
fun LocalDateTime.isBefore(
    other: LocalDateTime,
    timezone: TimeZone = TimeZone.currentSystemDefault()
): Boolean = (
    other.toInstant(timeZone = timezone) -
        this.toInstant(timeZone = timezone)
    ).isPositive()

@OptIn(ExperimentalTime::class)
fun Instant.isAfter(other: Instant): Boolean = (other - this).isNegative()

@OptIn(ExperimentalTime::class)
fun Instant.isBefore(other: Instant): Boolean = (other - this).isPositive()

fun LocalDateTime.toJavaDate(timezone: TimeZone = TimeZone.currentSystemDefault()): Date =
    Date.from(this.toInstant(timeZone = timezone).toJavaInstant())
