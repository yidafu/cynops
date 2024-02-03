package dev.yidafu.cynops

@Suppress("OVERRIDE_BY_INLINE")
class Log(val tag: String) : ILog {
    inline fun v(message: String) {
        v(tag, message)
    }
    inline fun v(message: () -> String) {
        v(tag, message())
    }

    override inline fun v(tag: String, message: String) {
    }

    override fun v(tag: String, message: () -> String) {
    }
    inline fun d(message: String) {
        d(tag, message)
    }
    inline fun d(message: () -> String) {
        d(tag, message())
    }
    override fun d(tag: String, message: String) {
    }

    override fun d(tag: String, message: () -> String) {
    }
    inline fun i(message: String) {
        d(tag, message)
    }
    inline fun i(message: () -> String) {
        d(tag, message())
    }
    override fun i(tag: String, message: String) {
    }

    override fun i(tag: String, message: () -> String) {
    }
    inline fun w(message: String) {
        w(tag, message)
    }
    inline fun w(message: () -> String) {
        w(tag, message())
    }

    inline fun w(message: String, throwable: Throwable) {
        w(tag, message, throwable)
    }

    override fun w(tag: String, message: String) {
    }

    override fun w(tag: String, message: () -> String) {
    }

    override fun w(tag: String, message: String, throwable: Throwable) {
    }
    inline fun e(message: String) {
        e(tag, message)
    }
    inline fun e(message: () -> String) {
        e(tag, message())
    }

    inline fun e(message: String, throwable: Throwable) {
        e(tag, message, throwable)
    }

    override fun e(tag: String, message: String) {
    }

    override fun e(tag: String, message: () -> String) {
    }

    override fun e(tag: String, message: String, throwable: Throwable) {
    }
}
