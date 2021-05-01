package com.azharkova.di.container

import com.azharkova.di.resolver.Resolver
import com.azharkova.di.scope.ScopeType
import com.azharkova.di.util.createType
import kotlin.native.concurrent.ThreadLocal
import kotlin.reflect.KClass

public interface ContainerProtocol {
    fun <T : Any> register(type: String?, scope: ScopeType = ScopeType.Graph, fabric: () -> T?)

    fun <T : Any> resolve(type: String?): T?
}

public class DIContainer : ContainerProtocol {
    @ThreadLocal
    public companion object {
        val Factory = DIContainer()
    }

    val resolver = Resolver()

    public fun <T : Any> register(clazz: KClass<T>, scope: ScopeType = ScopeType.Graph, fabric: () -> T?) {
        resolver.register(clazz,scope,fabric)
    }

    public fun <T : Any> resolve(clazz: KClass<T>): T? {
        return resolver.resolve(clazz)
    }

   public override fun <T : Any> resolve(type: String?): T? {
       if (type != null) {
           val clazz = createType<T>(type)
           if (clazz != null) {
               return resolver.resolve(clazz)
           }
       }
        return null
    }

 public   override fun <T : Any> register(type: String?, scope: ScopeType, fabric: () -> T?) {
     if (type != null) {
         val clazz = createType<T>(type)
         if (clazz != null) {
             resolver.register(clazz, scope, fabric)
         }
     }
 }
}

public inline fun <reified T:Any> resolved(container: DIContainer = DIContainer.Factory) = container.resolve(T::class)