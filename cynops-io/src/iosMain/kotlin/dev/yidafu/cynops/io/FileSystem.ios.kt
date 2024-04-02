package dev.yidafu.cynops.io

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.stat

// actual fun readDir(path: String): List<Path>? {
//    TODO("Not yet implemented")
// }

@OptIn(ExperimentalForeignApi::class)
actual fun fileInfo(path: String): FileInfo? {
    memScoped {
        val result = alloc<stat>()
        if (stat(path, result.ptr) != 0) {
            val mtime: Long = result.st_mtimespec.tv_sec * 1000
            val ctime: Long = result.st_ctimespec.tv_sec * 1000
            val size: Long = result.st_size
            return FileInfo(mtime, ctime, size)
        }
        return null
    }
}
