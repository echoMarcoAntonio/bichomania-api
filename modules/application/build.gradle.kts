plugins {
    java
}

group = "com.bichomania.clinicavet"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

dependencies {
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))

    // Spring Framework (necessário para @Service, @Transactional, etc.)
    implementation("org.springframework:spring-context:6.1.4")
    implementation("org.springframework:spring-tx:6.1.4")

    // Spring Data (para Page, Pageable)
    implementation("org.springframework.data:spring-data-commons:3.2.3")

    // Logging (SLF4J)
    implementation("org.slf4j:slf4j-api:2.0.12")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
}