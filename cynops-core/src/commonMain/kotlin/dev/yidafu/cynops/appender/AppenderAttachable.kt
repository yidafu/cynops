package dev.yidafu.cynops.appender

internal interface AppenderAttachable {
    fun addAppender(appender: Appender)

    fun getAppender(name: String): Appender

    fun isAttached(appender: Appender): Boolean

    fun detachAndStopAllAppenders()

    fun detachAppender(appender: Appender): Boolean

    fun detachAppender(name: String): Boolean

    fun iterator(): Iterator<Appender>
}
