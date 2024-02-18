package dev.yidafu.loki.core.reporter

import kotlinx.io.RawSource
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * report log meta data
 */
@Serializable
data class LogMeta(
    /**
     * log file path
     */
    val filepath: String,
    /**
     * log file inode
     */
    val inode: Long,
    /**
     * pointer of already report content
     */
    var pointer: Long,
    /**
     * reported row
     */
    var row: Long = 0,
    /**
     * @hide
     */
    val isDone: Boolean = false,
    /**
     * File instance of [filepath]
     */
    @Transient
    val logFile: RawSource = SystemFileSystem.source(Path(filepath)),
) {
    fun isReportable(): Boolean {
        return false
//        return logFile.isFile && pointer < logFile.length()
    }
}
