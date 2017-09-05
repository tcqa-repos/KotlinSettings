package Kotlin_11_M1.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger

object Kotlin_11_M1_IdeaAndroidStudio22branch20161versionNoTests : BuildType({
    template(Kotlin_11_M1.buildTypes.Kotlin_11_M1_KotlinCompilerAndPlugin)
    uuid = "989685cd-1311-4335-9153-78b9dd52267f"
    extId = "Kotlin_11_M1_IdeaAndroidStudio22branch20161versionNoTests"
    name = "Idea Android Studio 2.2 branch (2016.1 version) - No Tests"

    params {
        param("CONST.system.ideaVersion.br143", "143.2072")
        param("release.branch.idea.suffix", "as22")
        param("system.ideaVersion", "145.257.12")
        param("update.dependencies.action", "update_android_studio")
        param("version.idea.readable.name", "Studio2.2")
    }

    steps {
        ideaRunner {
            name = "IntelliJ IDEA Project (with Java 1.8)"
            id = "RUNNER_880"
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
                -XX:MaxPermSize=500m
            """.trimIndent()
            targetJdkHome = "%env.JDK_18%"
            artifactsToBuild = """
                KotlinPlugin
                KotlinBarePlugin
                InjectorGenerator
                IdeLazyResolver
                KotlinAndroidExtensions
            """.trimIndent()
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        stepsOrder = arrayListOf("RUNNER_846", "RUNNER_847", "RUNNER_848", "RUNNER_849", "RUNNER_880", "RUNNER_850", "RUNNER_851", "RUNNER_852", "RUNNER_853", "RUNNER_854", "RUNNER_855", "RUNNER_856", "RUNNER_857", "RUNNER_858", "RUNNER_859")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_99"
            buildTypeExtId = bt345.extId
            successfulOnly = true
            branchFilter = "+:%release.tag.branch.name%"
        }
    }

    cleanup {
        artifacts(builds = 2, days = 5)
    }
    
    disableSettings("RUNNER_849", "RUNNER_850", "RUNNER_852", "RUNNER_853", "RUNNER_854", "RUNNER_855", "RUNNER_856", "vcsTrigger")
})
