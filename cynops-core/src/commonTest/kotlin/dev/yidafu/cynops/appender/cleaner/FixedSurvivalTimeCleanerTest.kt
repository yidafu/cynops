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
        SystemFileSystem.createDirectories(Path("/tmp/log/cleaner-test2"))
    }
    test("FixedSurvivalTimeCleaner by handle") {
       SystemFileSystem.sink(Path("/tmp/log/cleaner-test/test1.log")).buffered().apply {
           writeString("update modify date")
           flush()
       }


        val cleaner = FixedSurvivalTimeCleaner("/tmp/log/cleaner-test", 1000, 3000)
        runBlocking {
            cleaner.clean()
            readDir("/tmp/log/cleaner-test")?.size shouldBe 1
            delay(1500)
            cleaner.clean()
            readDir("/tmp/log/cleaner-test")?.size shouldBe 0
        }
    }

    test("auto clean log files") {
        SystemFileSystem.sink(Path("/tmp/log/cleaner-test2/test.log")).buffered().apply {
            writeString("update modify date")
            flush()
        }
        val cleaner = FixedSurvivalTimeCleaner("/tmp/log/cleaner-test2", 1000, 3000)
        runBlocking {
            cleaner.onStart()
            readDir("/tmp/log/cleaner-test2")?.size shouldBe 1
            delay(5000)

            readDir("/tmp/log/cleaner-test2")?.size shouldBe 0
            cleaner.onStop()
        }
    }
})
