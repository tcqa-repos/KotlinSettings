package Kotlin.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_ts2kt : BuildType({
    uuid = "b6ca0b2c-e8be-4c92-b04a-4773bce64d97"
    extId = "Kotlin_ts2kt"
    name = "TypeScript Declaration to Kotlin Converter"

    artifactRules = "out"
    buildNumberPattern = "%build.counter% - Kotlin %dep.bt345.build.number%"

    params {
        param("system.jps.kotlin.extra.annotation.paths", "%system.kotlinHome%/lib/kotlin-jdk-annotations.jar")
        param("system.jps.kotlin.home", "%system.kotlinHome%")
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/kotlin/Kotlin/kotlinc")
        param("system.path.macro.KOTLIN.BUNDLED", "%system.kotlinHome%")
        param("teamcity.ideaRunner.additional.lib.path", "%system.kotlinHome%/../lib/jps;%system.kotlinHome%/../lib/kotlin-runtime.jar;%system.kotlinHome%/../lib/kotlin-plugin.jar;%system.kotlinHome%/../lib/kotlin-reflect.jar")
    }

    vcs {
        root(Kotlin.vcsRoots.Vcs_Kotlin_ts2kt)

    }

    steps {
        ant {
            name = "Download Tools"
            mode = antFile {
            }
            targets = "update.tools"
        }
        ideaRunner {
            name = "Build"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            artifactsToBuild = "ts2kt"
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED
        }
        ant {
            name = "Node Dependencies"
            mode = antFile {
            }
            targets = "tc.update.node.modules"
        }
        ant {
            name = "Test on testData"
            mode = antFile {
            }
            targets = "tc.run.test.for.testData"
        }
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = bt345.extId
            successfulOnly = true
            branchFilter = """
                +:*
                -:rri/*
            """.trimIndent()
        }
        vcs {
        }
    }

    failureConditions {
        executionTimeoutMin = 20
    }

    features {
        swabra {
            forceCleanCheckout = true
            lockingProcesses = Swabra.LockingProcessPolicy.KILL
            verbose = true
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
            }

            artifacts {
                artifactRules = """
                    kotlin-plugin-*.zip!**=>kotlin
                    kotlin-plugin-*.zip!/Kotlin/kotlinc/lib/kotlin-runtime.jar=>%teamcity.build.checkoutDir%/lib
                    kotlin-plugin-*.zip!/Kotlin/kotlinc/lib/kotlin-test.jar=>%teamcity.build.checkoutDir%/lib
                """.trimIndent()
            }
        }
    }
})
