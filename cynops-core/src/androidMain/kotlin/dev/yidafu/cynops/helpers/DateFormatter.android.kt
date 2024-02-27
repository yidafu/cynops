package dev.yidafu.cynops.helpers

import android.annotation.TargetApi
import android.os.Build
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

@TargetApi(Build.VERSION_CODES.O)
actual fun LocalDateTime.format(format: String): String = DateTimeFormatter.ofPattern(format).format(this.toJavaLocalDateTime())
