package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec

abstract class SyncAppender : BaseAppender() {
    override var encoder: ICodec<ILogEvent> = LogCodec
}
