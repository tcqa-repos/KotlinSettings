package Kotlin_11_M1

import Kotlin_11_M1.buildTypes.*
import Kotlin_11_M1.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project

object Project : Project({
    uuid = "24698dd5-f1e6-4caa-ac6f-3d1ee1f2db08"
    extId = "Kotlin_11_M1"
    parentId = "Kotlin"
    name = "1.1-M1"

    vcsRoot(Kotlin_11_M1_KotlinGithubMaster)

    buildType(Kotlin_11_M1_Idea142branch150versionNoTests)
    buildType(Kotlin_11_M1_Idea142branch150version)
    buildType(Kotlin_11_M1_IdeaAndroidStudio22branch20161versionNoTests)
    buildType(Kotlin_11_M1_AutoMerge)
    buildType(Kotlin_11_M1_Idea145branch160versionNoTests)
    buildType(Kotlin_11_M1_RelayPlugins)
    buildType(Kotlin_11_M1_IdeaAndroidStudio22branch20161version)
    buildType(Kotlin_11_M1_Idea145branch160version)

    template(Kotlin_11_M1_KotlinCompilerAndPluginRelease1)
    template(Kotlin_11_M1_KotlinCompilerAndPlugin)

    params {
        param("CONST.release.branch.idea.suffix.br143", "br143")
        param("CONST.release.branch.idea.suffix.br145", "br145")
        param("CONST.system.ideaVersion.br143", "143.2167.2")
        param("CONST.system.ideaVersion.br145", "2016.1")
        param("CONST.version.idea.readable.name.br143", "IJ143")
        param("CONST.version.idea.readable.name.br145", "IJ2016.1")
        param("release.branch.idea.suffix", "")
        param("release.branch.prefix", "%release.tag.branch.name%_/")
        param("release.tag", "pre_release")
        param("release.tag.branch.name", "1.1-M1")
        param("system.ideaVersion", "")
        param("version.idea.readable.name", "")
    }
})
