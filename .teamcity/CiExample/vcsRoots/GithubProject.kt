package CiExample.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2017_2.*
import jetbrains.buildServer.configs.kotlin.v2017_2.vcs.GitVcsRoot

object GithubProject : GitVcsRoot({
    uuid = "067647b5-6318-4345-ab3c-08f6f4a3f0c9"
    id = "GithubProject"
    name = "github project"
    pollInterval = 10
    url = "https://github.com/mdsjip/ci-example"
    agentCleanPolicy = GitVcsRoot.AgentCleanPolicy.ALWAYS
    authMethod = password {
        userName = "mdsjip"
        password = "credentialsJSON:63ec5b70-6898-464c-9dc4-57b26cb36a49"
    }
})
