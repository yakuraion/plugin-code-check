package pro.yakuraion.plugins.codecheck

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested

interface CodeCheckPluginExtension {

    interface DetektConfiguration {

        val enabled: Property<Boolean>

        val config: ConfigurableFileCollection
    }

    val jvmTarget: Property<String>

    @get:Nested
    val detekt: DetektConfiguration

    fun detekt(action: DetektConfiguration.() -> Unit) {
        action.invoke(detekt)
    }
}
