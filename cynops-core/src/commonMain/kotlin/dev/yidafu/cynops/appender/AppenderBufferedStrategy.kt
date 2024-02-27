package dev.yidafu.cynops.appender

sealed class AppenderBufferedStrategy(val maxSize: Int, val interval: Int) {
    class MaxBufferedSizeStrategy(maxSize: Int = 20) : AppenderBufferedStrategy(20, Int.MAX_VALUE)

    class IntervalBufferedStrategy(interval: Int = 100) : AppenderBufferedStrategy(Int.MAX_VALUE, interval)

    class MixedBufferedStrategy(maxSize: Int, interval: Int) : AppenderBufferedStrategy(maxSize, interval)
}
