package CiExample.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

object CiExample_TestBuild_128 : BuildType({
    uuid = "108e40f1-6bd8-44f2-adee-bd0cf76ea5ae-128"
    id = "CiExample_TestBuild_128"
    name = "test build with 128 Mb of JVM memory"

    vcs {
        root(CiExample.vcsRoots.GithubProject)

    }

    steps {
        script {
            scriptContent = "echo 'test'"
            dockerImage = "bash"
        }
        gradle {
            tasks = "clean build"
            buildFile = "build.gradle.kts"
            useGradleWrapper = true
            jvmArgs = "-Xmx128m"
        }
    }

    triggers {
        vcs {
            branchFilter = "+:master"
        }
    }
})
