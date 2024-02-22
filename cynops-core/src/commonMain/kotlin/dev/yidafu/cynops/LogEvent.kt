package dev.yidafu.cynops

import dev.yidafu.cynops.helpers.getPid
import dev.yidafu.cynops.mdc.MDC
import kotlinx.datetime.Clock

const val TAG_TIMESTAMP = "timestamp"
const val TAG_TOPIC = "topic"
const val TAG_HOSTNAME = "hostname"
const val TAG_PID = "pid"
const val TAG_ENV = "env"
const val TAG_LEVEL = "level"
const val TAG_LOGGER_NAME = "name"

/**
 * ILogEvent Implement
 */
class LogEvent(
    override val timestamp: String,
    override val topic: String,
    override val hostname: String,
    override val pid: String,
    override val env: String,
    override val level: Level,
    override val tag: String,
    override val tagMap: Map<String, String>,
    override val message: String,
) : ILogEvent {
    /**
     * unique key of LogEvent
     */
    val uniqueKey by lazy {
        StringBuilder().apply {
            append(topic)
            append(',')
            append(hostname)
            append(',')
            append(pid)
            append(',')
            append(env)
            append(',')
            append(level)
            append(',')
            append(tag)
            append(',')
            append(tagMap.values.joinToString(","))
        }.toString()
    }

    /**
     * return all label. topic/hostname/pid/env/level/name is default label.
     */
    fun getMap(): Map<String, String> {
        return mapOf(
            "topic" to topic,
            "hostname" to hostname,
            "pid" to pid,
            "env" to env,
            "level" to level.toString(),
            "tag" to tag,
        ) + tagMap
    }

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
        ): LogEvent {
            val timestamp = getNanosecond()
            val map = MDC.copyOfContextMap
            println(map)
            val topic = map[TAG_TOPIC] ?: "unknown"
            val hostname = map[TAG_HOSTNAME] ?: "localhost"
            val pid = map[TAG_PID] ?: getPid().toString()
            val env = map[TAG_ENV] ?: "dev"

            val tagMap =
                map.filter {
                    it.key != TAG_TOPIC &&
                        it.key != TAG_HOSTNAME &&
                        it.key != TAG_PID &&
                        it.key != TAG_ENV
                }.toMap()
            return LogEvent(
                timestamp.toString(),
                topic,
                hostname,
                pid,
                env,
                level,
                loggerName,
                tagMap,
                message,
            )
        }
    }
}
