package dev.yidafu.cynops.sender

interface Sender {
    fun send(data: ByteArray): Boolean
}
