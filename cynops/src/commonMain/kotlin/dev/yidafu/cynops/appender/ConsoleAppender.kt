package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import java.io.PrintStream

class ConsoleAppender(override var name: String) : SyncAppender<ILogEvent>() {
    override var encoder: ICodec<ILogEvent> = LogCodec

    private val outputStream: PrintStream = System.out

    override fun writeOut(bytes: ByteArray) {
        outputStream.write(bytes)
        outputStream.write('\n'.code)
        outputStream.flush()
    }
}
