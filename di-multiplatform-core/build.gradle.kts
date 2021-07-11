
plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    google()
}
buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")
    }
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    val hostOs = System.getProperty("os.name")
    /*val iosTarget = when {
        hostOs == "X64"-> iosX64("ios")
        hostOs == "arm64" -> iosArm64("ios")
        else -> iosX64("ios")
    }*/
    ios {
        binaries {
            framework {
                baseName = "library"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // implementation("com.google.devtools.ksp:symbol-processing-api:1.5.10-1.0.0-beta01")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {

                implementation("com.google.devtools.ksp:symbol-processing-api:1.5.10-1.0.0-beta01")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting
        val iosTest by getting
    }
}
