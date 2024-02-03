package dev.yidafu.cynops

class Logger {
    val logMap = mutableMapOf<String, Log>()
    inline fun withTag(tag: String): Log {
        return logMap[tag] ?: run {
            val log = Log(tag)
            logMap[tag] = log
            log
        }
    }

    inline fun v(tag: String, message: () -> String) {
        withTag(tag).v(message)
    }

    inline fun v(tag: String, message: String) {
        withTag(tag).v(message)
    }

    inline fun d(tag: String, message: () -> String) {
        withTag(tag).d(message)
    }

    inline fun d(tag: String, message: String) {
        withTag(tag).d(message)
    }

    inline fun i(tag: String, message: () -> String) {
        withTag(tag).i(message)
    }

    inline fun i(tag: String, message: String) {
        withTag(tag).i(message)
    }

    inline fun w(tag: String, message: () -> String) {
        withTag(tag).w(message)
    }

    inline fun w(tag: String, message: String) {
        withTag(tag).w(message)
    }
    fun w(tag: String, throwable: Throwable, message: () -> String) {
        withTag(tag).w(message(), throwable)
    }
    fun w(tag: String, throwable: Throwable, message: String) {
        withTag(tag).w(message, throwable)
    }

    inline fun e(tag: String, message: () -> String) {
        withTag(tag).e(message)
    }

    inline fun e(tag: String, message: String) {
        withTag(tag).e(message)
    }
    fun e(tag: String, throwable: Throwable, message: () -> String) {
        withTag(tag).e(message(), throwable)
    }
    fun e(tag: String, throwable: Throwable, message: String) {
        withTag(tag).e(message, throwable)
    }
}
