package com.azharkova.di_multiplatform_core

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}