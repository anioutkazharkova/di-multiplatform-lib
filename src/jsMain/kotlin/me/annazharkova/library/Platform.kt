package me.annazharkova.library

actual class Platform actual constructor() {
    actual val platform: String
        get() = "JS"
}