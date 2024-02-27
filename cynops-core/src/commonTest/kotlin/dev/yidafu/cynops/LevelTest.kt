package dev.yidafu.cynops

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LevelTest : FunSpec({
    test("level compare") {
        (Level.Debug < Level.Info) shouldBe true
        (Level.Info < Level.Warn) shouldBe true
        (Level.Warn < Level.Error) shouldBe true
    }

    test("Level to int") {
        Level.Info.toInt() shouldBe Level.INFO_INT
    }

    test("Level from string") {
        Level.from("off") shouldBe Level.Off
        Level.from("trace") shouldBe Level.Trace
        Level.from("debug") shouldBe Level.Debug
        Level.from("info") shouldBe Level.Info
        Level.from("warn") shouldBe Level.Warn
        Level.from("error") shouldBe Level.Error
        Level.from("all") shouldBe Level.All
        shouldThrow<IllegalArgumentException> { Level.from("test") }
    }

    test("Level from int") {
        Level.from(0) shouldBe Level.Trace
        Level.from(10) shouldBe Level.Debug
        Level.from(20) shouldBe Level.Info
        Level.from(30) shouldBe Level.Warn
        Level.from(40) shouldBe Level.Error
        Level.from(Int.MIN_VALUE) shouldBe Level.Off
        Level.from(Int.MAX_VALUE) shouldBe Level.All

        shouldThrow<IllegalArgumentException> { Level.from(999) }
    }
})

class LevelKotest : ShouldSpec({
    val levels = listOf(Level.Off, Level.Trace, Level.Debug, Level.Info, Level.Warn, Level.Error, Level.All)

    context("Level class") {
        should("correctly return the string representation with toString()") {
            for (level in levels) {
                level.toString() shouldBe
                    when (level) {
                        Level.Off -> "off"
                        Level.Trace -> "trace"
                        Level.Debug -> "debug"
                        Level.Info -> "info"
                        Level.Warn -> "warn"
                        Level.Error -> "error"
                        Level.All -> "all"
                    }
            }
        }

        should("return the correct integer value with toInt()") {
            for (level in levels) {
                level.toInt() shouldBe
                    when (level) {
                        Level.Off -> Level.OFF_INT
                        Level.Trace -> Level.TRACE_INT
                        Level.Debug -> Level.DEBUG_INT
                        Level.Info -> Level.INFO_INT
                        Level.Warn -> Level.WARN_INT
                        Level.Error -> Level.ERROR_INT
                        Level.All -> Level.ALL_INT
                    }
            }
        }

        should("compare correctly with compareTo()") {
            for (i in 0 until levels.size - 1) {
                levels[i].compareTo(levels[i + 1]) shouldBeLessThan 0
            }
            levels[levels.size - 1].compareTo(levels[0]) shouldBeGreaterThan 0
        }

        context("from(String)") {
            should("return the corresponding Level for a valid string") {
                Level.from("trace") shouldBe Level.Trace
            }

            should("throw IllegalArgumentException for an invalid string") {
                shouldThrow<IllegalArgumentException> { Level.from("invalid") }
            }
        }

        context("from(Int)") {
            should("return the corresponding Level for a valid code") {
                Level.from(Level.TRACE_INT) shouldBe Level.Trace
            }

            should("throw IllegalArgumentException for an invalid code") {
                shouldThrow<IllegalArgumentException> { Level.from(999) }
            }
        }
    }
})
