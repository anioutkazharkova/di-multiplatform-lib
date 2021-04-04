package com.azharkova.di.util

import kotlin.reflect.KClass

public actual fun<T: Any> createType(type: String): KClass<T>? {
    return Class.forName(type)?.kotlin as? KClass<T>
}