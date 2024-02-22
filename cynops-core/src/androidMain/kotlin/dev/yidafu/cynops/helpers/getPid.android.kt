package dev.yidafu.cynops.helpers

import android.os.Process

/**
 * get current process pid
 */
actual fun getPid(): Int {
    return Process.myPid()
}