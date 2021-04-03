package com.azharkova.di.memory.reference.weak

expect class WeakReference<T : Any>(referred: T) {
    fun clear()
    fun get(): T?
}