package dev.yidafu.cynops.appender

expect class ConsoleAppender() : SyncAppender {
    override var name: String
}
