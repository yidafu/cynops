package dev.yidafu.cynops

import dev.yidafu.cynops.helpers.DefaultMessageFormatter

@Suppress("OVERRIDE_BY_INLINE")
class Log(val tag: String, private val context: LoggerContext) : ILog {
    fun log(event: ILogEvent) {
        context.sharedFlow.tryEmit(event)
    }

    inline fun log(
        level: Level,
        tag: String,
        message: String,
    ) {
        val event = LogEvent.create(level, tag, message)
        log(event)
    }

    inline fun v(message: String) {
        v(tag, message)
    }

    inline fun v(message: () -> String) {
        v(tag, message())
    }

    override inline fun v(
        tag: String,
        message: String,
    ) {
        log(Level.Info, tag, message)
    }

    override fun v(
        tag: String,
        message: () -> String,
    ) {
        v(tag, message())
    }

    inline fun d(message: String) {
        d(tag, message)
    }

    inline fun d(message: () -> String) {
        d(tag, message())
    }

    override fun d(
        tag: String,
        message: String,
    ) {
        log(Level.Debug, tag, message)
    }

    override fun d(
        tag: String,
        message: () -> String,
    ) {
        d(tag, message())
    }

    inline fun i(message: String) {
        i(tag, message)
    }

    inline fun i(message: () -> String) {
        i(tag, message())
    }

    override fun i(
        tag: String,
        message: String,
    ) {
        log(Level.Info, tag, message)
    }

    override fun i(
        tag: String,
        message: () -> String,
    ) {
        i(tag, message())
    }

    inline fun w(message: String) {
        w(tag, message)
    }

    inline fun w(message: () -> String) {
        w(tag, message())
    }

    inline fun w(
        message: String,
        throwable: Throwable,
    ) {
        w(tag, message, throwable)
    }

    override fun w(
        tag: String,
        message: String,
    ) {
        w(tag, message)
    }

    override fun w(
        tag: String,
        message: () -> String,
    ) {
        w(tag, message())
    }

    override fun w(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        w(tag, DefaultMessageFormatter.format(message, throwable))
    }

    inline fun e(message: String) {
        e(tag, message)
    }

    inline fun e(message: () -> String) {
        e(tag, message())
    }

    inline fun e(
        message: String,
        throwable: Throwable,
    ) {
        e(tag, message, throwable)
    }

    override inline fun e(
        tag: String,
        message: String,
    ) {
        log(Level.Error, tag, message)
    }

    override inline fun e(
        tag: String,
        message: () -> String,
    ) {
        e(tag, message())
    }

    override inline fun e(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        e(tag, DefaultMessageFormatter.format(message, throwable))
    }
}
