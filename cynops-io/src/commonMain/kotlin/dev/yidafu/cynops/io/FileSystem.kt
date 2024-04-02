package dev.yidafu.cynops.io

import kotlinx.io.files.Path

expect fun readDir(path: String): List<Path>?

expect fun fileInfo(path: String): FileInfo?
