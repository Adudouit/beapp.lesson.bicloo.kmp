# NETWORK
## Add Ktor dependencies
To use the Ktor HTTP client in your project, you need to add at least two dependencies: a client dependency and an engine dependency. Open the `shared/build.gradle.kts` file and follow the steps below:

##### 1. Specify Ktor version inside sourceSets:

```
sourceSets {
    val ktorVersion = "2.1.1"
}
```

##### 2. To use the Ktor client in common code, add the dependency to ktor-client-core to the commonMain source set:

```
val commonMain by getting {
    dependencies {
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
		 implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

    }
}
```

##### 3. Add an engine dependency for the required platform to the corresponding source set:
For Android, add the ktor-client-okhttp dependency to the androidMain source set:


```
val androidMain by getting {
    dependencies {
        implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    }
}

```

For iOS, add the ktor-client-darwin dependency to iosMain:

```
val iosMain by creating {
    dependsOn(commonMain)
    iosX64Main.dependsOn(this)
    iosArm64Main.dependsOn(this)
    iosSimulatorArm64Main.dependsOn(this)
    dependencies {
        implementation("io.ktor:ktor-client-darwin:$ktorVersion")
    }
}

```
##### 4. Add the serialization plugins

```
plugins {
	id("kotlinx-serialization")
}
```

## Make the first call
##### 1. Copy the `Contract` Dto & Entities in the shared module

##### 2. Create a RestDatsource object and add the `contracts` call

```
object RestDataSource {

	private val client = HttpClient()

	suspend fun getContracts(): List<ContractEntity> {
		client.get(....)
		// 
	}
}
```

#### 3. Replace the `RestManager` with the shared `RestDataSource` in the `CitySelectionViewModel`