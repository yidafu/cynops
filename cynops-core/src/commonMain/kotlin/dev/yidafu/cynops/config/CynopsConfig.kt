package dev.yidafu.cynops.config

import dev.yidafu.cynops.Level
import dev.yidafu.cynops.appender.Appender

val DEFAULT_LOG_LEVEL = Level.Info

data class CynopsConfig(
    val appenderList: MutableList<Appender> = mutableListOf(),
    val minLevel: Level = DEFAULT_LOG_LEVEL,
    val cacheStrategy: LogCacheStrategy = LogCacheStrategy.SuspendCacheStrategy,
) {
    fun merge(config: CynopsConfig): CynopsConfig {
        return CynopsConfig(
            appenderList,
            config.minLevel,
            config.cacheStrategy,
        )
    }
}

internal val DefaultCynopsConfig = CynopsConfig()
