package dev.yidafu.cynops.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
internal val context = newSingleThreadContext("cynops")

@OptIn(ExperimentalCoroutinesApi::class)
fun runOnLog(block: suspend () -> Unit): Job {
    return CoroutineScope(context).launch {
        block()
    }
}
