package dev.yidafu.cynops.appender.cleaner

import dev.yidafu.cynops.helpers.runOnLog
import dev.yidafu.cynops.listener.EventBus
import dev.yidafu.cynops.listener.EventListener
import kotlinx.coroutines.Job

/**
 *
 * @param checkInterval 大于 0 就会定时清理，小于 0 不会设置定时任务
 */
abstract class BaseCleaner(private val checkInterval: Long) : Cleaner, EventListener {
    private var job: Job? = null

    override fun onStart() {
        if (checkInterval > 0) {
            job =
                runOnLog {
//                    while (isStarted()) {
//                        clean()
//                        delay(100)
//                    }
                }
        }
    }

    override fun isStarted(): Boolean {
        return job != null
    }

    override fun onStop() {
        job?.cancel()
        job = null
    }

    override fun setEventBus(bus: EventBus) {
    }
}
