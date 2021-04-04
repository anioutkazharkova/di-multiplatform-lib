package com.azharkova.di.init

import com.azharkova.di.container.ContainerProtocol
import com.azharkova.di.container.DIContainer

public class ConfiguratorApp {
    public val appDIContainer: DIContainer by lazy { DIContainer() }
}