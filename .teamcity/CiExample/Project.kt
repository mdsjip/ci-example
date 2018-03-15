package CiExample

import CiExample.buildTypes.GradleBuild
import CiExample.vcsRoots.GithubProject
import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.ReuseBuilds
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

fun Project.pipeline(init: Pipeline.() -> Unit = {}) {
    val pipeline = Pipeline()
    pipeline.init()
    pipeline.phases.forEach { it.buildTypes.forEach { bt -> this.buildType(bt) } }
}

object Project : Project({
    uuid = "f94db678-d1de-47dc-b005-7d6e9a8cd46d"
    id = "CiExample"
    parentId = "_Root"
    name = "ci-example"
    vcsRoot(GithubProject)
    pipeline {
        phase("01 [Clean stuff]") {
            +GradleBuild("clean")
        }
        phase("02 [Compile]") {
            +GradleBuild("compileKotlin")
        }
        phase("03 [Code checks]") {
            +GradleBuild("detektCheck")
            +GradleBuild("lintKotlin")
        }
        phase("04 [Unit tests]") {
            +GradleBuild("junitPlatformTest")
        }
    }

    features {
        versionedSettings {
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = GithubProject.id
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})


class Phase {
    val buildTypes = hashSetOf<BuildType>()
    operator fun BuildType.unaryPlus() {
        buildTypes.add(this)
    }
}

class Pipeline {
    val phases = arrayListOf<Phase>()
    fun phase(phaseName: String = "", init: Phase.() -> Unit = {}) {
        val newPhase = Phase()
        newPhase.init()
        newPhase.buildTypes.forEach { it.name = "$phaseName ${it.name}" }
        phases.lastOrNull()?.let { prevPhase ->
            newPhase.buildTypes.forEach {
                it.dependencies {
                    for (dependency in prevPhase.buildTypes) {
                        snapshot(dependency) {
                            reuseBuilds = ReuseBuilds.SUCCESSFUL
                        }
                    }
                }
            }
        }
        phases.add(newPhase)
    }
}

