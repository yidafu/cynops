package dev.yidafu.cynops.appender

import android.util.Log
import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.Level
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.PlainCodec

actual class ConsoleAppender actual constructor() : SyncAppender() {
    actual override var name: String = "Console"

    override var encoder: ICodec<ILogEvent> = PlainCodec

    override fun doAppend(event: ILogEvent) {
        when (event.level) {
            Level.Debug -> Log.d(event.tag, encoder.encode(event))
            Level.Error -> Log.e(event.tag, encoder.encode(event))
            Level.Info -> Log.e(event.tag, encoder.encode(event))
            Level.Trace -> Log.v(event.tag, encoder.encode(event))
            Level.Warn -> Log.w(event.tag, encoder.encode(event))
            else -> {
                // do nothing
            }
        }
    }
}
