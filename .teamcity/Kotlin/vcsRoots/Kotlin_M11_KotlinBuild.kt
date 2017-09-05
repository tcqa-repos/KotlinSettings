package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_M11_KotlinBuild : GitVcsRoot({
    uuid = "7d736d63-e1a1-42f7-9f46-3627fbbad473"
    extId = "Kotlin_M11_KotlinBuild"
    name = "Kotlin @ github:KotlinBuild"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "refs/heads/%release.branch.prefix%%release.branch.idea.suffix%"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(%release.branch.prefix%%release.branch.idea.suffix%/*)
        +:refs/heads/(%release.branch.prefix%%release.branch.idea.suffix%)
        +:refs/heads/(%release.branch.prefix%%release.branch.idea.suffix%rr/*)
    """.trimIndent()
    authMethod = password {
        userName = "KotlinBuild"
        password = "zxxaf209c17bce60b19383e081cf26140d6"
    }
})
