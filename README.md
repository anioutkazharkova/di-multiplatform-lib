# Multiplatform-DI library for Kotlin Multiplatform

Lightweight dependency injection framework for Kotlin Multiplatform application

Dependency injection (DI) is a software design pattern that implements Inversion of Control (IoC) for resolving dependencies. Multiplatform-DI helps you to use DI in your KMM application easily, to provide shared common architecture solution that works both in iOS and Android native applications.
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

## Installation

Just add a dependency into your build.gradle.kts in shared module 
```
 dependencies {
       implementation("io.github.anioutkazharkova:di-multiplatform-lib:1.0.4.5")
  }
  ```
  
## Basic usage (good practice)

1. In case, you need to provide an access to DI library from native apps, create a class to manage registration and resolution of objects with DIContainer:
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
2. Provide common configuration (used for both common and native apps):
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
3. Register all your components from shared module: 
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
4. Resolve components from any place:
```
fun setup(di: DIManager) {
        this.moviesService = di.resolve<MoviesService>(MoviesService::class) as? MoviesService
        print(moviesService)
    }
```
5. Call container from Android:
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
6. And from iOS:
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
