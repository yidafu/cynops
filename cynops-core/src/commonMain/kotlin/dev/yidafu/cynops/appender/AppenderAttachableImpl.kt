package dev.yidafu.cynops.appender

internal class AppenderAttachableImpl : AppenderAttachable {
    private val appenderList = mutableListOf<Appender>()

    override fun addAppender(appender: Appender) {
        appenderList.add(appender)
    }

    override fun getAppender(name: String): Appender {
        return appenderList.find { it.name == name } ?: throw IllegalStateException("appender $name not found")
    }

    override fun detachAndStopAllAppenders() {
        appenderList.forEach { it.onStop() }
        appenderList.clear()
    }

    override fun detachAppender(name: String): Boolean {
        val appender = appenderList.find { it.name == name }
        return appender?.let {
            it.onStop()
            appenderList.remove(it)
            true
        } ?: false
    }

    override fun detachAppender(appender: Appender): Boolean {
        return appenderList.remove(appender)
    }

    override fun isAttached(appender: Appender): Boolean {
        return appenderList.any { it == appender }
    }

    override fun iterator(): Iterator<Appender> {
        return appenderList.iterator()
    }
}
