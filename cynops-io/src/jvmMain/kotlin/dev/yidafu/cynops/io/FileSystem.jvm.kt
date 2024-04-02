package dev.yidafu.cynops.io

import kotlinx.io.files.Path
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes

actual fun readDir(path: String): List<Path>? {
    val file = File(path)
    if (file.exists() && file.isDirectory) {
        return file.listFiles()?.map { Path(it.absolutePath) }?.toList()
    }
    return null
}

actual fun fileInfo(path: String): FileInfo? {
    val file = File(path)
    val p = file.toPath()
    if (file.exists()) {
        val ctime = Files.readAttributes(p, BasicFileAttributes::class.java).creationTime().toMillis()
        return FileInfo(file.lastModified(), ctime, file.length())
    }
    return null
}
