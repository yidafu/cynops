package dev.yidafu.cynops.sender

class DefaultSender : Sender {
    override fun send(data: ByteArray): Boolean {
        println("DefaultSender =>")
        println(data.toString(Charsets.UTF_8))
        return true
    }
}
