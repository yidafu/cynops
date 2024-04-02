package dev.yidafu.cynops.appender

import kotlinx.io.Sink
import kotlinx.io.writeString

class LogFileOutputStream(private val file: Sink) {
    fun write(s: String) {
        file.writeString(s)
    }

    fun write(b: ByteArray) {
        file.write(b, b.size)
    }

    fun close() {
        file.close()
    }

    fun flush() {
        file.flush()
    }
}
