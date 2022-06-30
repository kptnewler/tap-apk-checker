package com.taptap.apk_checker_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask
import com.google.gson.Gson
import com.taptap.apk_checker_plugin.data.ApkCheckerConfig
import com.taptap.apk_checker_plugin.data.ApkCheckerExt
import com.taptap.glog.core.GLog
import com.taptap.glog.core.LogLevel
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.*

class ApkCheckTask: DefaultTask() {

    @Input
    val apkCheckerExt: ApkCheckerExt? = null

    @Input
    val apkName: String = ""

    @OutputFile
    var apkCheckerOutFile: File? = null

    private var rDotTextPath = ""

    private var gson = Gson().newBuilder().create()

    private val apkCheckerConfig = ApkCheckerConfig()

    @TaskAction
    fun run() {
        // 如果没有指定变体下的 apk 则不执行
        if (apkCheckerExt?.checkVariant.isNullOrEmpty()) {
            GLog.printLog(LogLevel.ERROR, "checkVariant is empty")
            return
        }

        getResourceLocalPath(project, apkCheckerExt?.checkVariant!!)
    }

    private fun executeAppCheck() {
        // 如果 apk 不存在则不执行
        if (!File(apkCheckerConfig.apk).exists()) {
            GLog.printLog(LogLevel.ERROR, "apk is not found")
            return
        }

        //判断是否有java8的环境
        val java8 = File("/usr/lib/jvm/java-8-openjdk-amd64/bin")
        if (java8.exists()) {
            return
        }

        apkCheckerConfig.output = File(project.buildDir, "${apkName}_apk_checker_result").name
    }

    private fun createConfigFile(): String {
        val configJson = gson.toJson(apkCheckerConfig)
        val configFile = File(project.buildDir, "apk_checker_config.json")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
        val fileWriter = FileWriter(configFile)
        fileWriter.use {
            it.write(configJson)
        }
        return configFile.absolutePath
    }

    private fun uploadResult() {

    }

    private fun getResourceLocalPath(target: Project, checkVariant: String) {
        val appExtension = target.extensions.getByName("android") as? AppExtension
        appExtension?.let { appExt->
            appExt.applicationVariants
                .filter { it.name == checkVariant }
                .flatMap {
                    apkCheckerConfig.mapping = it.mappingFile.absolutePath
                    it.outputs
                }
                .forEach { baseVariantOutput->
                    if (baseVariantOutput is ApkVariantOutput) {
                        apkCheckerConfig.apk = baseVariantOutput.outputFile.absolutePath
                        val processResourceTask = baseVariantOutput.processResourcesProvider.get()
                        if (processResourceTask is LinkApplicationAndroidResourcesTask) {
                            rDotTextPath = processResourceTask.getTextSymbolOutputFile()?.absolutePath ?: ""
                        }
                    }
                }
        }
    }
}