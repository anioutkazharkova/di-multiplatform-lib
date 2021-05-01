package com.azharkova.di.resolver

import com.azharkova.di.scope.ScopeType


class RegistrationRef<T:Any>(val name: String, val initor: ()->T?) {
    var scope: ScopeType = ScopeType.Graph
    var resolveProperties: ((T?)->Unit)? = null

    fun resolve():T? {
        return initor.invoke()
    }

    fun resolveDependencies(resolvedInstance: T?){
       if (resolvedInstance != null) {
           resolveProperties?.invoke(resolvedInstance)
       }
    }
}

public data class Reference<out T: Any>(
    val current: T?,
    val initor: () -> T?
)