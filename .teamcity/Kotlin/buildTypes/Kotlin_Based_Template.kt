package Kotlin.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_Based_Template : Template({
    uuid = "874f1d5e-b589-4caa-bcaa-8e69b9c1498b"
    extId = "Kotlin_Based_Template"
    name = "Kotlin-based Project"

    artifactRules = "out/artifacts/*.jar"
    buildNumberPattern = "%build.counter% - Kotlin %dep.bt345.build.number%"

    params {
        param("system.artifactory.deploy.branch", "%dep.bt345.teamcity.build.branch%")
        param("system.jps.kotlin.extra.annotation.paths", "%system.kotlinHome%/lib/kotlin-jdk-annotations.jar")
        param("system.jps.kotlin.home", "%system.kotlinHome%")
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/kotlin/Kotlin/kotlinc")
        param("teamcity.ideaRunner.additional.lib.path", "%system.kotlinHome%/../lib/jps;%system.kotlinHome%/../lib/kotlin-runtime.jar;%system.kotlinHome%/../lib/kotlin-plugin.jar")
    }

    vcs {
        checkoutMode = CheckoutMode.ON_SERVER
    }

    triggers {
        vcs {
            id = "vcsTrigger"
        }
        finishBuildTrigger {
            id = "TRIGGER_61"
            buildTypeExtId = bt345.extId
            successfulOnly = true
            branchFilter = """
                +:<default>
                +:rr/*
                +:idea*
            """.trimIndent()
        }
    }

    features {
        swabra {
            id = "swabra"
            forceCleanCheckout = true
            lockingProcesses = Swabra.LockingProcessPolicy.KILL
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_87"
                artifactRules = """
                    kotlin-plugin-*.zip!**=>kotlin
                    kotlin-plugin-*.zip!/Kotlin/kotlinc/lib/kotlin-runtime.jar=>%teamcity.build.checkoutDir%/lib
                """.trimIndent()
            }
        }
    }
})
