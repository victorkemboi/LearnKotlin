plugins {
    kotlin("jvm") version Versions.kotlin
    id(BuildPlugins.ktlint) version Versions.ktlint
    id(BuildPlugins.detekt) version Versions.detekt
    id(BuildPlugins.spotless) version Versions.spotless
}

group = Project.group
version = Project.version

repositories {
    mavenCentral()
}

allprojects {

    repositories {
        mavenCentral()
    }

    apply(plugin = BuildPlugins.spotless)

    apply(plugin = BuildPlugins.ktlint)
    ktlint {
        android.set(true)
        verbose.set(true)
        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }
}

subprojects {

    apply(plugin = BuildPlugins.detekt)
    detekt {
        config = files("${project.rootDir}/detekt.yml")
        parallel = true
    }

    apply(plugin = BuildPlugins.spotless)
    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                rootProject.file("${project.rootDir}/spotless/copyright.kt"),
                "^(package|object|import|interface)"
            )
        }
    }
}