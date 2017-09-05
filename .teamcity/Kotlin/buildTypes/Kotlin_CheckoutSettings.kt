package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*

object Kotlin_CheckoutSettings : BuildType({
    uuid = "7090962a-df35-464b-b417-2921dbf42943"
    extId = "Kotlin_CheckoutSettings"
    name = "CheckoutSettings"

    vcs {
        root("Kotlin_HttpsGithubComJetBrainsKotlinTeamcityBuildGit")

    }
})
