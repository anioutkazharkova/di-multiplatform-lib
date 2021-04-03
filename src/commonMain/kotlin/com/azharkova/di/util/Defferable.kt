package com.azharkova.di.util

class Deferrable {
    private val actions: MutableList<() -> Unit> = mutableListOf()

    fun defer(f: () -> Unit) {
        actions.add(f)
    }

    fun execute() {
        actions.forEach { it() }
    }
}

fun <T> defer(f: (Deferrable) -> T): T {
    val deferrable = Deferrable()
    try {
        return f(deferrable)
    } finally {
        deferrable.execute()
    }
}