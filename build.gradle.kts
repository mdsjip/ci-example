import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        google()
        maven("https://repo.spring.io/milestone")
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.1.RELEASE")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.2.0-RC1")
    }
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
    plugin("checkstyle")
}

plugins {
    val kotlinVersion = "1.2.41"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.5.RELEASE"
    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC6-4"
    id("org.jmailen.kotlinter") version "1.11.2"
    id("com.github.ben-manes.versions") version "0.17.0"
}

detekt {
    version = "1.0.0.RC6-4"
    profile("main", Action {
        input = "$projectDir/src/main/kotlin"
        config = "$projectDir/detekt.yml"
        filters = ".*test.*,.*/resources/.*,.*/tmp/.*"
    })
}

kotlinter {
    ignoreFailures = false
    indentSize = 4
    continuationIndentSize = 8
    reporters = arrayOf("checkstyle", "plain")
}

version = "1.0.0-SNAPSHOT"


tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

repositories {
    mavenCentral()
    maven("http://repo.spring.io/milestone")
}

dependencies {
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.h2database:h2")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile("io.springfox:springfox-swagger2:2.8.0")
    compile("io.springfox:springfox-swagger-ui:2.8.0")
    testCompile("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0-RC1")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0-RC1")
}

