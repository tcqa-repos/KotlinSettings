package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.Swabra.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.swabra
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner

object Kotlin_CompilerAndPluginBootstrapNoTests : BuildType({
    template(Kotlin.buildTypes.Kotlin_KotlinCompilerAndPlugin)
    uuid = "a0959fc9-6e7d-48d7-bbf1-7cef1ff10cf3"
    extId = "Kotlin_CompilerAndPluginBootstrapNoTests"
    name = "Compiler Bootstrap - No Tests"

    artifactRules = """
        out/artifacts
        dist/kotlin-compiler-*.zip
        out/*.hprof
    """.trimIndent()

    params {
        param("bootstrap.branch.name", "<default>")
        select("bootstrap.tag", "bootstrap", description = "Tag for downloading kotlin compiler for current build",
                options = listOf("bootstrap", "bootstrap_M7", "bootstrap_test", "M8", "M9", "M10", "M10.1", "M11", "M11.1", "M12", "M12.1", "pre_release"))
        param("system.bootstrap.build.no.tests", "true")
        param("version.prefix", "bootstrap-(%dep.bt345.build.number%)")
    }

    steps {
        ant {
            name = "Standalone compiler - Bootstrap"
            id = "RUNNER_337"
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "build-bootstrap-artifacts"
            antHome = "%kotlin.ant.home%"
            antArguments = "-Dbootstrap.compiler.home=%kotlin.bootstrap.home%"
            jdkHome = "%env.JDK_16%"
            jvmArgs = "-Xmx1024m -ea -XX:MaxPermSize=200m"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("org.jfrog.artifactory.selectedDeployableServer.overrideDefaultDeployerCredentials", "true")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx10a7df6d479251d4de4363f3ccae2df8")
        }
        ideaRunner {
            name = "Intellij IDEA Project - No Tests"
            id = "RUNNER_181"
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
                -Xmx1024M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:MaxPermSize=300M
            """.trimIndent()
            targetJdkHome = "%kotlin.jdk18%"
            makeRequiredModulesOnly = true
            artifactsToBuild = "KotlinJpsPlugin"
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        ant {
            name = "Post Build - revertTemplateFiles"
            id = "RUNNER_398"
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "revertTemplateFiles"
            jdkHome = "%env.JDK_16%"
        }
        stepsOrder = arrayListOf("RUNNER_777", "RUNNER_58", "RUNNER_60", "RUNNER_96", "RUNNER_337", "RUNNER_65", "RUNNER_181", "RUNNER_775", "RUNNER_821", "RUNNER_19", "RUNNER_33", "RUNNER_112", "RUNNER_246", "RUNNER_255", "RUNNER_66", "RUNNER_398", "RUNNER_95")
    }

    features {
        freeDiskSpace {
            id = "jetbrains.agent.free.space"
            failBuild = false
        }
        swabra {
            id = "swabra"
            filesCleanup = Swabra.FilesCleanup.DISABLED
            lockingProcesses = Swabra.LockingProcessPolicy.KILL
        }
    }

    dependencies {
        artifacts(Kotlin.buildTypes.bt345) {
            id = "ARTIFACT_DEPENDENCY_169"
            buildRule = tag("%bootstrap.tag%", "%bootstrap.branch.name%")
            artifactRules = "kotlin-plugin-*.zip!**=>%system.kotlin.bootstrap.plugin.dir%"
        }
    }

    cleanup {
        artifacts(builds = 2, days = 3)
    }
    
    disableSettings("RUNNER_112", "RUNNER_148", "RUNNER_19", "RUNNER_246", "RUNNER_255", "RUNNER_33", "RUNNER_65", "RUNNER_66", "RUNNER_775", "RUNNER_821", "RUNNER_95", "RUNNER_96")
})
