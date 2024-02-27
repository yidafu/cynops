package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.config.CynopsConfig
import dev.yidafu.cynops.helpers.runOnLog
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.datetime.Clock

/**
 * push [dev.yidafu.cynops.ILogEvent] to [queue] first. then per 16ms will consume all queue data.
 */
abstract class BufferedAppender(override var config: CynopsConfig) : BaseAppender() {
    private val strategy: AppenderBufferedStrategy = config.appenderBufferedStrategy
    private val bufferedList = mutableListOf<ILogEvent>()
    private var lastFlushTime: Long = currentMillisecond
    private val bufferedMutex = Mutex()

    private val currentMillisecond: Long
        get() = Clock.System.now().toEpochMilliseconds()

    private inline fun withLock(block: () -> Unit) {
        bufferedMutex.tryLock(bufferedList)
        try {
            block()
        } finally {
            bufferedMutex.unlock(bufferedList)
        }
    }

    override fun doAppend(event: ILogEvent) =
        withLock {
            bufferedList.add(event)
            if (strategy.maxSize > bufferedList.size) {
                flush()
            }
            if (strategy.interval > (currentMillisecond - lastFlushTime)) {
                flush()
            }
        }

    override fun flush() {
        lastFlushTime = currentMillisecond
        runOnLog {
            flushLog()
        }
    }

    private suspend fun flushLog() =
        withLock {
            bufferedList.chunked(20).forEach { list ->
                asyncAppend(list)
            }
            bufferedList.clear()
        }

    /**
     * async append eventArray
     */
    abstract suspend fun asyncAppend(eventArray: List<ILogEvent>)
}
