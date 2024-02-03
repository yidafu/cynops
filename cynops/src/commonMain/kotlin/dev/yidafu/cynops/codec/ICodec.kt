package dev.yidafu.cynops.codec

/**
 * encode/decode [E]
 */
interface ICodec<E> {
    fun encode(event: E): String

    fun decode(raw: String): E
}
