package com.azharkova.di.init

import com.azharkova.di.container.DIContainer

class ConfiguratorApp {
    val appDIContainer: DIContainer by lazy { DIContainer() }
}