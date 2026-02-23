plugins {
    java
}

dependencies {
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))

    // spring core
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.slf4j:slf4j-api")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.6.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")

    // TESTES (spring boot já traz junit completo)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}