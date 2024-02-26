package dev.yidafu.cynops.appender

import dev.yidafu.cynops.ILogEvent
import dev.yidafu.cynops.codec.ICodec
import dev.yidafu.cynops.codec.LogCodec
import dev.yidafu.cynops.config.CynopsConfig

abstract class SyncAppender : BaseAppender() {

    override lateinit var config: CynopsConfig

    override var encoder: ICodec<ILogEvent> = LogCodec
}
