plugins {
    java
}

group = "com.bichomania.clinicavet"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

dependencies {
    implementation(project(":modules:common"))
}
