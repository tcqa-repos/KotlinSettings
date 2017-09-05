package Kotlin_11_M1.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger

object Kotlin_11_M1_Idea145branch160versionNoTests : BuildType({
    template(Kotlin_11_M1.buildTypes.Kotlin_11_M1_KotlinCompilerAndPlugin)
    uuid = "9775eadb-c7b4-487e-ade7-d0f62020efcf"
    extId = "Kotlin_11_M1_Idea145branch160versionNoTests"
    name = "Idea 145 branch (16.0 version) - No Tests"

    params {
        param("release.branch.prefix", "%release.tag.branch.name%")
        param("system.ideaVersion", "%CONST.system.ideaVersion.br145%")
        param("version.idea.readable.name", "%CONST.version.idea.readable.name.br145%")
    }

    steps {
        ideaRunner {
            name = "IntelliJ IDEA Project - Java 1.6 (No Tests)"
            id = "RUNNER_877"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
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
            name = "IntelliJ IDEA Project - Java 1.8 (No Tests)"
            id = "RUNNER_878"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
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
        stepsOrder = arrayListOf("RUNNER_846", "RUNNER_847", "RUNNER_848", "RUNNER_849", "RUNNER_877", "RUNNER_850", "RUNNER_878", "RUNNER_851", "RUNNER_852", "RUNNER_853", "RUNNER_854", "RUNNER_855", "RUNNER_856", "RUNNER_857", "RUNNER_858", "RUNNER_859")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_133"
            buildTypeExtId = bt345.extId
            branchFilter = "+:%release.tag.branch.name%"
        }
    }
    
    disableSettings("RUNNER_849", "RUNNER_850", "RUNNER_855", "RUNNER_856", "vcsTrigger")
})
