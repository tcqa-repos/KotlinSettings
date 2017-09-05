package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object bt411 : BuildType({
    uuid = "b0ca244c-0f2d-413d-ae9a-7742efcf3b85"
    extId = "bt411"
    name = "KAnnotator Jps Plugin Test"
    description = "Run Jps Test on KAnnotator sources"
    paused = true

    artifactRules = "dist => dist.zip"

    params {
        param("kotlin.bootstrap.home", "%system.kotlin.bootstrap.plugin.dir%/Kotlin/kotlinc")
        param("kotlinHome", "%teamcity.build.checkoutDir%/dist/Kotlin/kotlinc")
        param("system.jps.kotlin.extra.annotation.paths", "%kotlinHome%/lib/kotlin-jdk-annotations.jar")
        param("system.kotlin.bootstrap.plugin.dir", "%teamcity.build.checkoutDir%/dependencies/bootstrap-compiler")
        param("system.path.macro.KOTLIN.BUNDLED", "%kotlinHome%")
        param("teamcity.ideaRunner.additional.lib.path", "%kotlinHome%/../lib/jps;%kotlinHome%/../lib/kotlin-runtime.jar;%kotlinHome%/../lib/kotlin-plugin.jar")
    }

    vcs {
        root(Kotlin.vcsRoots.KAnnotator___GitHub, "+:.=>jps-plugin/testData/kannotator")
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Kotlin dependencies"
            mode = antFile {
                path = "update_dependencies.xml"
            }
            jdkHome = "%env.JDK_16%"
        }
        ant {
            name = "Kannotator dependencies"
            mode = antFile {
                path = "jps-plugin/testData/kannotator/update_dependencies.xml"
            }
            jdkHome = "%env.JDK_16%"
        }
        ideaRunner {
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("runnable", "")
            }
            jvmArgs = "-Xmx1500m -XX:MaxPermSize=500m"
            runConfigurations = "KAnnotator Jps Plugin Test"
            makeRequiredModulesOnly = true
        }
    }

    triggers {
        schedule {
            schedulingPolicy = daily {
                hour = 3
            }
            triggerBuild = always()
            param("dayOfWeek", "Sunday")
        }
        vcs {
            triggerRules = """
                -:.
                +:jps-plugin/**
            """.trimIndent()
            branchFilter = "+:<default>"
        }
    }

    failureConditions {
        executionTimeoutMin = 300
    }

    features {
        freeDiskSpace {
            requiredSpace = "1gb"
            failBuild = false
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                artifactRules = """
                    kotlin-plugin-*.zip!**=>%system.kotlin.bootstrap.plugin.dir%
                    kotlin-*.zip!**=>dist
                    kotlin-plugin-*.zip!/Kotlin/kotlinc/lib/kotlin-runtime.jar=>%teamcity.build.checkoutDir%/jps-plugin/testData/kannotator/lib/
                """.trimIndent()
            }
        }
    }
})
