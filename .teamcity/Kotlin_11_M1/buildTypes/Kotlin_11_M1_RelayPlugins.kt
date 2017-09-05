package Kotlin_11_M1.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.vcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger

object Kotlin_11_M1_RelayPlugins : BuildType({
    uuid = "ac390136-e058-4179-974d-bc5dad05d9fe"
    extId = "Kotlin_11_M1_RelayPlugins"
    name = "Relay Plugins"
    description = "This configuration do simple repack for plugins in release branch with version modification"

    artifactRules = "%build.number%/*.zip"
    buildNumberPattern = "%dep.bt345.build.number%-%version.idea.readable.name%-%build.counter%"

    params {
        param("release.branch.idea.suffix", "%CONST.release.branch.idea.suffix.br143%")
        param("system.ideaVersion", "%CONST.system.ideaVersion.br143%")
        param("system.relay.origin.version", "%dep.bt345.build.number%")
        param("system.relay.plugins.dir", "artifacts")
        param("system.relay.substitute.version", "%build.number%")
        param("version.idea.readable.name", "%CONST.version.idea.readable.name.br143%")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_M11_KotlinBuild)

        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        ant {
            name = "Replace version in plugins"
            mode = antFile {
                path = "TeamCityRelay.xml"
            }
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildTypeExtId = bt345.extId
        }
    }

    features {
        vcsLabeling {
            vcsRootExtId = "__ALL__"
            labelingPattern = "%release.branch.prefix%%system.build.number%-relay"
        }
    }

    dependencies {
        artifacts(Kotlin.buildTypes.bt345) {
            buildRule = lastSuccessful("%release.tag.branch.name%")
            artifactRules = "*plugin*.zip=>%system.relay.plugins.dir%"
        }
    }
})
