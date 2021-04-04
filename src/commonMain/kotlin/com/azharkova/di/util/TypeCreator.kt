package com.azharkova.di.util

import kotlin.reflect.KClass

public expect fun<T: Any> createType(type: String): KClass<T>?