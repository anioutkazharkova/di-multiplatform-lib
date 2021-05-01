package com.azharkova.di.scope

import com.azharkova.di.memory.reference.weak.BoxWeak
import com.azharkova.di.memory.reference.weak.WeakReference
import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import com.azharkova.di.util.trySynchronized
import kotlin.reflect.KClass

class ShareScope :ScopeProtocol {
    var cachedServices:HashMap<String, BoxWeak>  = hashMapOf()
    private val _lock = Any()

    override fun <T : Any> resolve(
        regReference: RegistrationRef<T>,
        clazz: KClass<T>
    ): T? {
        val key = regReference.name
         trySynchronized(_lock) {
        var service: T? = previous(key)
        if (service != null) {
            return service
        }
        return save(regReference, key)
        }
    }

    override fun <T : Any> save(reference: RegistrationRef<T>, key: String): T? {
        val service = reference.resolve()
        if (service != null) {
            save(service, key)
        }

        return service
    }

    override fun <T : Any> save(instance: T, key: String) {
        cachedServices.put(key,BoxWeak(WeakReference(instance)))
    }

    override fun <T : Any> previous(key: String): T? {
       return cachedServices[key]?.value?.get() as? T
    }

    fun reset() {
        cachedServices.clear()
    }
}