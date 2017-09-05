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
        password = "credentialsJSON:dbf840aa-4d88-455c-a597-db5b553eb11f"
    }
})
