plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
}

dependencies {
    implementation(project(":modules:application"))
    implementation(project(":modules:infrastructure"))
    implementation(project(":modules:domain"))
    implementation(project(":modules:common"))
    implementation("org.springframework.boot:spring-boot-starter-web")
}
