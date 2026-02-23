import org.gradle.api.tasks.testing.Test

plugins {
    // Define a versão do Spring Boot globalmente, mas não aplica na raiz
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.22" apply false // Se usar Kotlin, senão ignore
    java
}

allprojects {
    group = "com.bichomania.clinicavet"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")

    // Configura Java 21 para todos
    extensions.configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    // Gerenciamento de versões do Spring (BOM)
    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.4.3")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}