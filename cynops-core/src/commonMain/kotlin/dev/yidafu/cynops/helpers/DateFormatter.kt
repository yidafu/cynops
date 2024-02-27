package dev.yidafu.cynops.helpers

import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(format: String): String
