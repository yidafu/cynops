package dev.yidafu.cynops

import dev.yidafu.cynops.appender.ConsoleAppender
import dev.yidafu.cynops.config.CynopsConfig
import kotlinx.coroutines.flow.MutableSharedFlow

class LoggerContext(
    val config: CynopsConfig = CynopsConfig(),
    val sharedFlow: MutableSharedFlow<ILogEvent> =
        MutableSharedFlow(
            config.cacheStrategy.replay,
            config.cacheStrategy.bufferCapacity,
            config.cacheStrategy.overflow,
        ),
) {
    init {
        config.appenderList.forEach {
            it.context = this
        }
    }

    fun start() {
        config.appenderList.forEach { it.onStart() }
    }

    fun stop() {
        config.appenderList.forEach { it.onStop() }
    }
}

val DefaultLoggerContext =
    LoggerContext(
        CynopsConfig(
            mutableListOf(ConsoleAppender()),
        ),
    )
