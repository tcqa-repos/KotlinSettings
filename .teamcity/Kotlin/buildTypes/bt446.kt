package Kotlin.buildTypes

import Kotlin.buildTypes.bt438
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.GradleBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object bt446 : BuildType({
    uuid = "93017141-0687-48a7-a3a8-141adaf76892"
    extId = "bt446"
    name = "Compile Kotlin examples"


    vcs {
        root(Kotlin.vcsRoots.kotlin_examples___GitHub)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        maven {
            name = "Maven examples"
            goals = "clean package"
            pomLocation = "maven/pom.xml"
            runnerArgs = "-U"
            userSettingsSelection = "jb mirror"
            useOwnLocalRepo = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        gradle {
            name = "Gradle mixed-java-kotlin-hello-world"
            tasks = "build"
            workingDir = "gradle/mixed-java-kotlin-hello-world"
            gradleParams = "--gradle-user-home %teamcity.build.checkoutDir%/.gradleMixedJavaKotlin"
            useGradleWrapper = true
            enableDebug = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        gradle {
            name = "Gradle hello-world"
            tasks = "build"
            workingDir = "gradle/hello-world"
            gradleParams = "--gradle-user-home %teamcity.build.checkoutDir%/.gradleHelloWorld"
            useGradleWrapper = true
            enableDebug = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
    }

    triggers {
        vcs {
        }
        finishBuildTrigger {
            buildTypeExtId = bt438.extId
            successfulOnly = true
            branchFilter = """
                +:<default>
                +:rr/*
                +:idea*
            """.trimIndent()
        }
    }

    failureConditions {
        executionTimeoutMin = 15
        errorMessage = true
    }
})
