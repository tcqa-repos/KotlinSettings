package Kotlin_11_M1.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*

object Kotlin_11_M1_Idea142branch150version : BuildType({
    template(Kotlin_11_M1.buildTypes.Kotlin_11_M1_KotlinCompilerAndPlugin)
    uuid = "8dec4120-bc91-4679-8ede-507487dc3efc"
    extId = "Kotlin_11_M1_Idea142branch150version"
    name = "Idea 143 branch (15.0 version)"

    params {
        param("env.JDK8_PATH", "%env.JDK_18%/jre")
        param("release.branch.idea.suffix", "%CONST.release.branch.idea.suffix.br143%")
        param("system.ideaVersion", "%CONST.system.ideaVersion.br143%")
        param("version.idea.readable.name", "%CONST.version.idea.readable.name.br143%")
    }

    cleanup {
        artifacts(builds = 2, days = 5)
    }
})
