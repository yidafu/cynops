package dev.yidafu.cynops.api

interface MessageFormatter {
    fun format(msg: String, throwable: Throwable?): String
    fun format(msg: String): String
}
