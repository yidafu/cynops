package dev.yidafu.cynops.reporter

import dev.yidafu.cynops.helpers.runOnLog
import dev.yidafu.cynops.listener.EventBus
import dev.yidafu.loki.core.reporter.Reporter
import kotlinx.coroutines.*

abstract class IntervalReporter(private val reportInterval: Long) : Reporter {
    private var _started = false

    private var intervalJob: Job? = null

    override fun onStart() {
        if (isStarted()) return

        _started = true
        intervalJob =
            runOnLog {
                while (_started) {
                    delay(reportInterval)
                    if (!isStarted()) break
                    report()
                }
            }
    }

    override fun isStarted(): Boolean {
        return _started
    }

    /**
     * reporter stop 时会释放文件 fd，需要调用 [onStart] 重新初始化
     */
    override fun onStop() {
        if (!isStarted()) return

        _started = false
        intervalJob?.cancel()
    }

    override fun setEventBus(bus: EventBus) {
        bus.addListener(this)
    }
}
