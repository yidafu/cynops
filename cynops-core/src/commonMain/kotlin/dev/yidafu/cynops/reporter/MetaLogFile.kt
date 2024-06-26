package dev.yidafu.loki.core.reporter

import kotlinx.io.Buffer
import kotlinx.io.RawSource
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/**
 * instance of `meta.json`
 *
 * constructor class will create a File instance of `meta.json`
 */
internal class LogMetaFile(private val logDirectory: String) : RawSource {
    private val metaFilename = "meta.json"
    private val closed = false
    private val filepath
        get() = "$logDirectory/$metaFilename"

    private val metaFile = SystemFileSystem.source(Path(filepath))

    private var meteFieList: MutableList<LogMeta> = mutableListOf()

    private val output =
        run {
            if (SystemFileSystem.exists(Path(filepath))) {
//                val buf = CharArray(1024)
//                val reader = FileReader(metaFile)
//                val jsonStr = StringBuilder()
//                var len = 0
//                while (reader.read(buf).also { len = it } != -1) {
//                    jsonStr.append(buf.sliceArray(0..<len).concatToString())
//                }
//
//                meteFieList = Json.decodeFromString<MutableList<LogMeta>>(if (jsonStr.isEmpty()) "[]" else jsonStr.toString())
//            }
//
//            RandomAccessFile(metaFile, "rw").apply {
//                write(Json.encodeToString(meteFieList).toByteArray())
            }
        }

    init {
        val dir = SystemFileSystem.source(Path(logDirectory))
        val fileList = mutableListOf<LogMeta>()
//
//        dir.listFiles()?.filterNotNull()
//            ?.filter { it.isFile && it.extension == "log" }
//            ?.forEach {
//                if (!meteFieList.any { f -> f.filepath == it.absolutePath }) {
//                    // TODO: 暂时不支持 inode
//                    fileList.add(LogMeta(it.absolutePath, 0, 0))
//                }
//            }

        meteFieList.addAll(fileList)
        updateMateFile()
    }

    /**
     * get LogMeta list that has new content to report
     */
    fun getNeedReportFiles(): List<LogMeta> {
        return meteFieList.filter {
            it.isReportable()
        }
    }

    /**
     * write data to `meta.json`
     */
    fun updateMateFile() {
//        if (output.fd.valid()) {
//            output.seek(0)
//            output.write(Json.encodeToString(meteFieList).toByteArray())
//        }
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     *
     *  As noted in [AutoCloseable.close], cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * *mark* the `Closeable` as closed, prior to throwing
     * the `IOException`.
     *
     * @throws [java.io.IOException] if an I/O error occurs
     */
    override fun close() {
        if (closed) return

        updateMateFile()
//        output.close()
    }

    override fun readAtMostTo(
        sink: Buffer,
        byteCount: Long,
    ): Long {
        TODO("Not yet implemented")
    }
}
