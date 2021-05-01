package com.azharkova.di.memory.reference.cached

import com.azharkova.di.container.DIContainer
import com.azharkova.di.scope.ScopeType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

class CachedValue<T:Any>(private val clazz: KClass<T>,
                         private val container : DIContainer  = DIContainer.Factory,
                         private val fabric: () -> T?):
    ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef:Any? , property: KProperty<*>): T? {
        return container.resolve(clazz)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        container.register(clazz, ScopeType.Cached,fabric)
    }
}
inline fun <reified T:Any> cached(container: DIContainer = DIContainer.Factory, noinline fabric: () -> T?) = CachedValue(T::class,container,fabric)
