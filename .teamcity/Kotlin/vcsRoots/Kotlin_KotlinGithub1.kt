package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinGithub1 : GitVcsRoot({
    uuid = "21582784-e4ac-447c-ad92-afe4901eda1f"
    extId = "Kotlin_KotlinGithub1"
    name = "Kotlin @ github - master, rr/gradle/*"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "master"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(rr/gradle/*)
    """.trimIndent()
    userForTags = "KotlinBuild <kotlin-build@jetbrains.com>"
    useMirrors = false
    authMethod = password {
        userName = "KotlinBuild"
        password = "zxxaf209c17bce60b19383e081cf26140d6"
    }
})
