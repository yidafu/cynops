package dev.yidafu.cynops.io

import kotlinx.io.files.Path
import java.io.File

actual fun readDir(path: String): List<Path>? {
    val file = File(path)
    if (file.exists() && file.isDirectory) {
        return file.listFiles()?.map { Path(it.absolutePath) }?.toList()
    }
    return null
}

actual fun fileInfo(path: String): FileInfo? {
    val file = File(path)
    if (file.exists()) {
        val ctime = file.lastModified()
        return FileInfo(file.lastModified(), ctime, file.length())
    }
    return null
}
