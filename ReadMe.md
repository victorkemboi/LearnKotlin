## KotlinLearn
This is a gradle project for the sole basis of exploring and learning Kotlin language, tools and frameworks.
The root project wil encapsulate various modules which will be covering different topics.

### Prerequisites
#### Code Analysis
The root project contains the codeAnalysis script which when ran, will run linting and formatting tasks using KtLint,
Detekt and Spotless.

For the first time, you must guarantee some running permissions:
```shell script
chmod +x codeAnalysis.sh
```
After that, you just need to run:
```shell script
./codeAnalysis
```
#### Dependency Management
All dependencies need to be defined in the `buildSrc` module inside the `Dependencies.kt` file.
From there, each project can implement their required dependencies that have been defined in the `buildSrc`.

#### Adding modules
To add a module to the project do the following:
- Add the module folder in the root of the project.
- Add the module gradle file inside module folder.
- Open the `settings.gradle.kts` file in the project root.
- Add an include function call with the module folder name as its parameter at EOF i.e
  - ``` include("module-folder")```
- Sync the project, and you are good to go :rocket:

#### Gradle
Gradle is used for build automation and dependency management in this multi-modular project.
* [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Kotlin DSl is used for Kotlin syntax
and better IDE support. Trying to expand kotlin knowledge here:smiley:
* Plugins
    * [Ktlint](https://github.com/JLLeitschuh/ktlint-gradle) - Creates convenient tasks in a Gradle project that
  run linting and auto-formatting.
    * [Detekt](https://github.com/detekt/detekt) - A static code analysis tool for the Kotlin programming language.
    * [Spotless](https://github.com/diffplug/spotless) - Versatile code formatter and adds license headers.
    * [Dokka](https://github.com/Kotlin/dokka) - A documentation engine for Kotlin, performing the same function as
  javadoc for Java.
    * [jacoco](https://github.com/jacoco/jacoco) - A Code Coverage Library
    * [Kotlin](https://kotlinlang.org/) - A modern programming language that makes developers happier.

#### Modules
* ##### Ktor App
  This module explores [Ktor](https://ktor.io/) which is a framework for building asynchronous servers and clients in
connected systems using the powerful Kotlin programming language.

  **Tech stack**
  * [Ktor](https://ktor.io/) - Asynchronous server framework.
  * [Netty](https://netty.io/) - An asynchronous event-driven network application framework used as the engine for ktor.
  * [Logback Classic](http://logback.qos.ch/) - A reliable, generic, fast and flexible logging framework.
  * [Ktor Serialization](https://ktor.io/docs/kotlin-serialization.html) - Allows you to use content converters
  provided by the [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
  * [Ktor Auth](https://ktor.io/docs/authentication.html) - Used to provide authentication for the server.
  * [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime) - A multiplatform Kotlin library for working with
  date and time.