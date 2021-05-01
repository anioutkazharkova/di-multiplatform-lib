package com.azharkova.di.scope

public enum class ScopeType {
    Container, Cached, Graph, Weak, Entity;
}

class MainScopeHolder {
        val cachedScope = CachedScope()
        val graphScope = GraphScope()
        val entityScope = EntityScope()
        val weakScope = ShareScope()
        val containerScope = ContainerScope()
}