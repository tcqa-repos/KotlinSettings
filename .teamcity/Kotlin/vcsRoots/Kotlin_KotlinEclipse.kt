package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinEclipse : GitVcsRoot({
    uuid = "3643b934-19bf-497c-ae00-44ee4d35e671"
    extId = "Kotlin_KotlinEclipse"
    name = "Kotlin Eclipse"
    url = "https://github.com/JetBrains/kotlin-eclipse.git"
    branchSpec = """
        +:refs/heads/(rr/*)
        +:refs/heads/(kotlin-1.0.x)
    """.trimIndent()
    serverSideAutoCRLF = true
    useMirrors = false
    authMethod = password {
        userName = "goodwinnk"
        password = "zxxe628e1fb896d7285821aabdc3c3bf812bf358d36ac3fdd2c"
    }
})
