package com.azharkova.di.util


actual inline fun <R> trySynchronized(lock: Any?, block: () -> R): R = block()