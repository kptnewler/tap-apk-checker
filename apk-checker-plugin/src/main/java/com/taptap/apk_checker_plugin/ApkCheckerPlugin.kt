package com.taptap.apk_checker_plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApkVariantOutput
import com.android.build.gradle.internal.res.LinkApplicationAndroidResourcesTask
import com.google.gson.Gson
import com.taptap.apk_checker_plugin.data.ApkCheckerExt
import com.taptap.glog.core.GLog
import com.taptap.glog.core.LogLevel
import org.gradle.api.Plugin
import org.gradle.api.Project

/*
 * 目标
 * 1、获取 mapping 文件
 * 2、获取 R.txt
 * 3、获取 apk 文件
 */
open class GreetingPluginExtension {
    val message = "Hello from GreetingPlugin"
}
class ApkCheckerPlugin: Plugin<Project> {
    val gson: Gson = Gson().newBuilder().create()
    override fun apply(project: Project) {
        GLog.initialize(project.gradle, true)
        val apkCheckerExt = project.extensions.create("apkChecker", ApkCheckerExt::class.java)

        project.afterEvaluate {
            getResourceLocalPathList(project, "demoDebug")
            val apkExtension = project.extensions.getByName("android") as? AppExtension
            if (apkCheckerExt.checkVariant.isEmpty()) {
                apkCheckerExt.checkVariant = apkExtension?.applicationVariants?.firstOrNull()?.name ?: ""
            }
        }
    }

    private fun getResourceLocalPathList(target: Project, checkVariant: String) {
        val appExtension = target.extensions.getByName("android") as? AppExtension

        appExtension?.let { appExt->
            appExt.applicationVariants
                .filter { it.name == checkVariant }
                .flatMap { it.outputs }
                .forEach { baseVariantOutput->
                    if (baseVariantOutput is ApkVariantOutput) {
                        GLog.printLog(LogLevel.INFO, baseVariantOutput.outputFile.absolutePath)
                        val processResourceTask = baseVariantOutput.processResourcesProvider.get()
                        if (processResourceTask is LinkApplicationAndroidResourcesTask) {
                            GLog.printLog(LogLevel.INFO,
                                processResourceTask.getTextSymbolOutputFile()?.absolutePath ?: "")
                            target.tasks.create("apk-checker", ApkCheckTask::class.java, appExt)
                        }
                    }
                }
        }
    }
}