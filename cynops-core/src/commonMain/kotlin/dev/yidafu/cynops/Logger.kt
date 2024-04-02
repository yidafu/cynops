package dev.yidafu.cynops

import dev.yidafu.cynops.config.CynopsConfig

class Logger(val context: LoggerContext = LoggerContext.Default) {
    val rootLogMap = mutableMapOf<String, Log>()

    inline fun v(
        tag: String,
        message: () -> String,
    ) {
        tag(tag).v(message)
    }

    inline fun v(
        tag: String,
        message: String,
    ) {
        tag(tag).v(message)
    }

    inline fun d(
        tag: String,
        message: () -> String,
    ) {
        tag(tag).d(message)
    }

    inline fun d(
        tag: String,
        message: String,
    ) {
        tag(tag).d(message)
    }

    inline fun i(
        tag: String,
        message: () -> String,
    ) {
        tag(tag).i(message)
    }

    inline fun i(
        tag: String,
        message: String,
    ) {
        tag(tag).i(message)
    }

    inline fun w(
        tag: String,
        message: () -> String,
    ) {
        tag(tag).w(message)
    }

    inline fun w(
        tag: String,
        message: String,
    ) {
        tag(tag).w(message)
    }

    inline fun w(
        tag: String,
        throwable: Throwable,
        message: () -> String,
    ) {
        tag(tag).w(message(), throwable)
    }

    inline fun w(
        tag: String,
        throwable: Throwable,
        message: String,
    ) {
        tag(tag).w(message, throwable)
    }

    inline fun e(
        tag: String,
        message: () -> String,
    ) {
        tag(tag).e(message)
    }

    inline fun e(
        tag: String,
        message: String,
    ) {
        tag(tag).e(message)
    }

    inline fun e(
        tag: String,
        throwable: Throwable,
        message: () -> String,
    ) {
        tag(tag).e(message(), throwable)
    }

    inline fun e(
        tag: String,
        throwable: Throwable,
        message: String,
    ) {
        tag(tag).e(message, throwable)
    }

    inline fun tag(tag: String): Log {
        return rootLogMap[tag] ?: run {
            val log = RootLog(tag, context)
            rootLogMap[tag] = log
            log
        }
    }

    companion object {
//        private val defaultLogger = Logger(LoggerContext.Default)

//        fun tag(tag: String): Log {
//            return defaultLogger.tag(tag)
//        }
    }
}

fun Logger(block: CynopsConfig.() -> Unit): Logger {
    val config = CynopsConfig().apply(block)
    val context = LoggerContext(config)
    context.start()
    return Logger(context)
}
