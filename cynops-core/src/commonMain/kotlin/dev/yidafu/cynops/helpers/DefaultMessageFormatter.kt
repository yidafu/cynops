package dev.yidafu.cynops.helpers

import dev.yidafu.cynops.api.MessageFormatter

object DefaultMessageFormatter : MessageFormatter {
    override fun format(
        msg: String,
        throwable: Throwable?,
    ): String {
        return "$msg${if (throwable != null) {
            "\n${
                throwable.suppressedExceptions.joinToString("\n") {
                    it.stackTraceToString()
                }}"
        } else {
            ""
        }}"
    }

    override fun format(msg: String): String {
        return format(msg, null)
    }
}
