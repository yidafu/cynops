package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.appender.cleaner.FixedSurvivalTimeCleaner
import dev.yidafu.cynops.appender.naming.FileNamingStrategy
import dev.yidafu.cynops.appender.naming.NamingStrategyFactory
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import dev.yidafu.cynops.config.CynopsConfig
import kotlinx.datetime.Clock
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

data class CynopsConfigFileAppender(
    var logDir: String,
    var namingStrategyName: String,
    var maxSurvivalTime: Long = 7.days.inWholeMilliseconds,
//    var namingStrategy: FileNamingStrategy,
    var cleanerCheckInterval: Long = 5.minutes.inWholeMilliseconds
)

fun CynopsConfig.file() {
    appenderList.add(FileAppender(this))
}

internal fun CynopsConfig.fileAppender(block: CynopsConfigFileAppender.() -> Unit) {
    _fileAppender.apply(block)
}

internal val CynopsConfig._fileAppender: CynopsConfigFileAppender
    get() = CynopsConfigFileAppender("./logs", "data")

/**
 * write log to file
 */
class FileAppender(
    config: CynopsConfig,
    override var name: String = "FILE",
    override var encoder: ICodec<ILogEvent> = LogCodec,
) : BufferedAppender(config) {
    private val namingStrategy: FileNamingStrategy = NamingStrategyFactory.getStrategy(
        config._fileAppender.namingStrategyName
    )

    private var outputStream: LogFileOutputStream? = null

    private val logDir
        get() = config._fileAppender.logDir

    private val cleaner = FixedSurvivalTimeCleaner(
        config._fileAppender.logDir,
        config._fileAppender.maxSurvivalTime,
        config._fileAppender.cleanerCheckInterval
    )

    private val fs = SystemFileSystem
    private val logFilePath: String
        get() = "$logDir/${namingStrategy.generate(0, Clock.System.now().toEpochMilliseconds())}.log"

    override fun onStart() {
        super.onStart()
        cleaner.onStart()
        val path = Path(logFilePath)
        path.parent?.let {
            if (!fs.exists(it)) {
                fs.createDirectories(it)
            }
        }

        val logFile = fs.sink(path, true).buffered()
        outputStream = LogFileOutputStream(logFile)
    }

    override fun onStop() {
        super.onStop()
        outputStream?.flush()
        outputStream?.close()
        cleaner.onStop()
    }

    override suspend fun asyncAppend(eventArray: List<ILogEvent>) {
        val evtStr = eventArray.joinToString("\n") { encoder.encode(it) } + "\n"
        outputStream?.write(evtStr.encodeToByteArray())
        outputStream?.flush()
    }
}
