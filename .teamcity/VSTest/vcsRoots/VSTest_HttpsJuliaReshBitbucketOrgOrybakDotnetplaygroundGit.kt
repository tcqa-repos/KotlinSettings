package VSTest.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit : GitVcsRoot({
    uuid = "98b363d6-95fc-43d4-8880-06eda3b84361"
    extId = "VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit"
    name = "https://JuliaResh@bitbucket.org/orybak/dotnetplayground.git"
    url = "https://JuliaResh@bitbucket.org/orybak/dotnetplayground.git"
    authMethod = password {
        userName = "JuliaResh"
        password = "zxxc19733ffb6b0b30a4763ae7b9ecf7c2e"
    }
})
