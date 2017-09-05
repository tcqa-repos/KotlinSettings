package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object Vcs_Kotlin_ts2kt : GitVcsRoot({
    uuid = "9a815aae-e8c0-4ae2-a4ba-432e39fd927b"
    extId = "Kotlin_ts2kt"
    name = "ts2kt"
    url = "https://github.com/bashor/ts2kt.git"
    branchSpec = "+:refs/heads/*"
    useMirrors = false
})
