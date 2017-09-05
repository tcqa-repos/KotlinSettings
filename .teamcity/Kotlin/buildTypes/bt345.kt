package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.Trigger
import jetbrains.buildServer.configs.kotlin.v10.Trigger.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.vcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object bt345 : BuildType({
    template(Kotlin.buildTypes.Kotlin_KotlinCompilerAndPlugin)
    uuid = "52462149-4dc2-4d47-bc4f-62468f92e7fc"
    extId = "bt345"
    name = "Compiler and Plugin"

    artifactRules = """
        out/artifacts
        dist/kotlin-compiler-*.zip
        dist/kotlin-compiler-javadoc.jar
        dist/kotlin-compiler-sources.jar
        dist/kotlin-compiler-for-maven.jar
        dist/kotlin-compiler-before-shrink.jar => internal
        dist/kotlin-for-upsource.jar => internal
        dist/kotlin-for-upsource-sources.jar => internal
        dist/kotlin-reflect-sources-for-maven.jar => internal
        dist/kotlin-test-data.zip* => internal
        dist/kotlin-stdlib-js-sources.jar => internal
        dependencies/native-platform-uberjar.jar => internal
        out/*.hprof => internal
        **/hs_err*.log => internal
    """.trimIndent()
    buildNumberPattern = "%kotlin.build.number%"

    params {
        param("idea.build.18.run.configurations", """
            Java 8 Tests
            All Non Compiler Tests
        """.trimIndent())
        param("kotlin.build.number", "%version.prefix%%build.counter%")
        param("teamcity.ideaRunner.additional.lib.path", "%kotlin.bootstrap.home%/../lib/jps;%kotlin.bootstrap.home%/../lib/kotlin-runtime.jar;%kotlin.bootstrap.home%/lib/kotlin-compiler.jar")
        param("version.prefix", "1.1.2-dev-")
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = """
                +:*
                -:1.1.*
                -:rrb/*
                -:M12/bootstrap
            """.trimIndent()
        }
        trigger {
            id = "remoteRunOnBranch"
            type = "remoteRunOnBranch"
            param("branchy:jetbrains.git", "pattern:jetbrains.git")
        }
    }

    features {
        vcsLabeling {
            id = "BUILD_EXT_24"
            vcsRootExtId = "__ALL__"
            branchFilter = """
                +:<default>
                +:M12/bootstrap
                +:M15
                +:beta2
            """.trimIndent()
        }
        vcsLabeling {
            id = "BUILD_EXT_25"
            vcsRootExtId = "__ALL__"
            labelingPattern = "android-%system.build.number%"
            branchFilter = "+:android_studio"
        }
        vcsLabeling {
            id = "BUILD_EXT_26"
            vcsRootExtId = "__ALL__"
            labelingPattern = "m8-%system.build.number%"
            branchFilter = "+:idea13_M8"
        }
        freeDiskSpace {
            id = "jetbrains.agent.free.space"
            requiredSpace = "6gb"
            failBuild = false
        }
        feature {
            id = "perfmon"
            type = "perfmon"
        }
        swabra {
            id = "swabra"
            filesCleanup = Swabra.FilesCleanup.DISABLED
            lockingProcesses = Swabra.LockingProcessPolicy.KILL
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.Kotlin_CompilerAndPluginBootstrapNoTests) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_260"
                cleanDestination = true
                artifactRules = """
                    kotlin-compiler-*.zip!**=>%system.kotlin.bootstrap.plugin.dir%/Kotlin
                    KotlinJpsPlugin/kotlin-jps-plugin.jar=>%system.kotlin.bootstrap.plugin.dir%/Kotlin/lib/jps
                """.trimIndent()
            }
        }
    }

    cleanup {
        artifacts(builds = 2, days = 7)
    }
    
    disableSettings("RUNNER_148")
})
