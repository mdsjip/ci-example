package CiExample

import CiExample.buildTypes.*
import CiExample.vcsRoots.*
import CiExample.vcsRoots.GithubProject
import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.Project
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures.versionedSettings

object Project : Project({
    uuid = "f94db678-d1de-47dc-b005-7d6e9a8cd46d"
    id = "CiExample"
    parentId = "_Root"
    name = "ci-example"

    vcsRoot(GithubProject)

    buildType(CiExample_TestBuild_64)
    buildType(CiExample_TestBuild_32)
    buildType(CiExample_TestBuild_128)
    buildType(CiExample_TestBuild_256)
    buildType(CiExample_TestBuild_16)

    features {
        versionedSettings {
            id = "PROJECT_EXT_1"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.PREFER_SETTINGS_FROM_VCS
            rootExtId = GithubProject.id
            showChanges = true
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
