package com.azharkova.di.resolver

import com.azharkova.di.scope.MainScopeHolder
import com.azharkova.di.scope.ScopeProtocol
import com.azharkova.di.scope.ScopeType
import com.azharkova.di.util.trySynchronized
import kotlin.reflect.KClass

interface ResolverProtocol {
    fun <T : Any> register(clazz: KClass<T>, scope: ScopeType = ScopeType.Graph, fabric: () -> T?)

    fun <T : Any> resolve(clazz: KClass<T>): T?
}

public class Resolver : ResolverProtocol {
    private var defaultScope: ScopeType = ScopeType.Graph
    private var registrations: HashMap<Int, RegistrationRef<*>> = hashMapOf()
    private  val scopeHolder = MainScopeHolder()

    override fun <T : Any> register(clazz: KClass<T>, scope: ScopeType, fabric: () -> T?) {
        this.add(clazz = clazz, scope = scope, fabric = fabric)
    }

   override fun <T : Any> resolve(clazz: KClass<T>): T? {
        val reference = this.searchReference(clazz)
        if (reference != null) {
            val scopeType = reference.scope
            return selectScope(scopeType).resolve(reference,  clazz)
        }
        return null
    }

    private fun <T : Any> add(
        clazz: KClass<T>,
        scope: ScopeType = ScopeType.Graph,
        fabric: () -> T?
    ) {
        trySynchronized(registrations) {
            val reference = RegistrationRef(clazz.qualifiedName ?: "NONAME", fabric)
            reference.scope = scope
            val key = clazz.hashCode()
            if (registrations.containsKey(key)) {
                registrations[key] = reference
            } else {
                registrations.put(key, reference)
            }
        }
    }

    private fun selectScope(scope: ScopeType): ScopeProtocol {
        when (scope) {
            ScopeType.Cached -> return scopeHolder.cachedScope
            ScopeType.Container -> return scopeHolder
                .containerScope
            ScopeType.Weak -> return scopeHolder.weakScope
            ScopeType.Entity -> return scopeHolder.entityScope
            ScopeType.Graph -> return scopeHolder.graphScope
        }
    }

    private fun <T : Any> searchReference(clazz: KClass<T>): RegistrationRef<T>? {
        val name = clazz.qualifiedName
        trySynchronized(registrations) {
            return (registrations.values.filter { it.name == name }).firstOrNull() as RegistrationRef<T>?
        }
    }
}