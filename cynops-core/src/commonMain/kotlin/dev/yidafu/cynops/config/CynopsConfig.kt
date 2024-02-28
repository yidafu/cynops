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
    val cacheStrategy: LogCacheStrategy = LogCacheStrategy.DropLastCacheStrategy(),
    val filters: List<Filter> = listOf(LevelFilter(Level.Debug)),
    val env: String = "dev",
    val topic: String = "default",
    val hostname: String = "localhost",
    val appenderBufferedStrategy: AppenderBufferedStrategy = AppenderBufferedStrategy.MaxBufferedSizeStrategy(20),
) {
    internal val extraConfigMap = mutableMapOf<String, Any>()

    internal fun <T : Any> getOrInit(
        key: String,
        initFn: () -> T,
    ): T {
        return extraConfigMap[key]?.let {
            it as T
        } ?: run {
            val initValue = initFn()
            extraConfigMap[key] = initValue
            initValue
        }
    }

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

    override fun hashCode(): Int {
        return 123456
    }
}

internal val DefaultCynopsConfig = CynopsConfig()
