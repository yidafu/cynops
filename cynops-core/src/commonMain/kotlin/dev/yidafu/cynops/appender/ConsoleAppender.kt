package dev.yidafu.cynops.appender

import dev.yidafu.cynops.config.CynopsConfig

fun CynopsConfig.console() {
    val appender = ConsoleAppender()
    appender.config = this
    appenderList.add(ConsoleAppender())
}

expect class ConsoleAppender() : SyncAppender {
    override var name: String
}
