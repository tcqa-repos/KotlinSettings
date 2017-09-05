package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_KotlinNetbeans : GitVcsRoot({
    uuid = "7513feed-2694-4d72-9178-a2c70e831bf7"
    extId = "Kotlin_KotlinNetbeans"
    name = "kotlin-netbeans"
    url = "https://github.com/Baratynskiy/kotlin-netbeans"
})
