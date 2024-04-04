package dev.yidafu.cynops.appender.naming

import dev.yidafu.cynops.helpers.format
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * generate file name like "2023-01-01.log"
 */
class DateFileNamingStrategy(override val name: String = "date") : FileNamingStrategy {
    override fun generate(
        level: Int,
        timestamp: Long,
    ): String {
        val date = Instant.fromEpochMilliseconds(timestamp)
        return date.toLocalDateTime(TimeZone.UTC).format("YYYY-MM-dd")
    }
}
