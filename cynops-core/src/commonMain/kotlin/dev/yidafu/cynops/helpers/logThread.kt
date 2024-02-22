package dev.yidafu.cynops.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun runOnLog(block: suspend  CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Unconfined).launch(block = block)
}

fun runOnIo(block: suspend  CoroutineScope.() -> Unit): Job {
    return CoroutineScope(Dispatchers.Unconfined).launch(block = block)
}
