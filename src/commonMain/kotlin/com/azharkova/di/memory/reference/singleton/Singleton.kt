package com.azharkova.di.memory.reference.singleton


import com.azharkova.di.resolver.Reference
import com.azharkova.di.resolver.RegistrationRef
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object Singleton {
    fun<T: Any> make(ref: RegistrationRef<T>): Reference<T> {
        val value = ref.initor.invoke()
        return  Reference(value){value}
    }
}

fun <T:Any> singleRef(ref: RegistrationRef<T>) = Singleton.make(ref)

fun <T:Any> single(ref: RegistrationRef<T>) = Singleton.make(ref).current
