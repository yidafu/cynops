package dev.yidafu.cynops.di

import dev.yidafu.cynops.config.configModule
import org.koin.core.context.startKoin

fun setup() {
    startKoin {
        modules(configModule)
    }
}
