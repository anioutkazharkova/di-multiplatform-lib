package com.azharkova.di.util

public actual inline fun <R> trySynchronized(lock: Any?, block: () -> R): R =
    if (lock == null)
        block()
    else
        kotlin.synchronized(lock, block)