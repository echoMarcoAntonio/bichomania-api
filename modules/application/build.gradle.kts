plugins {
    java
}

dependencies {
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))

    // Spring sem JPA starter
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.data:spring-data-commons") // page, Pageable

    // logging
    implementation("org.slf4j:slf4j-api")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.6.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")

    // testes
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}