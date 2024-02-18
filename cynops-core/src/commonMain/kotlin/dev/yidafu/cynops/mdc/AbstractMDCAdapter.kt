package dev.yidafu.cynops.mdc

internal var mdcAdapter: MDCAdapter? = null

abstract class AbstractMDCAdapter : MDCAdapter {
    init {
        mdcAdapter = this
    }
}
