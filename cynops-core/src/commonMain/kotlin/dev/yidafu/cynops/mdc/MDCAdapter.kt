package dev.yidafu.cynops.mdc

internal var mdcAdapter: MDCAdapter? = DefaultMDCAdapter()

abstract class AbstractMDCAdapter : MDCAdapter {
    init {
        mdcAdapter = this
    }
}

class DefaultMDCAdapter() : AbstractMDCAdapter() {
    override val copyOfContextMap: Map<String, String>
        get() = map.toMap()

    private val map = mutableMapOf<String, String>()
    init {
        copyOfContextMap.forEach {
            put(it.key, it.value)
        }
    }
    override fun put(key: String, value: String) {
        map[key] = value
    }

    override fun get(key: String): String? = map[key]

    override fun remove(key: String) {
        map.remove(key)
    }

    override fun clear() {
        map.clear()
    }

    override fun setContextMap(contextMap: Map<String, String>) {
        clear()
        map.putAll(contextMap)
    }

    override fun pushByKey(key: String, value: String) {
        put(key, value)
    }

    override fun popByKey(key: String): String? = get(key)

    override fun getCopyOfDequeByKey(key: String): ArrayDeque<String>? {
        return ArrayDeque(map.keys)
    }

    override fun clearDequeByKey(key: String) = remove(key)
}

/**
 * This interface abstracts the service offered by various MDC
 * implementations.
 *
 * @author Ceki Glc
 * @since 1.4.1
 */
interface MDCAdapter {
    /**
     * Put a context value (the `val` parameter) as identified with
     * the `key` parameter into the current thread's context map.
     * The `key` parameter cannot be null. The `val` parameter
     * can be null only if the underlying implementation supports it.
     *
     *
     * If the current thread does not have a context map it is created as a side
     * effect of this call.
     */
    fun put(
        key: String,
        value: String,
    )

    /**
     * Get the context identified by the `key` parameter.
     * The `key` parameter cannot be null.
     *
     * @return the string value identified by the `key` parameter.
     */
    operator fun get(key: String): String?

    /**
     * Remove the context identified by the `key` parameter.
     * The `key` parameter cannot be null.
     *
     *
     *
     * This method does nothing if there is no previous value
     * associated with `key`.
     */
    fun remove(key: String)

    /**
     * Clear all entries in the MDC.
     */
    fun clear()

    /**
     * Return a copy of the current thread's context map, with keys and
     * values of type String. Returned value may be null.
     *
     * @return A copy of the current thread's context map. May be null.
     * @since 1.5.1
     */
    val copyOfContextMap: Map<String, String>

    /**
     * Set the current thread's context map by first clearing any existing
     * map and then copying the map passed as parameter. The context map
     * parameter must only contain keys and values of type String.
     *
     * Implementations must support null valued map passed as parameter.
     *
     * @param contextMap must contain only keys and values of type String
     *
     * @since 1.5.1
     */
    fun setContextMap(contextMap: Map<String, String>)

    /**
     * Push a value into the deque(stack) referenced by 'key'.
     *
     * @param key identifies the appropriate stack
     * @param value the value to push into the stack
     * @since 2.0.0
     */
    fun pushByKey(
        key: String,
        value: String,
    )

    /**
     * Pop the stack referenced by 'key' and return the value possibly null.
     *
     * @param key identifies the deque(stack)
     * @return the value just popped. May be null/
     * @since 2.0.0
     */
    fun popByKey(key: String): String?

    /**
     * Returns a copy of the deque(stack) referenced by 'key'. May be null.
     *
     * @param key identifies the  stack
     * @return copy of stack referenced by 'key'. May be null.
     *
     * @since 2.0.0
     */
    fun getCopyOfDequeByKey(key: String): ArrayDeque<String>?

    /**
     * Clear the deque(stack) referenced by 'key'.
     *
     * @param key identifies the  stack
     *
     * @since 2.0.0
     */
    fun clearDequeByKey(key: String)
}
