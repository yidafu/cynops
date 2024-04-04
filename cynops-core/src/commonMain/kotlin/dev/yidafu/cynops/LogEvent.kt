package dev.yidafu.cynops

import dev.yidafu.cynops.helpers.getPid
import kotlinx.datetime.Clock

/**
 * ILogEvent Implement
 */
class LogEvent(
    override val timestamp: String,
    override val message: String,
    /**
     * 独立字段
     */
    override val level: Level,
    override val tagMap: Map<String, String>,
) : ILogEvent {
    override val topic = tagMap[TAG_TOPIC] ?: "Unknown"
    override val hostname = tagMap[TAG_HOSTNAME] ?: "localhost"
    override val pid by lazy {
        tagMap[TAG_PID] ?: getPid().toString()
    }
    override val env = tagMap[TAG_ENV] ?: "dev"
    override val tag = tagMap[TAG_LOGGER_NAME] ?: "Unknown"

    /**
     * unique key of LogEvent
     */
    val uniqueKey by lazy {
        tagMap.values.joinToString(",")
    }

    /**
     * return all label. topic/hostname/pid/env/level/name is default label.
     */
    fun getMap(): Map<String, String> = tagMap

    override fun equals(other: Any?): Boolean {
        if (other is LogEvent) {
            return timestamp == other.timestamp &&
                topic == other.topic &&
                hostname == other.hostname &&
                pid == other.pid &&
                env == other.env &&
                level == other.level &&
                tag == other.tag &&
                message == other.message &&
                tagMap == other.tagMap
        }
        return false
    }

    override fun hashCode(): Int {
        var result = timestamp.hashCode()
        result = 31 * result + topic.hashCode()
        result = 31 * result + hostname.hashCode()
        result = 31 * result + pid.hashCode()
        result = 31 * result + env.hashCode()
        result = 31 * result + level.hashCode()
        result = 31 * result + tag.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + tagMap.hashCode()

        return result
    }

    companion object {
        const val TAG_TIMESTAMP = "timestamp"
        const val TAG_TOPIC = "topic"
        const val TAG_HOSTNAME = "hostname"
        const val TAG_PID = "pid"
        const val TAG_ENV = "env"
        const val TAG_LEVEL = "level"
        const val TAG_LOGGER_NAME = "name"

        private fun getNanosecond(): Long {
            val instant = Clock.System.now()

            val seconds = instant.epochSeconds
            val nano = instant.nanosecondsOfSecond
            return seconds * 1_000_000_000 + nano
        }

        /**
         * create LokiLogEvent
         */
        fun create(
            level: Level,
            loggerName: String,
            message: String,
            ctxMap: Map<String, String>,
        ): LogEvent {
            val timestamp = getNanosecond()

            val tagMap =
                ctxMap +
                    mapOf(
                        TAG_LOGGER_NAME to loggerName,
                        TAG_LEVEL to level.toString(),
                    )
            return LogEvent(
                timestamp.toString(),
                message,
                level,
                tagMap,
            )
        }
    }
}
