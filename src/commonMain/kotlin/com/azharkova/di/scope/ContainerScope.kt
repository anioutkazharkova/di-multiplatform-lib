package com.azharkova.di.scope

import com.azharkova.di.util.trySynchronized
import com.azharkova.di.memory.reference.singleton.Singleton
import com.azharkova.di.resolver.Reference
import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import kotlin.reflect.KClass

class ContainerScope : ScopeProtocol{
    private var cachedServices: HashMap<String, Any?> = hashMapOf()
    private val _lock = Any()

    override fun <T : Any> resolve(
        regReference: RegistrationRef<T>,
        clazz: KClass<T>
    ): T? {
        val key = regReference.name

        trySynchronized(_lock) {
        var serviceReference = cachedServices[key] as? Reference<T>
        if (serviceReference != null) {
            return serviceReference.current
        } else {
            return save(regReference, key)
        }
       }
    }

    fun <T: Any> singleton(reference: RegistrationRef<T>):Reference<T>? {
        return Singleton.make(reference)
    }

    override fun <T : Any> previous(key: String): T? {
        return (cachedServices[key] as? Reference<T>)?.current
    }

    override fun<T:Any> save(reference: RegistrationRef<T>, key: String):T? {
        val singleton = singleton(reference)
        cachedServices.put(key, singleton)
        return singleton?.current
    }

    override fun <T : Any> save(instance: T, key: String) {

    }
}