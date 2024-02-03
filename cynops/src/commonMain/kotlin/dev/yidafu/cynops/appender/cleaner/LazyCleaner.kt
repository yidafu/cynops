package dev.yidafu.cynops.appender.cleaner

import dev.yidafu.cynops.appender.cleaner.BaseCleaner

/**
 * Nop implement
 */
class LazyCleaner(override val logDir: String = "", checkInterval: Long = -1) : BaseCleaner(checkInterval) {
    override fun clean() {
    }
}
