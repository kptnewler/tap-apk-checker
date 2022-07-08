package com.taptap.apk_checker_plugin.utils

import java.io.BufferedReader
import java.io.InputStreamReader

object CmdExecutor {
    fun execCmd(cmd: String): Int {
        val runtime = Runtime.getRuntime()
        try {
            val process = runtime.exec(cmd)
            val inputStream = InputStreamReader(process.inputStream)
            val errorStream = BufferedReader(InputStreamReader(process.errorStream))
            inputStream.useLines {
                it.forEach { line -> println(line) }
            }
            errorStream.useLines {
                it.forEach { line -> println(line) }
            }
            return process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return -1
    }
}