package dev.yidafu.cynops.codec

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.Level
import dev.yidafu.cynops.LogEvent

/**
 * ILogEvent <=> Strings
 */
object LogCodec : ICodec<ILogEvent> {
    /**
     * transform [ILogEvent] to **one** line String.
     * [ILogEvent.message] will replace '\n' to '\\n'
     */
    override fun encode(event: ILogEvent): String {
        val extraTagKes = event.tagMap.keys.filter {
            !arrayOf(LogEvent.TAG_HOSTNAME, LogEvent.TAG_ENV, LogEvent.TAG_TOPIC, LogEvent.TAG_LEVEL, LogEvent.TAG_PID, LogEvent.TAG_LOGGER_NAME).contains(it)
        }
        return StringBuilder().apply {
            append(event.timestamp)
            append(" <")
            append(event.topic)
            append("> <")
            append(event.hostname)
            append("> <")
            append(event.pid)
            append("> <")
            append(event.env)
            append("> ")
            append(event.level.toString())
            append(" (")
            append(event.tag)
            append(") ")
            extraTagKes.forEach { key ->
                val value = event.tagMap[key]
                append("[$key:$value] ")
            }
            append("- ")
            append(event.message.replace("\n", "\\n"))
        }.toString()
    }

    /**
     * decode string to [ILogEvent]
     *
     * String format
     * ```
     * timestamp <topic> <hostname/machine id> <pid/thread name> <env> Level (tag name) [key:value] [key:value] - message content
     * ````
     */
    override fun decode(raw: String): ILogEvent {
        val firstWhitespace = raw.indexOf(WHITESPACE)
        val timestamp =
            if (firstWhitespace > -1) {
                raw.slice(0..<firstWhitespace)
            } else {
                // 上报时过滤掉
                ""
            }
        var index = firstWhitespace

        val tagMap = mutableMapOf<String, String>()
        val topicIndex = raw.indexOf(WHITESPACE, index + 1)
        val topic =
            if (topicIndex > -1) {
                val s = raw.slice((index + 2)..<(topicIndex - 1))
                index = topicIndex
                s
            } else {
                "Unknown"
            }
        tagMap[LogEvent.TAG_TOPIC]  = topic
        val hostnameIndex = raw.indexOf(WHITESPACE, index + 1)
        val hostname =
            if (hostnameIndex > -1) {
                val s = raw.slice((index + 2)..<(hostnameIndex - 1))
                index = hostnameIndex
                s
            } else {
                "-"
            }
        tagMap[LogEvent.TAG_HOSTNAME]  = hostname

        val pidIndex = raw.indexOf(WHITESPACE, index + 1)
        val pid =
            if (pidIndex > -1) {
                val s = raw.slice((index + 2)..<(pidIndex - 1))
                index = pidIndex
                s
            } else {
                "-1"
            }
        tagMap[LogEvent.TAG_PID]  = pid

        val envIndex = raw.indexOf(WHITESPACE, index + 1)
        val env =
            if (envIndex > -1) {
                val s = raw.slice((index + 2)..<(envIndex - 1))
                index = envIndex
                s
            } else {
                "-"
            }
        tagMap[LogEvent.TAG_ENV]  = env

        val levelIndex = raw.indexOf(WHITESPACE, index + 1)
        val level =
            if (levelIndex > -1) {
                val s = raw.slice((index + 1)..<levelIndex)
                index = levelIndex
                s
            } else {
                Level.INFO_STR
            }
        tagMap[LogEvent.TAG_LEVEL]  = level

        val tagIndex = raw.indexOf(WHITESPACE, index + 1)
        val tag =
            if (tagIndex > -1) {
                val s = raw.slice((index + 2)..<(tagIndex - 1))
                index = tagIndex
                s
            } else {
                "Unknown"
            }
        tagMap[LogEvent.TAG_LOGGER_NAME]  = tag

        while (index + 1 < raw.length && raw[index + 1] == '[') {
            val rightIndex = raw.indexOf(']', index)
            if (rightIndex > -1) {
                val pair = raw.slice((index + 2)..<rightIndex)
                val (key, value) = pair.split(':')
                tagMap[key] = value
                index = rightIndex + 1
            } else {
                break
            }
        }
        val msgLeftIndex = index + 1
        val msgIndex = raw.indexOf('-', msgLeftIndex)
        val msg =
            if (msgIndex > -1) {
                raw.slice((msgIndex + 2)..<raw.length).replace("\\n", "\n")
            } else {
                ""
            }

        return LogEvent(
            timestamp,
            msg,
            Level.from(level),
            tagMap,
        )
    }

    private const val WHITESPACE = ' '
}
