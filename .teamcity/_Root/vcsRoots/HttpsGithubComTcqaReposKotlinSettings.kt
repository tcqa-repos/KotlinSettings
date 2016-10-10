package _Root.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object HttpsGithubComTcqaReposKotlinSettings : GitVcsRoot({
    uuid = "c84c2fda-4c4e-4554-97df-9cdacd402775"
    extId = "HttpsGithubComTcqaReposKotlinSettings"
    name = "https://github.com/tcqa-repos/KotlinSettings"
    url = "https://github.com/tcqa-repos/KotlinSettings"
    authMethod = password {
        userName = "tcqa-repos"
        password = "zxxb4813732520f9587c2fb27cb30cc3e32"
    }
})
