import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    kotlin(BuildPlugins.kotlinxSerialization) version Versions.kotlin
}

group = "inc.mes.ktor-app"

dependencies {
    // Netty engine
    implementation(Libraries.netty)
    // Ktor's core components
    implementation(Libraries.ktorCore)
    // log formatter
    implementation(Libraries.logBackClassic)

    // data serialization for ktor
    implementation(Libraries.ktorSerialization)

    // gson converter
    implementation(Libraries.ktorGson)

    // JWT auth libs
    implementation(Libraries.ktorAuth)
    implementation(Libraries.ktorJwtAuth)

    // kotlinx datetime
    implementation(Libraries.kotlinxDateTime)

    // kotlin test libraries
    testImplementation(TestLibraries.kotlinTest)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("inc.mes.ktor.ServerKt")
}
