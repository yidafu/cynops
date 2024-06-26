package dev.yidafu.cynops

interface ILog {
    val tag: String

    fun with(vararg pairs: Pair<String, String>): ILog

    fun getMap(): Map<String, String>

    fun v(
        tag: String,
        message: String,
    )

    fun v(
        tag: String,
        message: () -> String,
    )

    fun d(
        tag: String,
        message: String,
    )

    fun d(
        tag: String,
        message: () -> String,
    )

    fun i(
        tag: String,
        message: String,
    )

    fun i(
        tag: String,
        message: () -> String,
    )

    fun w(
        tag: String,
        message: String,
    )

    fun w(
        tag: String,
        message: () -> String,
    )

    fun w(
        tag: String,
        message: String,
        throwable: Throwable,
    )

    fun e(
        tag: String,
        message: String,
    )

    fun e(
        tag: String,
        message: () -> String,
    )

    fun e(
        tag: String,
        message: String,
        throwable: Throwable,
    )

    fun child(tag: String): ILog
}
