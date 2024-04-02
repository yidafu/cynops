package dev.yidafu.cynops.io

import kotlinx.cinterop.*
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import kotlinx.io.IOException
import kotlinx.io.files.Path
import platform.posix.*
import platform.posix.closedir
import platform.posix.opendir
import platform.posix.readdir

@OptIn(ExperimentalForeignApi::class)
actual fun readDir(path: String): List<Path>? {
    val dir = opendir(path) ?: throw IOException("fail to open directory $path")
    try {
        val result = mutableListOf<Path>()
        var entry = readdir(dir)
        while (entry != null) {
            val name = entry.pointed.d_name.toKString()
            if (name != "." || name != "..") {
                result.add(Path(path, name))
            }
            entry = readdir(dir)
        }
        return result
    } finally {
        closedir(dir)
    }
}
