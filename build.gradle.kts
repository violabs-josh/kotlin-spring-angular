import com.github.gradle.node.npm.task.NpxTask

plugins {
    kotlin("multiplatform") version "1.9.0"
    application
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("plugin.spring") version "1.8.22"
    id("com.github.node-gradle.node") version "3.5.+"
}

group = "io.violabs"
version = "1.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

val ngBuild = tasks.register<NpxTask>("buildWebapp") {
    command.set("ng")
    args.set(listOf("build", "--configuration=production"))
    dependsOn(tasks.npmInstall)
    inputs.dir(project.fileTree("src/jsMain").exclude("**/*.spec.ts"))
    inputs.dir("node_modules")
    inputs.files("angular.json", ".browserslistrc", "tsconfig.json", "tsconfig.app.json")
    outputs.dir("${project.buildDir}/install/webapp")
}

val ngTest = tasks.register<NpxTask>("testWebapp") {
    command.set("ng")
    args.set(listOf("test", "--watch=false"))
    dependsOn(tasks.npmInstall)
    inputs.dir("src/jsTest")
    inputs.dir("node_modules")
    inputs.files("angular.json", ".browserslistrc", "tsconfig.json", "tsconfig.spec.json", "karma.conf.js")
    outputs.upToDateWhen { true }
}

kotlin {
    sourceSets.all {
        languageSettings.apply {
            optIn("kotlin.js.ExperimentalJsExport")
        }
    }

    jvm {
        jvmToolchain(17)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
//                dependsOn(ngTest)
            }
        }
    }
    js(IR) {
        binaries.executable()
        browser {}
        generateTypeScriptDefinitions()
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-webflux")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
                implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
                implementation("org.jetbrains.kotlin:kotlin-reflect")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
            }

            tasks.named("processResources") {
                dependsOn(ngBuild)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.springframework.boot:spring-boot-starter-test")
                implementation("io.projectreactor:reactor-test")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("io.violabs.application.KotlinSpringAngularApplicationKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}