package Kotlin.buildTypes

import Kotlin.buildTypes.Kotlin_CompilerAndPluginBootstrapNoTests
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.Trigger
import jetbrains.buildServer.configs.kotlin.v10.Trigger.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_AndroidIntegrationTests : BuildType({
    uuid = "fa9acdba-c6d5-44b8-b548-d121993eaee8"
    extId = "Kotlin_AndroidIntegrationTests"
    name = "Android Gradle Integration Tests"

    allowExternalStatus = true
    artifactRules = "**/build.log"
    buildNumberPattern = "%dep.bt345.build.number%"

    params {
        param("jdk17.home", "%env.JDK_17%")
        param("jdk18.home", "%env.JDK_18%")
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/dist/kotlinc")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub11)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
        showDependenciesChanges = true
    }

    steps {
        ant {
            name = "Temporary hack: create dist/builtins directory"
            mode = antScript {
                content = """
                    <project name="Kotlin" default="create-builtins-dir">
                        <property name="build.number" value="snapshot"/>
                        <property name="kotlin-runtime.jar" value="dist/kotlinc/lib/kotlin-runtime.jar"/>
                    
                        <target name="create-builtins-dir">
                            <unzip src="${'$'}{kotlin-runtime.jar}" dest="dist/builtins">
                                <patternset>
                                    <include name="kotlin/*.kotlin_*"/>
                                    <include name="kotlin/reflect/*.kotlin_*"/>
                                    <include name="kotlin/ranges/*.kotlin_*"/>
                                    <include name="kotlin/collections/*.kotlin_*"/>
                                    <include name="kotlin/annotation/*.kotlin_*"/>
                                    <include name="kotlin/internal/*.kotlin_*"/>
                                </patternset>
                            </unzip>
                        </target>
                    </project>
                """.trimIndent()
            }
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "ilya.gorbunov@jetbrains.com")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx8c2a7eb4026210ed775d03cbe80d301b")
        }
        ant {
            name = "Download Android SDK"
            mode = antFile {
                path = "download_android_sdk.xml"
            }
            targets = "download_android_sdk"
        }
        maven {
            name = "Libraries"
            goals = "clean install -DskipTests -PnoTest"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-e -X -Dmaven.test.failure.ignore=true"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "jb mirror"
            userSettingsPath = ""
            param("jvmArgs", "-Xmx1200m -XX:MaxPermSize=350m")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "yole")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx4bf891eaf2acfa7416fa0f5a3ef609f8")
            param("target.jdk.home", "%env.JDK_16%")
            param("teamcity.build.workingDir", "libraries")
        }
        maven {
            name = "Run Android integration tests"
            goals = "integration-test"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-e -X -pl :kotlin-gradle-plugin-integration-tests -Dtest=KotlinAndroidGradleCLIOnly"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "jb mirror"
            userSettingsPath = ""
            param("jvmArgs", "-Xmx1200m -XX:MaxPermSize=350m")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "yole")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx4bf891eaf2acfa7416fa0f5a3ef609f8")
            param("target.jdk.home", "%env.JDK_16%")
            param("teamcity.build.workingDir", "libraries")
        }
    }

    triggers {
        finishBuildTrigger {
            buildTypeExtId = Kotlin_CompilerAndPluginBootstrapNoTests.extId
            successfulOnly = true
            branchFilter = """
                +:rr/android
                +:master
            """.trimIndent()
        }
        schedule {
            enabled = false
            schedulingPolicy = daily {
                hour = 8
            }
            triggerBuild = always()
            param("enforceCleanCheckout", "true")
            param("dayOfWeek", "Sunday")
        }
        trigger {
            type = "remoteRunOnBranch"
            enabled = false
            param("branchy:jetbrains.git", "pattern:jetbrains.git")
        }
        vcs {
            enabled = false
            branchFilter = """
                +:*
                -:rrb/*
            """.trimIndent()
            watchChangesInDependencies = true
        }
    }

    failureConditions {
        executionTimeoutMin = 120
        errorMessage = true
    }

    features {
        feature {
            type = "perfmon"
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                cleanDestination = true
                artifactRules = """
                    kotlin-compiler-*.zip!/**=>dist
                    *=>dist
                    internal/kotlin-reflect-sources-for-maven.jar=>dist
                    internal/native-platform-uberjar.jar=>dependencies
                """.trimIndent()
            }
        }
    }

    requirements {
        doesNotMatch("cloud.amazon.agent-name-prefix", "mps-ubuntu.*")
        noLessThan("teamcity.agent.work.dir.freeSpaceMb", "1000")
        doesNotMatch("teamcity.agent.name", "JetBrains-dotnet-.*")
    }

    cleanup {
        history(days = 10)
        artifacts(builds = 2, days = 5)
    }
})
