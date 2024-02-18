package dev.yidafu.cynops.appender

import kotlinx.io.RawSource

class LogFileOutputStream(private val file: RawSource) {
//    private val outputStream: BufferedOutputStream
//    private val fileOutputStream: FileOutputStream

//    private val BUFFER_SIZE: Int = 8 * 1024
    init {
        file

        // 实时地把日志写入文件里
//        fileOutputStream = FileOutputStream(file, true)
//        outputStream = BufferedOutputStream(fileOutputStream, BUFFER_SIZE)
    }

    fun write(b: ByteArray) {
        safeWrite(b)
    }

    private fun safeWrite(buf: ByteArray) {
//        val channel = fileOutputStream.channel
//        var fileLock: FileLock? = null
//        try {
//            fileLock = channel.lock()
//            fileOutputStream.write(buf)
//        } catch (e: IOException) {
//            println("LogFileOutputStream write byte array failed\n\t${e.stackTraceToString()}")
//        } finally {
//            fileLock?.release()
//        }
    }

    fun write(
        b: ByteArray,
        off: Int,
        len: Int,
    ) {
//        fileOutputStream.write(b, off, len)
    }

    /**
     * Writes the specified byte to this output stream. The general
     * contract for `write` is that one byte is written
     * to the output stream. The byte to be written is the eight
     * low-order bits of the argument `b`. The 24
     * high-order bits of `b` are ignored.
     *
     *
     * Subclasses of `OutputStream` must provide an
     * implementation for this method.
     *
     * @param b   the `byte`.
     * @throws [java.io.IOException]  if an I/O error occurs. In particular,
     * an `IOException` may be thrown if the
     * output stream has been closed.
     */
    fun write(b: Int) {
//        fileOutputStream.write(b)
    }

    fun close() {
//        fileOutputStream.close()
    }

    fun flush() {
//        fileOutputStream.flush()
    }
}
