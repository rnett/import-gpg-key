plugins {
    kotlin("js") version "1.4.30"
}

group = "com.github.rnett.github-actions"
version = "0.1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    testImplementation(kotlin("test-js"))


    implementation("com.github.rnett.ktjs-github-action:kotlin-js-action:0.1.0-SNAPSHOT")
}

kotlin {
    js(IR) {
        useCommonJs()
        browser {
            webpackTask {
                output.globalObject = "this" // NodeJS mode
                this.outputFileName = "index.js"
                sourceMaps = false
                mode = org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.PRODUCTION
            }

            distribution{
                directory = file("$projectDir/dist/")
                name = "index.js"
            }
        }
        binaries.executable()
    }
}


