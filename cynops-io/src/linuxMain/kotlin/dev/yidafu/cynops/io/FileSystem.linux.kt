package dev.yidafu.cynops.io

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.posix.stat

// @OptIn(ExperimentalForeignApi::class)
// actual fun readDir(path: String): List<Path>? {
//    val dir = opendir(path) ?: throw IOException("fail to open directory $path")
//    try {
//        val result = mutableListOf<Path>()
//        var entry = readdir(dir)
//        while (entry != null) {
//            val name = entry.pointed.d_name.toKString()
//            if (name != "." || name != "..") {
//                result.add(Path(path, name))
//            }
//            entry = readdir(dir)
//        }
//        return result
//    } finally {
//        closedir(dir)
//    }
// }

@OptIn(ExperimentalForeignApi::class)
actual fun fileInfo(path: String): FileInfo? {
    memScoped {
        val result = alloc<stat>()
        if (stat(path, result.ptr) != 0) {
            val mtime: Long = result.st_mtim.tv_sec * 1000
            val ctime: Long = result.st_ctim.tv_sec * 1000
            val size: Long = result.st_size
            return FileInfo(mtime, ctime, size)
        }
        return null
    }
}
