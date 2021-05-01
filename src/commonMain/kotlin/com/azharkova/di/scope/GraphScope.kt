package com.azharkova.di.scope

import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import com.azharkova.di.util.trySynchronized
import com.azharkova.di.util.defer
import kotlin.reflect.KClass

class GraphScope :  ScopeProtocol {
    var resolutionDepth: Int = 0
    var graph:HashMap<String,Any>  = hashMapOf()

    private  var _lock = Any()

   override fun<T:Any> resolve(regReference: RegistrationRef<T>, clazz: KClass<T>):T? {
       val key = regReference.name

     trySynchronized(_lock) {
           resolutionDepth = resolutionDepth + 1
           defer {
               resolutionDepth = resolutionDepth - 1
               if (resolutionDepth == 0) {
                   graph.clear()
               }
           }
           var service: T? = previous(key)
           if (service != null) {
               return service
           } else {
               val resolvedService = save(regReference,key)
               regReference.resolveProperties?.invoke(resolvedService)
               return resolvedService
           }
       }
   }

    override fun<T:Any> previous(key: String):T? {
        return graph[key] as? T
    }

    override fun <T : Any> save(reference: RegistrationRef<T>, key: String): T? {
        val service = reference.resolve()
        if (service != null) {
            save(service, key)
        }

        return service
    }

    override fun<T:Any> save(instance: T, key: String) {
        graph.put(key, instance)
    }
}