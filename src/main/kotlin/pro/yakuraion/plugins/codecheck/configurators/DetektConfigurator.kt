package pro.yakuraion.plugins.codecheck.configurators

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import pro.yakuraion.plugins.codecheck.CodeCheckPluginExtension
import pro.yakuraion.plugins.codecheck.OpenReportTask

class DetektConfigurator(
    private val project: Project,
    private val extension: CodeCheckPluginExtension,
) {

    fun configure() {
        applyDetekt()
        configureDetektExtension()
        addDetektFormatting()
        configureDetektTask()
        registerOpenReportTask()
    }

    private fun applyDetekt() {
        project.pluginManager.apply(DetektPlugin::class.java)
    }

    private fun configureDetektExtension() {
        project.extensions.configure<DetektExtension> {
            buildUponDefaultConfig = false
            config = extension.detekt.config
        }
    }

    private fun addDetektFormatting() {
        project.dependencies.add(
            "detektPlugins",
            "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_VERSION"
        )
    }

    private fun configureDetektTask() {
        project.tasks.withType<Detekt>().configureEach {
            onlyIf { extension.detekt.enabled.get() }
            jvmTarget = extension.jvmTarget.get()
            val outputFile = project.extensions.getByType<ReportingExtension>().file(DETEKT_REPORT_FILE)
            reports.html.outputLocation.set(outputFile)
            finalizedBy(project.tasks.named(OPEN_REPORT_TASK))
        }
    }

    private fun registerOpenReportTask() {
        project.tasks.register<OpenReportTask>(OPEN_REPORT_TASK) {
            val detektTask = project.tasks.withType<Detekt>().first()
            onlyIf { extension.openFailedReport.get() && detektTask.state.failure != null }
            report.set(detektTask.htmlReportFile)
        }
    }

    companion object {

        private const val DETEKT_VERSION = "1.22.0"

        private const val DETEKT_REPORT_FILE = "detekt.html"

        private const val OPEN_REPORT_TASK = "openDetektReport"
    }
}
