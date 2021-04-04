package com.azharkova.di.memory.reference.singleton

import com.azharkova.di.container.DIContainer
import com.azharkova.di.memory.reference.cached.CachedValue
import com.azharkova.di.scope.ScopeType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class SingletonValue<T:Any>(private val clazz: KClass<T>,
                            private val container : DIContainer = DIContainer.Factory,
                            private val fabric: () -> T?):
    ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef:Any? , property: KProperty<*>): T? {
        return container.resolve(clazz)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        container.register(clazz, ScopeType.Container,fabric)
    }
}

public inline fun <reified T:Any> single(container: DIContainer = DIContainer.Factory, noinline fabric: () -> T?) = CachedValue(T::class,container,fabric)