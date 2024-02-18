package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.appender.cleaner.FixedSurvivalTimeCleaner
import dev.yidafu.cynops.appender.naming.FileNamingStrategy
import dev.yidafu.cynops.appender.naming.NamingStrategyFactory
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import kotlinx.datetime.Clock
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.time.DurationUnit

/**
 * write log to file
 */
class FileAppender(
    private val logDir: String,
    namingStrategyName: String,
    maxSurvivalTime: Long = 7.days.inWholeMilliseconds,
    cleanerCheckInterval: Long = 5.minutes.toLong(DurationUnit.MILLISECONDS),
    override var name: String = "FILE",
    override var encoder: ICodec<ILogEvent> = LogCodec,
    private val namingStrategy: FileNamingStrategy = NamingStrategyFactory.getStrategy(namingStrategyName),
) : AsyncAppender<ILogEvent>() {
    private var outputStream: LogFileOutputStream? = null

    private val cleaner = FixedSurvivalTimeCleaner(logDir, maxSurvivalTime, cleanerCheckInterval)

    private val fs = SystemFileSystem
    private val logFilePath: String
        get() = "$logDir/${namingStrategy.generate(0, Clock.System.now().toEpochMilliseconds())}.log"

    override fun onStart() {
        super.onStart()
        cleaner.onStart()
        val logFile = fs.source(Path(logFilePath))
        outputStream = LogFileOutputStream(logFile)
    }

    override fun onStop() {
        outputStream?.flush()
        outputStream?.close()
        cleaner.onStop()
        super.onStop()
    }

    override fun filterLevel(event: ILogEvent): Boolean {
        return true
    }

    override suspend fun asyncAppend(eventArray: ArrayList<ILogEvent>) {
        val evtStr = eventArray.joinToString("\n") { encoder.encode(it) } + "\n"

        outputStream?.write(evtStr.encodeToByteArray())
    }
}
