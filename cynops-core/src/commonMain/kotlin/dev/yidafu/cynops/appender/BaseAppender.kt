package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.LoggerContext
import dev.yidafu.cynops.appender.filter.FilterReply
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.config.CynopsConfig
import dev.yidafu.cynops.helpers.runOnIo
import dev.yidafu.cynops.listener.EventBus
import kotlinx.coroutines.flow.takeWhile

/**
 * implement [dev.yidafu.cynops.listener.EventListener] interface
 */
abstract class BaseAppender() : Appender {
    abstract var config: CynopsConfig
    override lateinit var context: LoggerContext

//    lateinit var sharedFlow: MutableSharedFlow<ILogEvent>

    private var _isStarted = false

    abstract var encoder: ICodec<ILogEvent>

    /**
     *
     */
    override fun filter(event: ILogEvent): Boolean {
        return context.config.filters.all { it.decide(event) == FilterReply.ACCEPT }
    }

    override fun isStarted(): Boolean = _isStarted

    override fun onStart() {
        _isStarted = true
        runOnIo {
            /**
             * TODO: before exit program flush all buffered log events
             */
            context.sharedFlow
                .takeWhile {
                    filter(it)
                }
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
