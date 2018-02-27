package CiExample.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

object CiExample_TestBuild : BuildType({
    uuid = "108e40f1-6bd8-44f2-adee-bd0cf76ea5ae"
    id = "CiExample_TestBuild"
    name = "test build"

    vcs {
        root(CiExample.vcsRoots.GithubProject)
    }

    steps {
        gradle {
            tasks = "clean build"
            buildFile = "build.gradle.kts"
            useGradleWrapper = true
        }
    }

    triggers {
        vcs {
        }
    }
})
