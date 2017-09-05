package Kotlin_11_M1.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_11_M1_KotlinGithubMaster : GitVcsRoot({
    uuid = "3e43c497-a2b1-4f00-b49f-f05e397c40c9"
    extId = "Kotlin_11_M1_KotlinGithubMaster"
    name = "Kotlin @ github:releases"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "%release.tag.branch.name%"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(idea13)
        +:refs/heads/(rr/*)
        +:refs/heads/(release/*)
        +:refs/heads/(%release.tag.branch.name%)
        +:refs/heads/(%release.tag.branch.name%_/*)
        +:(refs/tags/build-*)
    """.trimIndent()
    useTagsAsBranches = true
    authMethod = password {
        userName = "KotlinBuild"
        password = "credentialsJSON:626c5172-50ba-46ca-a33f-97ae69170e2f"
    }
})
