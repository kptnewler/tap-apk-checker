package com.taptap.apk_checker_plugin.utils

import com.jaredrummler.ktsh.Shell
import com.taptap.glog.core.GLog
import com.taptap.glog.core.LogLevel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

object CmdExecutor {
    private val shell = Shell.SH

    fun execCmd(cmd: String): Shell.Command.Result {
        val result = shell.run(cmd) {
            timeout = Shell.Timeout(1, TimeUnit.MINUTES)

            redirectErrorStream = false

            onCancelled = {
                GLog.printLog(LogLevel.FAILURE, "apk checker cancel")
            }

            onStdOut = {
                GLog.printLog(LogLevel.INFO, it)
            }

            onStdErr = {
                GLog.printLog(LogLevel.ERROR, it)
            }
        }
        return result
    }

    fun close() = shell.shutdown()
}