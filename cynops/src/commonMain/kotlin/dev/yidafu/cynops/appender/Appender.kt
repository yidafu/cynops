package dev.yidafu.cynops.appender

import dev.yidafu.cynops.listener.EventListener

interface Appender<E> : EventListener {
    abstract var name: String

    fun doAppend(event: E)

    fun flush()
}
