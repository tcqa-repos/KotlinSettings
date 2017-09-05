package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinGithubGoodwinnkMaster : GitVcsRoot({
    uuid = "4c90e106-cbe0-4bbd-96e9-31819b7f3e9c"
    extId = "Kotlin_KotlinGithubGoodwinnkMaster"
    name = "Kotlin @ github:goodwinnk:master"
    url = "https://github.com/JetBrains/kotlin.git"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(idea13)
        +:refs/heads/(rr/*)
        +:refs/heads/(release/*)
        +:refs/heads/(M11/*)
        +:refs/heads/(M12/*)
        +:refs/heads/(M13)
        +:(refs/tags/build-*)
    """.trimIndent()
    useTagsAsBranches = true
    authMethod = password {
        userName = "goodwinnk"
        password = "zxxe628e1fb896d7285821aabdc3c3bf812bf358d36ac3fdd2c"
    }
})
