package com.azharkova.di.util

import com.azharkova.di.container.ContainerProtocol
import com.azharkova.di.container.DIContainer
import com.azharkova.di.scope.ScopeType
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import platform.Foundation.NSClassFromString
import kotlin.reflect.KClass

public actual fun<T: Any> createType(type: String): KClass<T>? {
    val objcClass = NSClassFromString(type)
    if (objcClass != null) {
        return getOriginalKotlinClass(objcClass) as? KClass<T>
    }
    return null
}

