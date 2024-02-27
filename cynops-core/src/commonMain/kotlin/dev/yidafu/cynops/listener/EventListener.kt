package dev.yidafu.cynops.listener

interface EventListener {
    /**
     * start hook
     */
    fun onStart()

    /**
     * if called onStart return true, else return false
     */
    fun isStarted(): Boolean

    /**
     * stop hook
     */
    fun onStop()

    /**
     * add current EventListener to event bus
     */

    fun setEventBus(bus: EventBus)
}
