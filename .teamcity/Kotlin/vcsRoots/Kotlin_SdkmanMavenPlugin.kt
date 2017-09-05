package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Kotlin_SdkmanMavenPlugin : GitVcsRoot({
    uuid = "0a3427e3-e34d-461e-8ff8-9937072a7cdb"
    extId = "Kotlin_SdkmanMavenPlugin"
    name = "sdkman maven plugin"
    pollInterval = 12000
    url = "https://github.com/sdkman/sdkman-maven-plugin.git"
})
