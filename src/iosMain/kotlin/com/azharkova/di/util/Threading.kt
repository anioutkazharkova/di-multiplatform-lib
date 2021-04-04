package com.azharkova.di.util

import platform.objc.objc_sync_enter
import platform.objc.objc_sync_exit


actual inline fun <R> trySynchronized(lock: Any?, block: () -> R): R {
    if (lock != null) {
        objc_sync_enter(lock)
        val result = block()
        objc_sync_exit(lock)
        return result
    } else {
        return block()
    }
}