package dev.yidafu.cynops.appender.cleaner

/**
 * Nop implement
 */
class LazyCleaner(override val logDir: String = "", checkInterval: Long = -1) : BaseCleaner(checkInterval) {
    override fun clean() {
    }
}
