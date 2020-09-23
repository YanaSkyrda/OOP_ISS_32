plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains:annotations:19.0.0")
    testCompile("junit", "junit", "4.12")
    implementation("org.mockito:mockito-core:3.5.11")
}
