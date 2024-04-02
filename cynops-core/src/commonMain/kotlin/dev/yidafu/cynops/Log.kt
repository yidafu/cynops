package dev.yidafu.cynops

import dev.yidafu.cynops.helpers.DefaultMessageFormatter
import dev.yidafu.cynops.helpers.getPid

@Suppress("OVERRIDE_BY_INLINE")
open class Log(
    override val tag: String,
    protected val context: LoggerContext,
) : ILog {
    protected val ctxMap: MutableMap<String, String> = mutableMapOf()

    override fun with(vararg pairs: Pair<String, String>): Log {
        pairs.forEach { pair ->
            ctxMap[pair.first] = pair.second
        }
        return this
    }

    override fun getMap(): Map<String, String> = ctxMap

    fun log(event: ILogEvent) {
        context.sharedFlow.tryEmit(event)
    }

    fun log(
        level: Level,
        tag: String,
        message: String,
    ) {
        val event = LogEvent.create(level, tag, message, getMap())
        log(event)
    }

    inline fun v(message: String) {
        v(tag, message)
    }

    inline fun v(message: () -> String) {
        v(tag, message())
    }

    override fun v(
        tag: String,
        message: String,
    ) {
        log(Level.Info, tag, message)
    }

    override fun v(
        tag: String,
        message: () -> String,
    ) {
        v(tag, message())
    }

    inline fun d(message: String) {
        d(tag, message)
    }

    inline fun d(message: () -> String) {
        d(tag, message())
    }

    override fun d(
        tag: String,
        message: String,
    ) {
        log(Level.Debug, tag, message)
    }

    override fun d(
        tag: String,
        message: () -> String,
    ) {
        d(tag, message())
    }

    inline fun i(message: String) {
        i(tag, message)
    }

    inline fun i(message: () -> String) {
        i(tag, message())
    }

    override fun i(
        tag: String,
        message: String,
    ) {
        log(Level.Info, tag, message)
    }

    override fun i(
        tag: String,
        message: () -> String,
    ) {
        i(tag, message())
    }

    inline fun w(message: String) {
        w(tag, message)
    }

    inline fun w(message: () -> String) {
        w(tag, message())
    }

    inline fun w(
        message: String,
        throwable: Throwable,
    ) {
        w(tag, message, throwable)
    }

    override fun w(
        tag: String,
        message: String,
    ) {
        w(tag, message)
    }

    override fun w(
        tag: String,
        message: () -> String,
    ) {
        w(tag, message())
    }

    override fun w(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        w(tag, DefaultMessageFormatter.format(message, throwable))
    }

    inline fun e(message: String) {
        e(tag, message)
    }

    inline fun e(message: () -> String) {
        e(tag, message())
    }

    inline fun e(
        message: String,
        throwable: Throwable,
    ) {
        e(tag, message, throwable)
    }

    override fun e(
        tag: String,
        message: String,
    ) {
        log(Level.Error, tag, message)
    }

    override fun e(
        tag: String,
        message: () -> String,
    ) {
        e(tag, message())
    }

    override fun e(
        tag: String,
        message: String,
        throwable: Throwable,
    ) {
        e(tag, DefaultMessageFormatter.format(message, throwable))
    }

    override fun child(tag: String): Log {
        return ChildLog(this, tag, context)
    }
}

class ChildLog(private val parent: Log, childTag: String, loggerContext: LoggerContext) : Log(childTag, loggerContext) {
    override val tag: String by lazy {
        "${parent.tag}:$childTag"
    }

    /**
     * recursively merge parent ctxMap and child ctxMap
     */
    override fun getMap(): Map<String, String> {
        return parent.getMap() + ctxMap
    }
}

class RootLog(
    tag: String,
    loggerContext: LoggerContext,
) : Log(
        tag,
        loggerContext,
    ) {
    private val defaultMap =
        loggerContext.config.defaultContextMap +
            mapOf(
                LogEvent.TAG_TOPIC to loggerContext.config.topic,
                LogEvent.TAG_HOSTNAME to loggerContext.config.hostname,
                LogEvent.TAG_PID to getPid().toString(),
                LogEvent.TAG_ENV to loggerContext.config.env,
                LogEvent.TAG_LOGGER_NAME to tag,
            )

    override fun getMap(): Map<String, String> {
        return defaultMap + ctxMap
    }
}
