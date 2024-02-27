package dev.yidafu.cynops.sender

class DefaultSender : Sender {
    override fun send(data: ByteArray): Boolean {
        println("DefaultSender =>")
        println(data.toString())
        return true
    }
}
