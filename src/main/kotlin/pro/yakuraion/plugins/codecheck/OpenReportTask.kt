package pro.yakuraion.plugins.codecheck

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import pro.yakuraion.plugins.utils.openFileByOS

abstract class OpenReportTask : DefaultTask() {

    @get:InputFile
    abstract val report: RegularFileProperty

    @TaskAction
    fun openReport() {
        project.openFileByOS(report.get().asFile)
    }
}
