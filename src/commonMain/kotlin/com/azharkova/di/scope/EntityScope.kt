package com.azharkova.di.scope


import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import kotlin.reflect.KClass

class EntityScope : ScopeProtocol {
    override fun <T : Any> resolve(
        regReference: RegistrationRef<T>,
        clazz: KClass<T>
    ): T? {
        return regReference.resolve()
    }

    override fun <T : Any> save(instance: T, key: String) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> save(reference: RegistrationRef<T>, key: String): T? {
        TODO("Not yet implemented")
    }

    override fun <T : Any> previous(key: String): T? {
        TODO("Not yet implemented")
    }
}