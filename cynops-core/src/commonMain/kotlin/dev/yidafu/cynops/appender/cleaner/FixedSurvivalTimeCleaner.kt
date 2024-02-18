package dev.yidafu.cynops.appender.cleaner

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
    val fileSystem: FileSystem = SystemFileSystem

    val Path.isDirectory get() = fileSystem.metadataOrNull(this)?.isDirectory == true
    val Path.isRegularFile get() = fileSystem.metadataOrNull(this)?.isRegularFile == true

    override fun clean() {
        val logDirPath = Path(logDir)
        if (logDirPath.isDirectory) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            val dir = fileSystem.source(logDirPath)
//            fileSystem.list(logDirPath)
//                .filter {
//                    it.isRegularFile && it.name.endsWith(".log")
//                }.forEach { path ->
//                    fileSystem.metadataOrNull(path)
//                        ?.lastModifiedAtMillis
//                        ?.let {
//                            if (currentTime - it > maxSurvivalTime) {
//                                fileSystem.delete(path)
//                            }
//                        }
//                }
        }
    }
}
