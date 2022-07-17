package com.taptap.apk_checker_plugin

import com.taptap.apk_checker_plugin.data.ApkCheckerExt
import com.taptap.apk_checker_plugin.utils.ImportPsi
import com.taptap.glog.core.GLog
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

abstract class ApkCheckerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        GLog.initialize(project.gradle, true)

        project.afterEvaluate {
//            val apkCheckerExt = project.extensions.create("apkChecker", ApkCheckerExt::class.java)

//            this.tasks.create("apk_checker", ApkCheckTask::class.java, apkCheckerExt)
            this.tasks.create("scan-check", ImportPsi::class.java)
        }
    }
}
