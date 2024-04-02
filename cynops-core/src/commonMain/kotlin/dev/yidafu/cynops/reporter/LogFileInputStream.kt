package dev.yidafu.cynops.reporter

import kotlinx.io.RawSource

/**
 * log file input stream
 */
class LogFileInputStream(
    private val file: RawSource,
    offset: Long,
    private val bufferedSize: Int = 8 * 1024,
//    private val fileInputStream: FileInputStream = FileInputStream(file),
//    private val inputStream: BufferedInputStream = BufferedInputStream(fileInputStream, bufferedSize),
) {
    private val byteList = mutableListOf<Byte>()

    init {
//        inputStream.skip(offset)
    }

    fun read(): Int {
//        return inputStream.read()
        return 0
    }

    fun read(
        b: ByteArray,
        off: Int,
        len: Int,
    ): Int {
//        return inputStream.read(b, off, len)
        return 0
    }

    /**
     * read all lines that recently added to log file
     * return new line string only when '\n' arised
     */
    fun readLines(): List<String> {
        var char: Byte
        val lines = mutableListOf<String>()
//        while (read().also { char = it.toByte() } != -1) {
//            if (char == '\n'.toByte()) {
//                val line = String(byteList.toByteArray(), Charsets.UTF_8)
//                lines.add(line)
//                byteList.clear()
//            } else {
//                byteList.add(char)
//            }
//        }

        return lines
    }

    fun close() {
//        inputStream.close()
    }
}
