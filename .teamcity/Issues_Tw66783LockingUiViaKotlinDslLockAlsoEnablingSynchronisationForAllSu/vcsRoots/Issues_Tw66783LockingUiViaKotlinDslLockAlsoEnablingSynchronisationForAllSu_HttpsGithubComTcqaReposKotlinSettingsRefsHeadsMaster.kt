package Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster : GitVcsRoot({
    uuid = "5e34c364-07bf-4920-b5e0-b47bf86fe9d5"
    name = "https://github.com/tcqa-repos/KotlinSettings#refs/heads/master"
    url = "https://github.com/tcqa-repos/KotlinSettings"
    branch = "refs/heads/TW-66783"
    authMethod = password {
        userName = "tcqa-repos"
        password = "credentialsJSON:f450f0e3-c536-4090-89d4-1928a49c4a6b"
    }
})
