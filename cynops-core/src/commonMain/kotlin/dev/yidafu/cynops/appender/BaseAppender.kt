package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.LoggerContext
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.helpers.runOnLog
import dev.yidafu.cynops.listener.EventBus
import kotlinx.coroutines.flow.takeWhile

/**
 * implement [dev.yidafu.loki.core.listener.EventListener] interface
 */
abstract class BaseAppender : Appender {
    override lateinit var context: LoggerContext
//    lateinit var sharedFlow: MutableSharedFlow<ILogEvent>

    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>

    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
        runOnLog {
            context.sharedFlow
                .takeWhile { _isStarted }
                .collect {
                    doAppend(it)
                }
        }
    }

    override fun onStop() {
        _isStarted = false
    }

    /**
     * nop flush
     */
    override fun flush() {}

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }
}
