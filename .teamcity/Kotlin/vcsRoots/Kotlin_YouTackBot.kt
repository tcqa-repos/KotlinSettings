package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_YouTackBot : GitVcsRoot({
    uuid = "793bfafb-9fac-46f8-abf2-00ab47ce1d92"
    extId = "Kotlin_YouTackBot"
    name = "YouTackBot"
    url = "https://github.com/erokhins/YouTrackBot.git"
})
