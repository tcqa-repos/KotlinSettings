package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinGithubKotlinBuild1 : GitVcsRoot({
    uuid = "4efd0c00-926f-49b0-8f2b-37f0822d3f80"
    extId = "Kotlin_KotlinGithubKotlinBuild1"
    name = "Kotlin @ github:KotlinBuild (stdlib docs)"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "1.0.5"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(%release.branch.prefix%%release.branch.idea.suffix%/*)
        +:refs/heads/(%release.branch.prefix%%release.branch.idea.suffix%)
        +:refs/heads/(build-docs/1.0.6)
    """.trimIndent()
    authMethod = password {
        userName = "yole"
        password = "zxx4bf891eaf2acfa7416fa0f5a3ef609f8"
    }
})
