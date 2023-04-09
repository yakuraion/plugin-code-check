package pro.yakuraion.plugins.codecheck

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import pro.yakuraion.plugins.codecheck.configurators.DetektConfigurator

class CodeCheckPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val extension = target.createExtension()
        DetektConfigurator(target, extension).configure()
    }

    private fun Project.createExtension(): CodeCheckPluginExtension {
        return extensions.create<CodeCheckPluginExtension>(EXTENSION_NAME).apply {
            detekt.enabled.convention(true)
        }
    }

    companion object {

        private const val EXTENSION_NAME = "codeCheck"
    }
}
