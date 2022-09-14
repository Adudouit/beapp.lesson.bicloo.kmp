import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
	kotlin("multiplatform")
	id("com.android.library")
	id("kotlinx-serialization")
}

kotlin {
	android()

	val xcf = XCFramework("BikeKit")
	listOf(
		iosX64(),
		iosArm64(),
		iosSimulatorArm64()
	).forEach {
		it.binaries.framework {
			baseName = "BikeKit"
			xcf.add(this)
		}
	}

	sourceSets {
        val ktorVersion = "2.1.1"

        val commonMain by getting {
			dependencies {
				implementation("io.ktor:ktor-client-core:$ktorVersion")
				implementation("io.ktor:ktor-client-serialization:$ktorVersion")
				implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
				implementation("io.ktor:ktor-client-logging:$ktorVersion")

			}
		}
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
			}
		}
		val androidMain by getting {
			dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
			}
		}


		val androidTest by getting
		val iosX64Main by getting
		val iosArm64Main by getting
		val iosSimulatorArm64Main by getting
		val iosMain by creating {
			dependsOn(commonMain)
			iosX64Main.dependsOn(this)
			iosArm64Main.dependsOn(this)
			iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }

        }
		val iosX64Test by getting
		val iosArm64Test by getting
		val iosSimulatorArm64Test by getting
		val iosTest by creating {
			dependsOn(commonTest)
			iosX64Test.dependsOn(this)
			iosArm64Test.dependsOn(this)
			iosSimulatorArm64Test.dependsOn(this)
		}
	}
}

android {
	compileSdk = 32
	sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
	defaultConfig {
		minSdk = 21
		targetSdk = 32
	}
}