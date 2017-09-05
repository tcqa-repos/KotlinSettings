package Kotlin_11_M1.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner

object Kotlin_11_M1_Idea145branch160version : BuildType({
    template(Kotlin_11_M1.buildTypes.Kotlin_11_M1_KotlinCompilerAndPlugin)
    uuid = "54fced56-5be6-4310-8a9c-fc3aedddbf54"
    extId = "Kotlin_11_M1_Idea145branch160version"
    name = "Idea 145 branch (16.0 version)"

    params {
        param("CONST.system.ideaVersion.br143", "143.2072")
        param("release.branch.prefix", "%release.tag.branch.name%")
        param("system.ideaVersion", "145.257.12")
        param("version.idea.readable.name", "IJ145")
    }

    steps {
        ideaRunner {
            name = "IntelliJ IDEA Project (with Java 1.8)"
            id = "RUNNER_876"
            enabled = false
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
            runConfigurations = "All IDEA Plugin Tests"
            artifactsToBuild = """
                KotlinPlugin
                KotlinBarePlugin
                InjectorGenerator
                IdeLazyResolver
                KotlinAndroidExtensions
            """.trimIndent()
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
        }
        stepsOrder = arrayListOf("RUNNER_846", "RUNNER_847", "RUNNER_848", "RUNNER_849", "RUNNER_876", "RUNNER_850", "RUNNER_851", "RUNNER_852", "RUNNER_853", "RUNNER_854", "RUNNER_855", "RUNNER_856", "RUNNER_857", "RUNNER_858", "RUNNER_859")
    }

    cleanup {
        artifacts(builds = 2, days = 5)
    }
})
