plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "pro.yakuraion.plugins.codecheck"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("codeCheck") {
            id = "pro.yakuraion.plugins.code-check"
            implementationClass = "pro.yakuraion.plugins.codecheck.CodeCheckPlugin"
        }
    }
}

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
