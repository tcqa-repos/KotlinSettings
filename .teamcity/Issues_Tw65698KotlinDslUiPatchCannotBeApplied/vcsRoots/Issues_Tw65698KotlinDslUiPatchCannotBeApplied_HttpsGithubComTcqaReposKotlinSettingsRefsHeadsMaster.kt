package Issues_Tw65698KotlinDslUiPatchCannotBeApplied.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Issues_Tw65698KotlinDslUiPatchCannotBeApplied_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster : GitVcsRoot({
    uuid = "8164d351-5d10-4a26-831b-fe01b13b6e73"
    name = "https://github.com/tcqa-repos/KotlinSettings#refs/heads/TW-65698"
    url = "https://github.com/tcqa-repos/KotlinSettings"
    branch = "refs/heads/TW-65698"
    authMethod = password {
        userName = "tcqa-repos"
        password = "credentialsJSON:f450f0e3-c536-4090-89d4-1928a49c4a6b"
    }
})
