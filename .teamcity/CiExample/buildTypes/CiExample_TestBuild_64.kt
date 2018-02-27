package CiExample.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

object CiExample_TestBuild_64 : BuildType({
    uuid = "108e40f1-6bd8-44f2-adee-bd0cf76ea5ae-64"
    id = "CiExample_TestBuild_64"
    name = "test build with 64 Mb of JVM memory"

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
            jvmArgs = "-Xmx64m"
        }
    }

    triggers {
        vcs {
            branchFilter = "+:master"
        }
    }
})
