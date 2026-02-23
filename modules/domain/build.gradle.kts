plugins {
    java
}

dependencies {
    implementation(project(":modules:common"))

    compileOnly("jakarta.persistence:jakarta.persistence-api")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}