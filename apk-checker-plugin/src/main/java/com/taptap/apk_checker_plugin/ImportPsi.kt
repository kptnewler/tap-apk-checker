package com.taptap.apk_checker_plugin.utils

import com.intellij.openapi.util.Disposer
import com.intellij.openapi.vfs.StandardFileSystems
import com.intellij.psi.PsiManager
import com.taptap.apk_checker_plugin.UastEnvironment
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.MessageRenderer
import org.jetbrains.kotlin.cli.common.messages.PrintingMessageCollector
import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UastFacade
import java.io.File

@CacheableTask
abstract class ImportPsi: DefaultTask() {

    @TaskAction
    fun parse() {
        val disposable = Disposer.newDisposable()

        val env = UastEnvironment.create(UastEnvironment.Configuration.create())

        val project = env.ideaProject

        val virtualFile = StandardFileSystems.local().findFileByPath("/Users/newler/IdeaProjects/tap-apk-checker/simple/src/main/java/com/taptap/appchecker/MainActivity.kt") ?: return

        val psiFile = PsiManager.getInstance(project).findFile(virtualFile) ?: return
        println(psiFile.toString())

        val uFile = UastFacade.convertElementWithParent(psiFile, UFile::class.java) as? UFile ?: return

        uFile.imports.forEach {
            println(it.toString())
        }
    }

    private fun createConfiguration(): CompilerConfiguration {
        val configuration = CompilerConfiguration()
        configuration.put(
            CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY,
            PrintingMessageCollector(
                System.err,
                MessageRenderer.PLAIN_FULL_PATHS,
                false
            )
        )
        return configuration
    }
}