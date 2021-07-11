pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}
includeBuild("convention-plugins")
rootProject.name = "di-multiplatform-lib"

include(":ksp-multiplatform")
include(":ksp-multiplatform-apply")
include(":di-multiplatform-core")
