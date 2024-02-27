package dev.yidafu.cynops

import dev.yidafu.cynops.appender.console
import dev.yidafu.cynops.appender.file
import dev.yidafu.cynops.helpers.runOnLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

fun main() {
    val logger =
        Logger {
            appender {
                console()
                file()
            }
        }
    runOnLog {
        logger.context.sharedFlow.collect {
            println("chanel collect")
        }
    }

    logger.context.sharedFlow.tryEmit(LogEvent.create(Level.Error, "test", "xxx"))
    repeat(21) {
        logger.i("tag") { "message" }
    }
    logger.context.stop()

    val flow = MutableSharedFlow<String>(0, 2, BufferOverflow.SUSPEND)

    flow.tryEmit("1111")
    flow.tryEmit("2222")
    flow.tryEmit("3333")

    GlobalScope.launch(Dispatchers.Unconfined) {
        flow.asSharedFlow().collect {
            println("collect $it")
        }
    }
    GlobalScope.launch(Dispatchers.IO) {
        flow.tryEmit("1111")
        flow.tryEmit("2222")
        flow.tryEmit("3333")
    }
}
