package CiExample.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

class CiExample_TestBuildWithJvmMemory(memory: Int) : BuildType({
    uuid = "108e40f1-6bd8-44f2-adee-bd0cf76ea5ae-$memory"
    id = "CiExample_TestBuild_$memory"
    name = "test build with $memory Mb of JVM memory"

    vcs { root(CiExample.vcsRoots.GithubProject) }
    steps {
        script {
            scriptContent = "echo 'test'"
            dockerImage = "bash"
        }
        gradle {
            jvmArgs = "-Xmx${memory}m"
            tasks = "clean build"
            buildFile = "build.gradle.kts"
            useGradleWrapper = true
        }
    }
    triggers {
        vcs {
            branchFilter = "+:master"
        }
    }
})
