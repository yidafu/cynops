package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.LoggerContext
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.helpers.runOnIo
import dev.yidafu.cynops.listener.EventBus

/**
 * implement [dev.yidafu.cynops.listener.EventListener] interface
 */
abstract class BaseAppender : Appender {
    override lateinit var context: LoggerContext
//    lateinit var sharedFlow: MutableSharedFlow<ILogEvent>

    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>

    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
        runOnIo {
            context.sharedFlow
//                .takeWhile { _isStarted }
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
