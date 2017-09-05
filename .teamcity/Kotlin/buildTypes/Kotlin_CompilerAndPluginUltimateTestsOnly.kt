package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.Trigger
import jetbrains.buildServer.configs.kotlin.v10.Trigger.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_CompilerAndPluginUltimateTestsOnly : BuildType({
    template(Kotlin.buildTypes.Kotlin_KotlinCompilerAndPlugin)
    uuid = "2afede81-6870-476a-a6f3-96df0b770567"
    extId = "Kotlin_CompilerAndPluginUltimateTestsOnly"
    name = "Compiler and Plugin (Ultimate Tests Only)"

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
        param("version.prefix", "1.1.0-dev-")
    }

    steps {
        ideaRunner {
            name = "IntelliJ IDEA Project - Java 1.6 (1)"
            id = "RUNNER_740"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%kotlin.jdk16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%kotlin.jdk18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = """
                -Xmx1000M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:ReservedCodeCacheSize=64m -XX:+UseCodeCacheFlushing
                -XX:MaxPermSize=500m -ea %kotlin.idea.build.additional.vm.options%
            """.trimIndent()
            targetJdkHome = "%kotlin.jdk16%"
            makeRequiredModulesOnly = true
            artifactsToBuild = "%idea.build.16.artifacts%"
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        ideaRunner {
            name = "IntelliJ IDEA Project - Java 1.8 (1)"
            id = "RUNNER_741"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%kotlin.jdk16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%kotlin.jdk18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = """
                -Xmx1000M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:ReservedCodeCacheSize=64m -XX:+UseCodeCacheFlushing
                -XX:MaxPermSize=500m -ea %kotlin.idea.build.additional.vm.options%
            """.trimIndent()
            targetJdkHome = "%kotlin.jdk18%"
            incrementalMake = true
            artifactsToBuild = "%idea.build.18.artifacts%"
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        stepsOrder = arrayListOf("RUNNER_777", "RUNNER_58", "RUNNER_60", "RUNNER_96", "RUNNER_65", "RUNNER_740", "RUNNER_775", "RUNNER_741", "RUNNER_821", "RUNNER_19", "RUNNER_33", "RUNNER_112", "RUNNER_246", "RUNNER_255", "RUNNER_66", "RUNNER_95")
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            enabled = false
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = """
                +:*
                -:rrb/*
                -:M12/bootstrap
            """.trimIndent()
        }
        trigger {
            id = "remoteRunOnBranch"
            type = "remoteRunOnBranch"
            enabled = false
            param("branchy:jetbrains.git", "pattern:jetbrains.git")
        }
    }

    features {
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
                id = "ARTIFACT_DEPENDENCY_218"
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
    
    disableSettings("RUNNER_148", "RUNNER_65", "RUNNER_775")
})
