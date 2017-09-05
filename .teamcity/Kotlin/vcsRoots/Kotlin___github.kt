package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin___github : GitVcsRoot({
    uuid = "18590885-bb92-4189-8789-689465b9e177"
    extId = "Kotlin___github"
    name = "Kotlin @ github - deprecated (use main instead)"
    url = "https://github.com/JetBrains/kotlin.git"
    branch = "master"
    branchSpec = """
        +:refs/heads/(master)
        +:refs/heads/(idea12)
        +:refs/heads/(idea13)
        +:refs/heads/(idea13_M8)
        +:refs/heads/(idea14_M8)
        +:refs/heads/(idea-13-0)
        +:refs/heads/(idea14)
        +:refs/heads/(android_studio)
        +:refs/heads/(M15)
        +:refs/heads/(beta1)
        +:refs/heads/(beta2)
        +:refs/heads/(beta3)
        +:refs/heads/(beta4)
        +:refs/heads/(beta5)
        +:refs/heads/(rc)
        +:refs/heads/(1.0.1)
        +:refs/heads/(1.0.2)
        +:refs/heads/(1.0.3)
        +:refs/heads/(1.0.4)
        +:refs/heads/(1.0.5)
        +:refs/heads/(1.0.6)
        +:refs/heads/(1.1-M1)
        +:refs/heads/(1.1-M03)
        +:refs/heads/(1.1-Beta)
        +:refs/heads/(M15_internal)
        +:refs/heads/(packages)
        +:refs/heads/(remote-run/*)
        +:refs/heads/(rr/*)
    """.trimIndent()
    userForTags = "KotlinBuild <kotlin-build@jetbrains.com>"
    useMirrors = false
    authMethod = password {
        userName = "KotlinBuild"
        password = "zxxaf209c17bce60b19383e081cf26140d6"
    }
})
