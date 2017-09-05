package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinGithubForGradleIntegrationTests : GitVcsRoot({
    uuid = "6253d79c-bb36-45f1-83ce-02165eb0e3c5"
    extId = "Kotlin_KotlinGithubForGradleIntegrationTests"
    name = "Kotlin @ github for Gradle Integration Tests"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "master"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(1.0.*)
        +:refs/heads/(rr/gradle/*)
    """.trimIndent()
    userForTags = "KotlinBuild <kotlin-build@jetbrains.com>"
    useMirrors = false
    authMethod = password {
        userName = "KotlinBuild"
        password = "zxxaf209c17bce60b19383e081cf26140d6"
    }
})
