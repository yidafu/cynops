package dev.yidafu.cynops.appender

import kotlinx.io.Sink

class LogFileOutputStream(private val file: Sink) {

    fun write(b: ByteArray) {
        println("log file write")
        safeWrite(b)
    }

    private fun safeWrite(buf: ByteArray) {
        file.write(buf)
    }

    fun close() {
        file.close()
    }

    fun flush() {
        file.flush()
    }
}
