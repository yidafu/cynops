package dev.yidafu.cynops.appender.filter

import dev.yidafu.cynops.ILogEvent

interface Filter {
    fun decide(event: ILogEvent): FilterReply
}
