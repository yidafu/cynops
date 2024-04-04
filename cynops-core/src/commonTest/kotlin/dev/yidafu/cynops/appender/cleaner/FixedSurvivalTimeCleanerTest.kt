package dev.yidafu.cynops.appender.cleaner

import dev.yidafu.cynops.io.readDir
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.writeString

class FixedSurvivalTimeCleanerTest : FunSpec({
    beforeEach {
        SystemFileSystem.createDirectories(Path("/tmp/log/cleaner-test"))
    }
    test("FixedSurvivalTimeCleaner") {
        SystemFileSystem.sink(Path("/tmp/log/cleaner-test/test1.log")).buffered().writeString("update modify date")

        val cleaner = FixedSurvivalTimeCleaner("/tmp/log/cleaner-test", 1000, 100)
        runBlocking {
//            cleaner.clean()
//            readDir("/tmp/log/cleaner-test")?.size shouldBe 1
//            delay(1500)
//            cleaner.clean()
//            readDir("/tmp/log/cleaner-test")?.size shouldBe 0
        }
    }
})
