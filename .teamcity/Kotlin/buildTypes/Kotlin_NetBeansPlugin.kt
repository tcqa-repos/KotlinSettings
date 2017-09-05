package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.vcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.BuildFailureOnMetric.*
import jetbrains.buildServer.configs.kotlin.v10.failureConditions.failOnMetricChange
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_NetBeansPlugin : BuildType({
    uuid = "4ab41e04-95e0-4168-8fad-b39e2698994f"
    extId = "Kotlin_NetBeansPlugin"
    name = "NetBeans Plugin"

    artifactRules = "target"
    buildNumberPattern = "0.2.0-dev-%build.counter%"

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinNetbeans)

        checkoutMode = CheckoutMode.ON_AGENT
    }

    steps {
        ant {
            name = "Download Compiler"
            enabled = false
            mode = antFile {
                path = "kotlin-bundled-compiler/get_bundled.xml"
            }
            workingDir = "kotlin-bundled-compiler"
            antArguments = "-Dno_eclipse=true"
        }
        maven {
            name = "Download kotlin-ide-common"
            goals = "validate"
            mavenVersion = auto()
            userSettingsPath = ""
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Maven Clean"
            goals = "clean"
            runnerArgs = "-X"
            mavenVersion = bundled_3_0()
            userSettingsPath = ""
            jdkHome = "%env.JDK_17%"
            jvmArgs = "-Xmx1024m -XX:MaxPermSize=300m -ea"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "credentialsJSON:22db9ec7-6894-4cd8-acdc-a2dfc55c9995")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "zarechenskiy")
        }
        maven {
            name = "Build and test plugins"
            goals = "install"
            runnerArgs = "-X"
            mavenVersion = bundled_3_0()
            userSettingsPath = ""
            jdkHome = "%env.JDK_18%"
            jvmArgs = "-Xmx1024m -XX:MaxPermSize=300m -ea"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "credentialsJSON:22db9ec7-6894-4cd8-acdc-a2dfc55c9995")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "zarechenskiy")
        }
    }

    triggers {
        vcs {
        }
        schedule {
            enabled = false
            schedulingPolicy = daily {
                hour = 5
            }
            branchFilter = "+:<default>"
            triggerBuild = always()
            withPendingChangesOnly = false
            param("dayOfWeek", "Sunday")
        }
    }

    failureConditions {
        nonZeroExitCode = false
        failOnMetricChange {
            metric = BuildFailureOnMetric.MetricType.PASSED_TEST_COUNT
            units = BuildFailureOnMetric.MetricUnit.DEFAULT_UNIT
            comparison = BuildFailureOnMetric.MetricComparison.LESS
            compareTo = value()
            param("anchorBuild", "lastSuccessful")
        }
    }

    features {
        vcsLabeling {
            vcsRootExtId = "__ALL__"
            labelingPattern = "%system.build.number%"
            successfulOnly = true
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Windows", "RQ_3")
        doesNotContain("system.agent.name", "win2008-v9-i-c45fa85d-1")
    }
    
    disableSettings("RQ_3")
})
