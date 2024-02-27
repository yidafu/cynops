package dev.yidafu.cynops.appender.cleaner
import dev.yidafu.cynops.listener.EventListener

/**
 * clean log file
 */
interface Cleaner : EventListener {
    abstract val logDir: String

    fun clean()
}
