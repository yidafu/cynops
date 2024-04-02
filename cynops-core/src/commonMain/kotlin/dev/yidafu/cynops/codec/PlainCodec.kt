package dev.yidafu.cynops.codec

import dev.yidafu.cynops.ILogEvent
import kotlinx.datetime.Instant

object PlainCodec : ICodec<ILogEvent> {
    /**
     *
     */
    override fun encode(event: ILogEvent): String {
        return buildString {
            append(
                Instant.fromEpochMilliseconds(
                    event.timestamp.substring(0, event.timestamp.length - 6).toLong(),
                ).toString(),
            )
            append(" [")
            append(event.hostname)
            append(" - ")
            append(event.env)
            append(" - ")
            append(event.pid)
            append("] ")

            append(event.level)

            append(" [")
            append(event.topic)
            append("] ")
            // ignore other tag
            append(event.message)
        }
    }

    override fun decode(raw: String): ILogEvent {
        TODO("decode never implement")
    }
}
