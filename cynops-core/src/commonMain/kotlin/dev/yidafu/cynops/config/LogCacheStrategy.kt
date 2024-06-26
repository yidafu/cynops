package dev.yidafu.cynops.config

import kotlinx.coroutines.channels.BufferOverflow

sealed class LogCacheStrategy(
    val replay: Int = 0,
    val bufferCapacity: Int = 0,
    val overflow: BufferOverflow,
) {
    data object SuspendCacheStrategy : LogCacheStrategy(
        0,
        10,
        BufferOverflow.SUSPEND,
    )

    class DropFirstCacheStrategy(
        replay: Int = 0,
        bufferCapacity: Int = 100,
    ) : LogCacheStrategy(replay, bufferCapacity, BufferOverflow.DROP_LATEST)

    class DropLastCacheStrategy(
        replay: Int = 0,
        bufferCapacity: Int = 100,
    ) : LogCacheStrategy(replay, bufferCapacity, BufferOverflow.DROP_LATEST)
}
