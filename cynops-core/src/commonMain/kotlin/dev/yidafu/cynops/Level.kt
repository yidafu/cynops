package dev.yidafu.cynops

/**
 * Log Level
 */
sealed class Level(
    private val code: Int,
    private val level: String,
) {
    object Off : Level(OFF_INT, OFF_STR)

    object Trace : Level(TRACE_INT, TRACE_STR)

    object Debug : Level(DEBUG_INT, DEBUG_STR)

    object Info : Level(INFO_INT, INFO_STR)

    object Warn : Level(WARN_INT, WARN_STR)

    object Error : Level(ERROR_INT, ERROR_STR)

    object All : Level(ALL_INT, ALL_STR)

    override fun toString(): String {
        return level
    }

    fun toInt(): Int {
        return code
    }

    operator fun compareTo(other: Level): Int {
        return this.code - other.code
    }

    companion object {
        const val OFF_INT = Int.MIN_VALUE
        const val TRACE_INT = 0
        const val DEBUG_INT = 10
        const val INFO_INT = 20
        const val WARN_INT = 30
        const val ERROR_INT = 40
        const val ALL_INT = Int.MAX_VALUE

        const val OFF_STR = "off"
        const val TRACE_STR = "trace"
        const val DEBUG_STR = "debug"
        const val INFO_STR = "info"
        const val WARN_STR = "warn"
        const val ERROR_STR = "error"
        const val ALL_STR = "all"

        fun from(level: String): Level {
            return when (level) {
                OFF_STR -> Off
                TRACE_STR -> Trace
                INFO_STR -> Info
                DEBUG_STR -> Debug
                WARN_STR -> Warn
                ERROR_STR -> Error
                ALL_STR -> All
                else -> throw IllegalArgumentException("$level can not be a valid Level String")
            }
        }

        fun from(code: Int): Level {
            return when (code) {
                OFF_INT -> Off
                TRACE_INT -> Trace
                INFO_INT -> Info
                DEBUG_INT -> Debug
                WARN_INT -> Warn
                ERROR_INT -> Error
                ALL_INT -> All
                else -> throw IllegalArgumentException("$code can not be a valid Level value")
            }
        }
    }
}
