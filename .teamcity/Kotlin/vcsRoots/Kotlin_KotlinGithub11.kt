package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinGithub11 : GitVcsRoot({
    uuid = "6890e171-59f6-4582-8be6-e46680b0f418"
    extId = "Kotlin_KotlinGithub11"
    name = "Kotlin @ github Android"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "master"
    branchSpec = "+:refs/heads/(rr/android)"
    userForTags = "KotlinBuild <kotlin-build@jetbrains.com>"
    useMirrors = false
    authMethod = password {
        userName = "KotlinBuild"
        password = "credentialsJSON:626c5172-50ba-46ca-a33f-97ae69170e2f"
    }
})
