package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.listener.EventBus

/**
 * implement [dev.yidafu.loki.core.listener.EventListener] interface
 */
abstract class BaseAppender<E> : Appender<E> {
    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>
    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
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
