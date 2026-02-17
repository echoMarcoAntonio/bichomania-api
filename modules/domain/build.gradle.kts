plugins {
    java
}

group = "com.bichomania.clinicavet"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

dependencies {
    implementation(project(":modules:common"))
    compileOnly("jakarta.persistence:jakarta.persistence-api:3.1.0")
}
