object Versions {
    const val kotlin = "1.5.30"

    // Gradle Plugins
    const val ktlint = "10.2.0"
    const val detekt = "1.18.1"
    const val spotless = "5.15.1"
    const val dokka = ""

    const val ktor = "1.6.3"
    const val logBackClassic = "1.2.5"
    const val joda = "2.10.10"
    const val kotlinxDateTime = "0.2.1"
}

object BuildPlugins {
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val dokka = "org.jetbrains.dokka"
    const val spotless = "com.diffplug.spotless"
    const val kotlinxSerialization = "plugin.serialization"
}

object Libraries {
    // Netty Engine
    const val netty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    // Ktor's core components
    const val ktorCore = "io.ktor:ktor-server-core:${Versions.ktor}"
    // Ktor Auth
    const val ktorAuth = "io.ktor:ktor-auth:${Versions.ktor}"
    // Ktor JWT Auth
    const val ktorJwtAuth = "io.ktor:ktor-auth-jwt:${Versions.ktor}"
    // log formatter
    const val logBackClassic = "ch.qos.logback:logback-classic:${Versions.logBackClassic}"
    // ktor serialization using kotlinx.serialization
    const val ktorSerialization = "io.ktor:ktor-serialization:${Versions.ktor}"
    // Gson converter
    const val ktorGson = "io.ktor:ktor-gson:${Versions.ktor}"
    // Joda time library
    const val joda = "joda-time:joda-time:${Versions.joda}"
    // kotlinx date time library
    const val kotlinxDateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
}

object TestLibraries {
    const val kotlinTest = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
}

object Project {
    const val group = "inc.mes"
    const val version = "1.0.0"
}

object BuildModules {
    const val coreModule = ":internal:core"
    const val dataModule = ":internal:data"
    const val domainModule = ":internal:domain"
}