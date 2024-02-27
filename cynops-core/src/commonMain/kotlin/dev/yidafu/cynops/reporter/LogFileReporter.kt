package dev.yidafu.cynops.reporter

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.LogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import dev.yidafu.cynops.sender.LokiSteams
import dev.yidafu.cynops.sender.LokiStream
import dev.yidafu.cynops.sender.Sender
import dev.yidafu.loki.core.reporter.LogMeta
import dev.yidafu.loki.core.reporter.LogMetaFile

class LogFileReporter(
    private val logDirectory: String,
    reportInterval: Long,
    private val codec: ICodec<ILogEvent> = LogCodec,
    private val sender: Sender,
) : IntervalReporter(reportInterval) {
    private var metaFile: LogMetaFile? = null

    private val logStreamMap = mutableMapOf<String, LogFileInputStream>()

    private fun getStream(logMeta: LogMeta): LogFileInputStream {
        return logStreamMap[logMeta.filepath] ?: run {
            val stream = LogFileInputStream(logMeta.logFile, logMeta.pointer)
            logStreamMap[logMeta.filepath] = stream
            stream
        }
    }

    override fun report(logList: List<String>) {
        if (logList.isEmpty()) return

        val streams =
            logList.map { codec.decode(it) }
                .map { it as LogEvent }
                // 压缩 log
                .groupBy { it.uniqueKey }
                .values.map {
                    val tagMap = it[0].getMap()
                    val values =
                        it.map { event ->
                            arrayOf(event.timestamp, event.message)
                        }.toTypedArray()
                    LokiStream(tagMap, values)
                }.toTypedArray()
        val lokiSteams = LokiSteams(streams)
        sender.send(lokiSteams.toString().encodeToByteArray())
    }

    private fun initMetaFile() {
        metaFile = LogMetaFile(logDirectory)
    }

    override fun report() {
        metaFile?.getNeedReportFiles()?.forEach {
            val stream = getStream(it)
            val logs = stream.readLines()

            // 不管上报成功与否，都需要自增
            it.pointer += logs.fold(logs.size) { acc, s -> acc + s.length }
            it.row += logs.size
            try {
                report(logs)
            } catch (tr: Throwable) {
                println("send data to Loki failed ${tr.message}")
            }
        }
        metaFile?.updateMateFile()
    }

    override fun onStart() {
        if (isStarted()) return

        initMetaFile()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        // 只有 isStarted 才有必要 close
        if (isStarted()) {
            close()
        }
    }

    /**
     * 关闭持有的文件
     */
    private fun close() {
        logStreamMap.values.forEach { it.close() }
        logStreamMap.clear()
        metaFile?.close()
    }
}
