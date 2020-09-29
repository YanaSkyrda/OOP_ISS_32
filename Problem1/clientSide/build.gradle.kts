plugins {
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven {
        url = uri("https://dl.bintray.com/mockito/maven")
    }

}

dependencies {
    testCompile("junit", "junit", "4.12")
    "implementation"(project(":model"))
    "testCompile"("org.mockito:mockito-core:3.+")
}
