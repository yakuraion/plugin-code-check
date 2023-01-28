pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "code-check"

includeBuild("plugin-utils") {
    dependencySubstitution {
        substitute(module("pro.yakuraion.plugins.utils:utils")).using(project(":"))
    }
}
