plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "pro.yakuraion.plugins.codecheck"
version = "1.0-SNAPSHOT"

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("pro.yakuraion.plugins.utils:utils:1.0-SNAPSHOT")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.22.0")
}
