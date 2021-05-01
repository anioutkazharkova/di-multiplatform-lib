package com.azharkova.di.memory.reference.weak

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class WeakValue<T:Any>(obj: T? = null): ReadWriteProperty<Any?, T?> {
    private var wref : WeakReference<T>?

    init {
        this.wref = obj?.let { WeakReference(it) }
    }

    override fun getValue(thisRef:Any? , property: KProperty<*>): T? {
        return wref?.get()
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        wref = value?.let { WeakReference(it) }
    }
}

//simply weakify
public fun <T:Any> weakify(obj: T? = null) = WeakValue(obj)