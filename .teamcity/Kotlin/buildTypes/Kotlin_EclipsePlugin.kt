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
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_EclipsePlugin : BuildType({
    uuid = "1bd76bef-6d04-4d61-8ce6-c605c6924f29"
    extId = "Kotlin_EclipsePlugin"
    name = "Eclipse Plugin"

    artifactRules = "kotlin-eclipse-p2updatesite/target/repository"
    buildNumberPattern = "0.8.2.%build.counter%"

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinEclipse)

        checkoutMode = CheckoutMode.ON_AGENT
    }

    steps {
        ant {
            name = "Download Compiler"
            mode = antFile {
                path = "kotlin-bundled-compiler/get_bundled.xml"
            }
            workingDir = "kotlin-bundled-compiler"
            antArguments = "-Dno_eclipse=true"
        }
        maven {
            name = "Maven Clean"
            goals = "clean"
            runnerArgs = "-X"
            mavenVersion = bundled_3_0()
            userSettingsPath = ""
            param("jvmArgs", "-Xmx1024m -XX:MaxPermSize=300m -ea")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "zarechenskiy")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx0fe250f445e93afbcc26222d89f6cdbd")
            param("target.jdk.home", "%env.JDK_17%")
        }
        maven {
            name = "Build and test plugins"
            goals = "install"
            runnerArgs = "-X"
            mavenVersion = bundled_3_0()
            userSettingsPath = ""
            param("jvmArgs", "-Xmx1024m -XX:MaxPermSize=300m -ea")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "zarechenskiy")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx0fe250f445e93afbcc26222d89f6cdbd")
            param("target.jdk.home", "%env.JDK_18%")
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
            triggerBuild = always()
            withPendingChangesOnly = false
            param("dayOfWeek", "Sunday")
            param("branchFilter", "+:<default>")
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
        contains("teamcity.agent.jvm.os.name", "Windows")
    }
})
