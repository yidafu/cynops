package dev.yidafu.cynops.appender.cleaner

import dev.yidafu.cynops.io.fileInfo
import dev.yidafu.cynops.io.readDir
import kotlinx.datetime.Clock
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

/**
 * 清除策略：固定保存时间
 * 超过[maxSurvivalTime]ms的日志文件删除掉
 */
class FixedSurvivalTimeCleaner(
    override val logDir: String,
    private val maxSurvivalTime: Long,
    checkInterval: Long,
) : BaseCleaner(checkInterval) {
    val fs: FileSystem = SystemFileSystem

    val Path.isDirectory get() = fs.metadataOrNull(this)?.isDirectory == true
    val Path.isRegularFile get() = fs.metadataOrNull(this)?.isRegularFile == true

    override fun clean() {
        val logDirPath = Path(logDir)
        if (logDirPath.isDirectory) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            readDir(logDirPath.toString())?.filter {
                it.isRegularFile && it.name.endsWith(".log")
            }?.forEach { path ->
                    fileInfo(path.toString())?.let {
                        if (currentTime - it.mtime > maxSurvivalTime) {
                            fs.delete(path, false)
                        }
                    }
            }
        }
    }
}
