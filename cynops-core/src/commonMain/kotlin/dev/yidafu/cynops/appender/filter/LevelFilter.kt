package dev.yidafu.cynops.appender.filter

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.Level

class LevelFilter(private val minLevel: Level) : Filter {
    override fun decide(event: ILogEvent): FilterReply {
        return if (event.level <= minLevel) FilterReply.ACCEPT else FilterReply.DENY
    }
}
