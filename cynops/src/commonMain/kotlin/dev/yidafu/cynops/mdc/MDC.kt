package dev.yidafu.cynops.mdc

object MDC {
    private val adapter: MDCAdapter
        get() {
            requireNotNull(mdcAdapter) { "MDCAdapter can't be null" }
            return mdcAdapter!!
        }

    fun put(key: String, value: String) {
        adapter.put(key, value)
    }

    fun get(key: String): String? {
        return adapter[key]
    }

    fun remove(key: String) {
        adapter.remove(key)
    }

    fun clear() {
        adapter.clear()
    }

    val copyOfContextMap: Map<String, String>
        get() = adapter.copyOfContextMap

    fun setContextMap(contextMap: Map<String, String>) {
        adapter.setContextMap(contextMap)
    }

    fun pushByKey(key: String, value: String) {
        adapter.pushByKey(key, value)
    }

    fun popByKey(key: String): String? {
        return adapter.popByKey(key)
    }

    fun getCopyOfDequeByKey(key: String): ArrayDeque<String>? {
        return adapter.getCopyOfDequeByKey(key)
    }
}
