plugins {
    java
}

dependencies {
    implementation(project(":modules:application"))
    implementation(project(":modules:common"))

    implementation("org.mapstruct:mapstruct:1.6.0")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0")
}