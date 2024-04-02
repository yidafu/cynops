package dev.yidafu.cynops.helpers

import kotlinx.datetime.LocalDateTime

actual fun LocalDateTime.format(format: String): String {
    return "$year--$monthNumber--$dayOfMonth"
}
