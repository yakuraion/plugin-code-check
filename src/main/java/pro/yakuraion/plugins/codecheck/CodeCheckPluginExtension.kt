package pro.yakuraion.plugins.codecheck

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property

@Suppress("LeakingThis")
abstract class CodeCheckPluginExtension {

    abstract val detektConfig: ConfigurableFileCollection

    abstract val jvmTarget: Property<String>

    init {
        jvmTarget.convention("11")
    }

    companion object {
        const val NAME = "codeCheck"
    }
}
