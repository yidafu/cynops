package dev.yidafu.cynops.appender

import dev.yidafu.cynops.Level
import dev.yidafu.cynops.LogEvent
import dev.yidafu.cynops.Logger
import dev.yidafu.cynops.LoggerContext
import dev.yidafu.cynops.config.CynopsConfig
import dev.yidafu.cynops.io.fileInfo
import dev.yidafu.cynops.io.readDir
import io.kotest.common.runBlocking
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

class FileAppenderTest : FunSpec({
    val targetDir = Path("/tmp/log/test-file-appender-${Clock.System.now().toEpochMilliseconds()}")

    beforeEach {
        SystemFileSystem.createDirectories(targetDir)
    }

    test("file appender with auto cleaner") {
        val config = CynopsConfig();
        config.fileAppender {
            logDir = targetDir.toString()
            maxSurvivalTime = 1000
            cleanerCheckInterval = 500
        }
        val appender = FileAppender(config).apply {
            context = LoggerContext(config)
        }
        // before start append log dir should be empty
        readDir(targetDir.toString())?.size shouldBe 0
        appender.onStart()
        // trigger buffered flush
        repeat(30) {
            appender.doAppend(
                LogEvent(
                    "1693232661802L",
                    "Suppressed: kotlinx.coroutines.internal.DiagnosticCoroutineContextException: [CoroutineId(2), \"coroutine#2\":StandaloneCoroutine{Cancelling}@72ebba7b, Dispatchers.IO]",
                    Level.Info,
                    mapOf(
                        "key" to "value", "key2" to "value2",
                        LogEvent.TAG_TOPIC to "topic",
                        LogEvent.TAG_HOSTNAME to "local-hostname",
                        LogEvent.TAG_PID to "1234",
                        LogEvent.TAG_ENV to "dev",
                        LogEvent.TAG_LOGGER_NAME to "TestTag"
                    ),
                ),
            )
        }
        // await emit log event to appender
        delay(100)
        readDir(targetDir.toString())?.size shouldBe 1

        // await cleaner trigger clean
        delay(2000)
        appender.onStop()
        delay(1000)
        // log file should have been clean
        readDir(targetDir.toString())?.size shouldBe 0

    }
})
