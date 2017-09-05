package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object kotlin_examples___GitHub : GitVcsRoot({
    uuid = "04ba28aa-9aee-4d2b-b709-bb4a92b437a8"
    extId = "kotlin_examples___GitHub"
    name = "kotlin-examples @ GitHub"
    url = "git://github.com/JetBrains/kotlin-examples.git"
    branch = "master"
    useMirrors = false
})
