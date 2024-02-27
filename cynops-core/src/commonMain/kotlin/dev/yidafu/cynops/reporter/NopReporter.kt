package dev.yidafu.loki.core.reporter

import dev.yidafu.cynops.reporter.IntervalReporter

class NopReporter(reportInterval: Long) : IntervalReporter(reportInterval) {
    override fun report(logList: List<String>) {
    }

    override fun report() {
        report(listOf())
    }
}
