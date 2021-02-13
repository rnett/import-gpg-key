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


//    implementation("com.github.rnett.ktjs-github-action:kotlin-js-action:0.1.0-SNAPSHOT")
}

kotlin {
    js(IR) {
        nodejs {
            binaries.executable()
        }
    }
}


//TODO distribution isn't working
tasks.create<org.gradle.api.tasks.Copy>("buildIndex"){
    group = "build"
    dependsOn("compileProductionExecutableKotlinJs")
    destinationDir = file("$projectDir/dist/")
    from("build/compileSync/kotlin/import-gpg-key.js")
    rename("import-gpg-key.js", "index.js")
}
