import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.api.plugins.JavaPluginExtension

plugins {
    id("org.springframework.boot") version "3.4.3" apply false
    base
}

group = "com.bichomania.clinicavet"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    // importa BOM do Spring Boot para TODOS os módulos
    dependencies {
        "implementation"(platform("org.springframework.boot:spring-boot-dependencies:3.4.3"))
        "testImplementation"(platform("org.springframework.boot:spring-boot-dependencies:3.4.3"))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}