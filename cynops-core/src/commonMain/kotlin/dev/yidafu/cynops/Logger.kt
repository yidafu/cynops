package dev.yidafu.cynops

import dev.yidafu.cynops.LogEvent.Companion.TAG_ENV
import dev.yidafu.cynops.LogEvent.Companion.TAG_HOSTNAME
import dev.yidafu.cynops.LogEvent.Companion.TAG_TOPIC
import dev.yidafu.cynops.config.CynopsConfig
import dev.yidafu.cynops.mdc.MDC

class Logger(val context: LoggerContext = LoggerContext.Default) {
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
        return logMap[tag] ?: run {
            val log = Log(tag, context)
            logMap[tag] = log
            log
        }
    }

    companion object {
        val logMap = mutableMapOf<String, Log>()
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
    MDC.put(TAG_ENV, config.env)
    MDC.put(TAG_TOPIC, config.topic)
    MDC.put(TAG_HOSTNAME, config.hostname)
    return Logger(context)
}
