package dev.yidafu.cynops.helpers

import platform.posix.getpid

/**
 * get current process pid
 */
actual fun getPid(): Int {
    return getpid()
}