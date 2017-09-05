package Kotlin.vcsRoots

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.vcs.GitVcsRoot

object KAnnotator___GitHub : GitVcsRoot({
    uuid = "9d5fdb64-015d-49f9-9022-d588bd8ca686"
    extId = "KAnnotator___GitHub"
    name = "KAnnotator @ GitHub"
    url = "git://github.com/JetBrains/kannotator.git"
    branch = "master"
    branchSpec = "+:refs/heads/(rr/*)"
    userNameStyle = GitVcsRoot.UserNameStyle.FULL
    useMirrors = false
})
