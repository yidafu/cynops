package dev.yidafu.cynops.appender.naming

import dev.yidafu.cynops.appender.naming.FileNamingStrategy
import java.text.SimpleDateFormat
import java.util.*

/**
 * generate file name like "2023-01-01.log"
 */
class DateFileNamingStrategy(override val name: String = "date") : FileNamingStrategy {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
    override fun generate(level: Int, timestamp: Long): String {
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(Date(timestamp))
    }
}
