package pro.yakuraion.plugins.codecheck

import io.gitlab.arturbosch.detekt.Detekt
import pro.yakuraion.plugins.utils.openFileByOS

@Suppress("PropertyName")
val OPEN_DETEKT_REPORT_TASK_NAME = "openDetektHtmlReportInBrowser"

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val codeCheckExtensions = project.extensions.create<CodeCheckPluginExtension>(CodeCheckPluginExtension.NAME)

detekt {
    buildUponDefaultConfig = true
    config = codeCheckExtensions.detektConfig
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.20.0")
}

tasks.withType(Detekt::class.java).configureEach {
    jvmTarget = codeCheckExtensions.jvmTarget.get()

    reports.html.outputLocation.set(reporting.file("detekt.html"))

    finalizedBy(tasks.named(OPEN_DETEKT_REPORT_TASK_NAME))
}

tasks.register(OPEN_DETEKT_REPORT_TASK_NAME) {
    val detektTask = tasks.withType(Detekt::class.java).first()
    onlyIf {
        @Suppress("SENSELESS_COMPARISON")
        detektTask.state.failure != null
    }
    doLast {
        val report = detektTask.reports.html.outputLocation.asFile.get()
        openFileByOS(report)
    }
}
