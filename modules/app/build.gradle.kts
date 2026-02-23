plugins {
    id("org.springframework.boot")
    java
}

dependencies {
    implementation(project(":modules:application"))
    implementation(project(":modules:infrastructure"))
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}