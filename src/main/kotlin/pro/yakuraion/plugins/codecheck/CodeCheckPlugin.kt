package pro.yakuraion.plugins.codecheck

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reporting.ReportingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import pro.yakuraion.plugins.utils.openFileByOS

class CodeCheckPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply(DetektPlugin::class.java)

        val extension = target.extensions.create<CodeCheckPluginExtension>(EXTENSION_NAME).apply {
            detekt.enabled.convention(true)
        }

        target.extensions.configure<DetektExtension> {
            buildUponDefaultConfig = true
            config = extension.detekt.config
        }

        target.dependencies.add(
            "detektPlugins",
            "io.gitlab.arturbosch.detekt:detekt-formatting:$DETEKT_VERSION"
        )

        target.tasks.withType<Detekt>().configureEach {
            onlyIf { extension.detekt.enabled.get() }
            jvmTarget = extension.jvmTarget.get()
            val outputFile = target.extensions.getByType<ReportingExtension>().file("detekt.html")
            reports.html.outputLocation.set(outputFile)
            finalizedBy(target.tasks.named(OPEN_REPORT_TASK))
        }

        target.tasks.register(OPEN_REPORT_TASK) {
            val detektTask = target.tasks.withType<Detekt>().first()
            onlyIf { detektTask.state.failure != null }
            doLast {
                val report = detektTask.reports.html.outputLocation.asFile.get()
                target.openFileByOS(report)
            }
        }
    }

    companion object {

        private const val DETEKT_VERSION = "1.22.0"

        private const val EXTENSION_NAME = "codeCheck"

        private const val OPEN_REPORT_TASK = "openCodeCheckReport"
    }
}
