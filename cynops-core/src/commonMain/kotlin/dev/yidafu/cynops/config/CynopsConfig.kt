package dev.yidafu.cynops.config

import dev.yidafu.cynops.Level
import dev.yidafu.cynops.appender.Appender
import dev.yidafu.cynops.appender.AppenderBufferedStrategy
import dev.yidafu.cynops.appender.filter.Filter
import dev.yidafu.cynops.appender.filter.LevelFilter

val DEFAULT_LOG_LEVEL = Level.Info

typealias AppenderList = MutableList<Appender>

data class CynopsConfig(
    val appenderList: AppenderList = mutableListOf(),
    val minLevel: Level = DEFAULT_LOG_LEVEL,
    val cacheStrategy: LogCacheStrategy = LogCacheStrategy.SuspendCacheStrategy,
    val filters: List<Filter> = listOf(LevelFilter(Level.Debug)),
    val env: String = "dev",
    val topic: String = "default",
    val hostname: String = "localhost",
    val appenderBufferedStrategy: AppenderBufferedStrategy = AppenderBufferedStrategy.MaxBufferedSizeStrategy(20),
) {
    fun appender(block: CynopsConfig.() -> Unit) {
        block()
    }

    fun merge(config: CynopsConfig): CynopsConfig {
        return CynopsConfig(
            appenderList,
            config.minLevel,
            config.cacheStrategy,
        )
    }
}

internal val DefaultCynopsConfig = CynopsConfig()
