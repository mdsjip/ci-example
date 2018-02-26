package CiExample

import CiExample.buildTypes.CiExample_TestBuildWithJvmMemory
import CiExample.vcsRoots.GithubProject
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "f94db678-d1de-47dc-b005-7d6e9a8cd46d"
    id = "CiExample"
    parentId = "_Root"
    name = "ci-example"

    vcsRoot(GithubProject)
    generateSequence(16) { it * 2 }.take(5).forEach {
        buildType(CiExample_TestBuildWithJvmMemory(it))
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
