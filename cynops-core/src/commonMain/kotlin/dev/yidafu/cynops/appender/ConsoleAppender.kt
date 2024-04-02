package dev.yidafu.cynops.appender

import dev.yidafu.cynops.config.CynopsConfig

fun CynopsConfig.console() {
    val appender = ConsoleAppender()
    appender.initConfig(this)
    appenderList.add(appender)
}

expect class ConsoleAppender() : SyncAppender {
    override var name: String
}
