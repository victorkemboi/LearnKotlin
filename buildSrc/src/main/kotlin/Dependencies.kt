object Versions {
    const val kotlin = "1.5.30"

    // Gradle Plugins
    const val ktlint = "10.2.0"
    const val detekt = "1.18.1"
    const val spotless = "5.15.1"
    const val dokka = ""
}

object BuildPlugins {
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val detekt = "io.gitlab.arturbosch.detekt"
    const val dokka = "org.jetbrains.dokka"
    const val spotless = "com.diffplug.spotless"
}

object Libraries {
}

object Project {
    const val group = "inc.mes"
    const val version = "1.0.0"
    const val projectName = "LearnKotlin"
}

object BuildModules {
    const val appModule = ":agent-app"
    const val coreModule = ":internal:core"
    const val dataModule = ":internal:data"
    const val domainModule = ":internal:domain"
}