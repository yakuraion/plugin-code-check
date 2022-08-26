plugins {
    `kotlin-dsl`
}

group = "pro.yakuraion.plugins.code-check"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("pro.yakuraion.plugins.utils:utils:1.0-SNAPSHOT")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.20.0")
}
