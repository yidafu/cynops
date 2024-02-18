package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.helpers.runOnLog
import kotlinx.coroutines.*

/**
 * push [dev.yidafu.loki.core.ILogEvent] to [queue] first. then per 16ms will consume all queue data.
 *
 */
abstract class AsyncAppender<E> : BaseAppender() {
//    private val queue: LinkedRingList<E> = LinkedRingList(1024)

    /**
     * TODO: [event] need filter
     */
    abstract fun filterLevel(event: ILogEvent): Boolean

    override fun doAppend(event: ILogEvent) {
        if (filterLevel(event)) {
//            asyncAppend(event)
        }
    }

    override fun flush() {
        // 确保同步刷日志
//        runBlocking {
//            flushLog()
//        }
    }

    private suspend fun flushLog() {
        runOnLog {
        }
//        val iter = queue.iterator()
//        val bufferArray = ArrayList<E>(20)
//        while (queue.isEmpty()) {
//            queue.poll()?.let {
//                if (bufferArray.size == 20) {
//                    asyncAppend(bufferArray)
//                    bufferArray.clear()
//                }
//            }
//        }
//        if (bufferArray.isNotEmpty()) {
//            bufferArray.clear()
//        }
    }

    /**
     * async append eventArray
     */
    abstract suspend fun asyncAppend(eventArray: ArrayList<E>)
}
