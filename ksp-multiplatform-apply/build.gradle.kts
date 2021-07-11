plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version  "1.5.10-1.0.0-beta01"
}

repositories {
    mavenCentral()
    google()
}
dependencies{
    implementation(kotlin("stdlib-jdk8"))
  implementation(project(":ksp-multiplatform"))
    ksp(project(":ksp-multiplatform"))
}