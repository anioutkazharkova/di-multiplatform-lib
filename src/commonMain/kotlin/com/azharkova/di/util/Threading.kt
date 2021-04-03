package com.azharkova.di.util

public expect inline fun <R> trySynchronized(lock: Any?, block: () -> R): R