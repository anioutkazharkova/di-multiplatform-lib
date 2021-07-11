[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.anioutkazharkova/di-multiplatform-lib/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.anioutkazharkova/di-multiplatform-lib) ![supports iOS](https://img.shields.io/badge/platform-ios-brightgreen.svg?style=flat)
![supports Android](https://img.shields.io/badge/platform-android-brightgreen.svg?style=flat)
 ![GitHub tag (latest SemVer pre-release)](https://img.shields.io/github/v/tag/anioutkazharkova/di-multiplatform-lib?include_prereleases) ![GitHub last commit](https://img.shields.io/github/last-commit/anioutkazharkova/di-multiplatform-lib) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![GitHub stars](https://img.shields.io/github/stars/anioutkazharkova/di-multiplatform-lib?style=social) ![GitHub forks](https://img.shields.io/github/forks/anioutkazharkova/di-multiplatform-lib?style=social) ![GitHub watchers](https://img.shields.io/github/watchers/anioutkazharkova/di-multiplatform-lib?style=social) ![GitHub followers](https://img.shields.io/github/followers/anioutkazharkova?style=social)

![Twitter URL](https://img.shields.io/twitter/url?style=social&url=https%3A%2F%2Ftwitter.com%2Fanioutkajarkova)

<span class="badge-buymeacoffee">
<a href="https://www.buymeacoffee.com/azharkova" title="Donate to this project using Buy Me A Coffee"><img src="https://img.shields.io/badge/buy%20me%20a%20coffee-donate-yellow.svg" alt="Buy Me A Coffee donate button" /></a>
</span>

# Multiplatform-DI library for Kotlin Multiplatform

Lightweight dependency injection framework for Kotlin Multiplatform application

Dependency injection (DI) is a software design pattern that implements Inversion of Control (IoC) and Service Locator for resolving dependencies. It could be implemented many ways.
More info about DI [here](https://developer.android.com/training/dependency-injection)

Multiplatform-DI helps you to use DI in your KMM application easily, to provide shared common architecture solution that works both in iOS and Android native applications.
Yes! It works even with iOS, in all cases. 

## Supports

* iOS (Kotlin Native)
* Android (Kotlin JVM)

## Features

- [x] Thread safety
- [x] WeakReference
- [x] Singletones (no extra actions in iOS side)
- [x] Scopes 
- [x] Easy register and resolve components  

## Future
- [ ] KSP injection 
- [ ] Circullar dependencies

## Installation

Just add a dependency into your build.gradle.kts in shared module.
Available in maven central.
```
//build.gradle.kts

 repositories {
      mavenCentral()
    }
/*....*/

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

```
 dependencies {
       implementation("io.github.anioutkazharkova:di-multiplatform-lib:1.0.4.5")
  }
  ```
 ## Sample 
 
 Check the ready-mady solution [here](https://github.com/anioutkazharkova/kmm-di-sample) It is a small client for Movies DB API with common BL and architecture
  
## Basic usage (good practice)

You can also use this [KMM DI template](https://github.com/anioutkazharkova/kmm-di-template) to create your app with integrated DI. More info in [wiki](https://github.com/anioutkazharkova/kmm-di-template/wiki)

Or you can also integrate if all by yourself. Just follow next steps: 

1. First, you need to enable an access to DI library from native apps, because their is no direct access to dependencies in shared module. 
You need create a class to manage registration and resolution of objects with DIContainer. It should be placed in commonMain of your shared module:
```
class DIManager {
    val appContainer: DIContainer by lazy { DIContainer() }

    fun<T:Any> resolve(type: KClass<T>):Any? {
        return appContainer.resolve(type)
    }

    fun<T:Any> addToScope(scope: ScopeType, type: KClass<T>, fabric: ()->T?) {
        appContainer.register(type, ScopeType.Graph,fabric)
    }
}
```
2. Provide common configuration (used for both common and native apps). Also place it in commonMain:
```
class ConfigurationApp {
val appContainer: DIManager = DIManager()

   init {
       setup()
   }

    fun setup() {
       //register hear all your components from shared module
    }
}
```
3. Register all your components from shared module (also commonMain): 
```
 fun setup() {
        appContainer.addToScope(ScopeType.Container,NetworkClient::class) {
            NetworkClient()
        }
        appContainer.addToScope(ScopeType.Container,MoviesService::class) {
            val nc = appContainer.resolve<NetworkClient>(NetworkClient::class) as? NetworkClient
            MoviesService(nc)
        }
    }
```
4. Now you can resolve components from any place of your shared part:
```
fun setup(di: DIManager) {
        this.moviesService = di.resolve<MoviesService>(MoviesService::class) as? MoviesService
        print(moviesService)
    }
```
5. Next, create a property to get access container in Android native app:
```
@HiltAndroidApp
class App : Application() {
    companion object {
        val config = ConfigurationApp()
        val container = config.appContainer
        lateinit var INSTANCE:App
        var AppContext: Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        AppContext = this
    }
}
```
6. Repeat same for iOS:
```
class Util{
    static var shared = Util()
    private lazy var config: ConfigurationApp = {
       return ConfigurationApp()
    }()
    
    lazy var container: DIManager = {
        return config.appContainer
    }()
}
```

You can also use some decorations to your code (property wrappers, delegates and ets). Sample you can see [here](https://github.com/anioutkazharkova/kmm-di-template/)
