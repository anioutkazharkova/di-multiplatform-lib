package com.azharkova.di.scope

import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import com.azharkova.di.util.trySynchronized
import kotlin.reflect.KClass

class CachedScope : ScopeProtocol {
    private var cachedServices: HashMap<String, Any?> = hashMapOf()
    private  var _lock = Any()
    override fun <T : Any> resolve(
        regReference: RegistrationRef<T>,
        clazz: KClass<T>
    ): T? {
        val key = regReference.name
        // trySynchronized(_lock) {
        var service: T? = previous(key)
        if (service != null) {
            return service
        } else {
            return save(regReference, key)
        }
        // }
    }

    override fun <T : Any> save(reference: RegistrationRef<T>, key: String):T? {
       val service = reference.resolve()
        if (service != null) {
            save(service, key)
        }
        return service
    }

    override fun <T : Any> save(instance: T, key: String) {
        cachedServices.put(key, instance)
    }

    override fun <T : Any> previous(key: String): T? {
       return cachedServices[key] as? T
    }
}