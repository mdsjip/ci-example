package CiExample.buildTypes

import jetbrains.buildServer.configs.kotlin.v2017_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2017_2.triggers.vcs

class GradleBuild(gradleTask: String) : BuildType({
    uuid = "3700d260-2fe5-4850-9674-4a46eb1eb8a7-$gradleTask"
    id = "GradleBuild_$gradleTask"
    name = gradleTask

    vcs { root(CiExample.vcsRoots.GithubProject) }
    steps {
        script {
            scriptContent = "echo 'running build with Gradle task: $gradleTask'"
        }
        gradle {
            tasks = gradleTask
            buildFile = "build.gradle.kts"
            useGradleWrapper = true
        }
    }
    triggers {
        vcs {
        }
    }
})
