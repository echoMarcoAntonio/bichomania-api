plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.bichomania.clinicavet"

dependencies {
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))
    implementation(project(":modules:application"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    runtimeOnly("org.postgresql:postgresql:42.7.3")
}
