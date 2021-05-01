package com.azharkova.di.scope

import com.azharkova.di.memory.reference.weak.weakify
import com.azharkova.di.resolver.RegistrationRef
import com.azharkova.di.resolver.Resolver
import kotlin.reflect.KClass

interface ScopeProtocol {
    fun<T:Any> resolve(regReference: RegistrationRef<T>, clazz: KClass<T>):T?

    fun<T:Any> save(instance: T, key: String)

    fun<T:Any> save(reference: RegistrationRef<T>, key: String):T?

    fun<T:Any> previous(key: String):T?
}

fun<T:Any> ScopeProtocol.resolveLazy(regReference: RegistrationRef<T>, resolver: Resolver, clazz: KClass<T>) = lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    resolve(regReference,clazz)
}

fun<T:Any> ScopeProtocol.resolveWeak(regReference: RegistrationRef<T>,  clazz: KClass<T>) = weakify(resolve(regReference, clazz))