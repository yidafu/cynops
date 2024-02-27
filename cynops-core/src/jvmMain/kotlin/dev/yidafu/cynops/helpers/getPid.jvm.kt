package dev.yidafu.cynops.helpers

import java.lang.management.ManagementFactory

/**
 * get current process pid
 */
actual fun getPid(): Int {
    val runtimeMXBean = ManagementFactory.getRuntimeMXBean()
    return runtimeMXBean.name.split("@")[0].toInt()
}
