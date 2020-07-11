package Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1_2.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1_2_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster : GitVcsRoot({
    uuid = "8fbf17f5-1746-4c31-8eea-7322573e8ca4"
    name = "https://github.com/tcqa-repos/KotlinSettings"
    url = "https://github.com/tcqa-repos/KotlinSettings"
    branch = "refs/heads/TW-66783-2"
    authMethod = password {
        userName = "tcqa-repos"
        password = "credentialsJSON:f450f0e3-c536-4090-89d4-1928a49c4a6b"
    }
})
