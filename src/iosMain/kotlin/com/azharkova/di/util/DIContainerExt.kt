package com.azharkova.di.util

import com.azharkova.di.container.DIContainer
import com.azharkova.di.scope.ScopeType
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import kotlin.reflect.KClass

fun<T: Any> DIContainer.createType(type: ObjCClass): KClass<T>? {
    return getOriginalKotlinClass(type) as? KClass<T>
}

fun<T: Any> DIContainer.register(type: ObjCClass, scope: ScopeType, fabric: () -> T?) {
    val clazz = createType<T>(type)
    if (clazz != null) {
        resolver.register(clazz,scope,fabric)
    }
}

fun <T : Any> DIContainer.resolve(type: ObjCClass): T? {
    val clazz = createType<T>(type)
    if (clazz != null) {
        return resolver.resolve(clazz)
    }
    return null
}