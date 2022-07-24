package com.taptap.apk_checker_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask
import com.google.gson.Gson
import com.jaredrummler.ktsh.Shell
import com.taptap.apk_checker_plugin.data.ApkCheckerConfig
import com.taptap.apk_checker_plugin.data.ApkCheckerExt
import com.taptap.apk_checker_plugin.utils.CmdExecutor
import com.taptap.glog.core.GLog
import com.taptap.glog.core.LogLevel
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileWriter

abstract class ApkCheckTask: DefaultTask() {
    @get:Input
    abstract var apkCheckerExt: Property<ApkCheckerExt>

    @get:OutputFile
    abstract var apkCheckerOutFile: File

    private var apkName: String = ""

    private var rDotTextPath = ""

    private val gson by lazy {
        Gson().newBuilder().create()
    }

    private val apkCheckerConfig = ApkCheckerConfig()

    @TaskAction
    fun run() {
        // 如果没有指定变体下的 apk 则不执行
        if (apkCheckerExt.get().checkVariant.isEmpty()) {
            GLog.printLog(LogLevel.ERROR, "checkVariant is empty")
            return
        }

        getResourceLocalPath(project, apkCheckerExt.get().checkVariant)

//        apkCheck()
    }

    private fun apkCheck() {
        // 如果 apk 不存在则不执行
        if (!File(apkCheckerConfig.apk).exists()) {
            GLog.printLog(LogLevel.ERROR, "apk is not found")
            return
        }

        //判断是否有java8的环境
        val java8 = File("/usr/lib/jvm/java-8-openjdk-amd64/bin")
        if (!java8.exists()) {
            return
        }

        apkCheckerConfig.output = File(project.rootProject.buildDir, "${apkName}_apk_checker_result").name

        if (doApkCheckCmd(createConfigFile()).isSuccess) {
            uploadResult()
        }

    }

    private fun createConfigFile(): String {
        val configJson = gson.toJson(apkCheckerConfig)
        val configFile = File(project.rootProject.buildDir, "apk_checker_config.json")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
        val fileWriter = FileWriter(configFile)
        fileWriter.use {
            it.write(configJson)
        }
        return configFile.absolutePath
    }

    private fun uploadResult(): String {
        // TODO 上传生成的 json 到服务器
        return ""
    }

    /*
    * 用于获取 apkChecker 配置路径
    */
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
                        GLog.printLog(LogLevel.INFO, baseVariantOutput.outputFile.absolutePath)
                        apkCheckerConfig.apk = baseVariantOutput.outputFile.absolutePath
                        apkName = baseVariantOutput.outputFile.name
                        val processResourceTask = baseVariantOutput.processResourcesProvider.get()
                        if (processResourceTask is LinkApplicationAndroidResourcesTask) {
                            rDotTextPath = processResourceTask.getTextSymbolOutputFile()?.absolutePath ?: ""
                        }
                    }
                }
        }
    }

    private fun doApkCheckCmd(configJsonPath: String): Shell.Command.Result {
        val apkCheckerJarPath = ""
        val cmd = "java -jar $apkCheckerJarPath --$configJsonPath"
        return CmdExecutor.execCmd(cmd)
    }
}