package Kotlin.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric.*
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.RetryBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.RetryBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.retryBuild
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object bt384 : BuildType({
    uuid = "9eb279d0-776f-454e-a946-50f9bee7d83c"
    extId = "bt384"
    name = "Android: JVM Codegen Tests"

    artifactRules = "out/*.hprof"
    buildNumberPattern = "%build.counter% - %dep.bt345.build.number%"

    params {
        param("env.kotlin.tests.android.timeout", "40")
        param("kotlin.bootstrap.home", "%system.kotlin.bootstrap.plugin.dir%/Kotlin/kotlinc")
        param("kotlinUnpackDependencyDir", "%teamcity.build.checkoutDir%/dependencies/kotlin")
        param("system.jps.kotlin.extra.annotation.paths", "%kotlin.bootstrap.home%/lib/kotlin-jdk-annotations.jar")
        param("system.kotlin.bootstrap.plugin.dir", "%teamcity.build.checkoutDir%/dependencies/bootstrap-compiler")
        param("system.path.macro.KOTLIN.BUNDLED", "%kotlin.bootstrap.home%")
        param("teamcity.ideaRunner.additional.lib.path", "%kotlin.bootstrap.home%/../lib/jps;%kotlin.bootstrap.home%/../lib/kotlin-runtime.jar;%kotlin.bootstrap.home%/../lib/kotlin-reflect.jar;%kotlin.bootstrap.home%/../lib/kotlin-plugin.jar")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Fetch third party dependencies"
            mode = antFile {
                path = "update_dependencies.xml"
            }
            targets = "update"
            jdkHome = "%system.jdk16.home%"
        }
        ant {
            name = "generateInjectors"
            enabled = false
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "invokeGenerators"
            jdkHome = "%system.jdk16.home%"
        }
        ant {
            name = "dist"
            enabled = false
            mode = antFile {
            }
            targets = "dist"
            jvmArgs = "-Xmx1G -ea"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("org.jfrog.artifactory.selectedDeployableServer.overrideDefaultDeployerCredentials", "true")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx2a11c9292201b6c809d59f4f98fe98d1f1b3e6e923118575")
        }
        ideaRunner {
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%env.JDK_18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = "-Xmx700M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out -XX:MaxPermSize=350m"
            targetJdkHome = "%env.JDK_18%"
            runConfigurations = "Codegen Tests on Android"
            makeRequiredModulesOnly = true
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
    }

    triggers {
        vcs {
            enabled = false
        }
        finishBuildTrigger {
            buildTypeExtId = bt345.extId
            branchFilter = """
                +:<default>
                +:1.0.*
                +:1.1*
            """.trimIndent()
        }
        schedule {
            enabled = false
            schedulingPolicy = daily {
                hour = 5
            }
            triggerBuild = always()
            param("dayOfWeek", "Sunday")
        }
        retryBuild {
            enabled = false
            delaySeconds = 600
            attempts = 2
        }
    }

    failureConditions {
        failOnMetricChange {
            metric = BuildFailureOnMetric.MetricType.TEST_COUNT
            threshold = 10
            units = BuildFailureOnMetric.MetricUnit.DEFAULT_UNIT
            comparison = BuildFailureOnMetric.MetricComparison.LESS
            compareTo = value()
            param("anchorBuild", "lastSuccessful")
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.IGNORE
            }

            artifacts {
                cleanDestination = true
                artifactRules = """
                    kotlin-plugin-*.zip!**=>%system.kotlin.bootstrap.plugin.dir%
                    kotlin-plugin-*.zip!Kotlin/kotlinc/lib/**=>dist/kotlinc/lib
                """.trimIndent()
            }
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        doesNotContain("system.agent.name", "10.10")
    }
})
