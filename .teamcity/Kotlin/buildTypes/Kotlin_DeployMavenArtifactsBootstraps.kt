package Kotlin.buildTypes

import Kotlin.buildTypes.bt345
import Kotlin.buildTypes.bt390
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.Trigger
import jetbrains.buildServer.configs.kotlin.v10.Trigger.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_DeployMavenArtifactsBootstraps : BuildType({
    uuid = "c2b8865a-4573-47dd-afff-41073768313a"
    extId = "Kotlin_DeployMavenArtifactsBootstraps"
    name = "Deploy Maven Artifacts (bootstraps)"

    allowExternalStatus = true
    buildNumberPattern = "%dep.bt345.build.number%"

    params {
        param("DeployVersion", "%dep.bt345.build.number%")
        param("jdk17.home", "%env.JDK_17%")
        param("jdk18.home", "%env.JDK_18%")
        select("system.deploy-repo", "bintray",
                options = listOf("bintray-repo" to "bintray", "sonatype-nexus-snapshots-repo" to "sonatype-nexus-snapshots"))
        select("system.deploy-url", "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin;publish=1",
                options = listOf("sonatype-url (maven central)" to "http://oss.sonatype.org/service/local/staging/deploy/maven2/", "bintray-eap-1.1-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap-1.1/kotlin", "bintray-eap-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin", "bintray-eap-url (publish automatically)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin/;publish=1", "bintray-dev-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin", "bintray-dev-url (publish automatically)" to "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin;publish=1"))
        password("system.kotlin.bintray.password", "zxx1b35d0c43c199eac801a3594a00e0550dbf2e62380451d0375d657c25ad36e38811fa87a51633ad9775d03cbe80d301b", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.bintray.user", "zxx529dbb75f4cf3ef14423ab58cd8f54b9", display = ParameterDisplay.HIDDEN)
        param("system.kotlin.key.name", "8A99F98A")
        password("system.kotlin.key.passphrase", "zxx2d8e59f9bfb0682b42d683a8d1c537a4468a048c31f01e807a0fcab54f31132be3539aad36189de6ce2af5e8973753dc8f38903b3d45c5ee2bb05528738b23f467bd58bf8625fc017442db755ee44d53ccfb759b52161faa9c0ee5f719e2228459d02642b7d0fe93", display = ParameterDisplay.HIDDEN)
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/dist/kotlinc")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_AGENT
        cleanCheckout = true
    }

    steps {
        script {
            name = "Checkout corresponding version"
            scriptContent = "git checkout %dep.bt345.build.vcs.number%"
        }
        ant {
            name = "Check build"
            enabled = false
            mode = antScript {
                content = """
                    <project name="AssertRightBuild">
                      <target name="check">
                        <echo message="Branch: %teamcity.build.vcs.branch.Kotlin_KotlinGithub2%"/>
                        <fail message="Do not remote run this build" if="build.is.personal" />
                    
                        <fail message="Builds to Maven Central should be published only from master and release branches">
                          <condition>
                            <and>
                            <equals arg1="%system.deploy-repo%" arg2="sonatype-nexus-snapshots"/>
                            <not>
                              <equals arg1="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" arg2="refs/heads/master"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="/bootstrap"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M13"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M14"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M15"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="beta\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="bintray"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="rc\d*${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="1.0.\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="1.1-M\d+${'$'}"/>
                            </not>
                            </and>
                          </condition>
                        </fail>
                    
                        </target>
                    </project>
                """.trimIndent()
            }
            targets = "check"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx07d67df0d96aad3350e276915eed3f06863eb00272fa1d7f")
        }
        maven {
            name = "Set Version"
            goals = "versions:set"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-DnewVersion=%DeployVersion%"
            userSettingsSelection = "jb mirror"
            useOwnLocalRepo = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Libraries Deploy"
            goals = "deploy"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-Dinvoker.skip=true -DskipTests --activate-profiles noTest -e -X"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "userSettingsSelection:byPath"
            userSettingsPath = "%system.teamcity.build.checkoutDir%/libraries/maven-settings.xml"
            useOwnLocalRepo = true
            param("jvmArgs", "-Xmx986m -XX:MaxPermSize=350m")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "ilya.gorbunov@jetbrains.com")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx8c2a7eb4026210ed775d03cbe80d301b")
            param("target.jdk.home", "%env.JDK_16%")
            param("teamcity.build.workingDir", "libraries")
        }
        script {
            name = "Publish bintray"
            scriptContent = "curl -D - -X POST --basic -u %system.kotlin.bintray.user%:%system.kotlin.bintray.password% https://api.bintray.com/content/kotlin/kotlin-dev/kotlin/%dep.bt345.build.number%/publish"
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildTypeExtId = bt390.extId
            successfulOnly = true
        }
        vcs {
            enabled = false
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = "+:<default>"
            watchChangesInDependencies = true
        }
        schedule {
            enabled = false
            schedulingPolicy = daily {
                hour = 6
            }
            triggerBuild = onWatchedBuildChange {
                buildType = bt345.extId
                watchedBuildRule = ScheduleTrigger.WatchedBuildRule.TAG
                watchedBuildTag = "bootstrap"
            }
            withPendingChangesOnly = false
            param("enforceCleanCheckout", "true")
            param("branchFilter", "+:<default>")
            param("dayOfWeek", "Sunday")
        }
        trigger {
            type = "urlBuildTrigger"
            enabled = false
            param("url.build.trigger.url", "https://teamcity.jetbrains.com/guestAuth/repository/download/bt345/bootstrap.tcbuildtag/updatePlugins.xml")
        }
        schedule {
            schedulingPolicy = cron {
                minutes = "*/3"
            }
            triggerBuild = onWatchedBuildChange {
                buildType = bt345.extId
                watchedBuildRule = ScheduleTrigger.WatchedBuildRule.TAG
                watchedBuildTag = "bootstrap"
                promoteWatchedBuild = false
            }
            param("branchFilter", "+:<default>")
            param("dayOfWeek", "Sunday")
        }
    }

    failureConditions {
        executionTimeoutMin = 240
        errorMessage = true
    }

    features {
        feature {
            type = "perfmon"
        }
    }

    dependencies {
        artifacts(Kotlin.buildTypes.bt345) {
            buildRule = tag("bootstrap")
            artifactRules = """
                kotlin-compiler*.zip!**=>dist_bk
                kotlin-compiler-javadoc.jar=>dist_bk
                kotlin-compiler-sources.jar=>dist_bk
                kotlin-compiler-for-maven.jar=>dist_bk
                internal/native-platform-uberjar.jar=>dependencies
            """.trimIndent()
        }
        artifacts(Kotlin.buildTypes.bt390) {
            buildRule = build("%dep.bt345.build.number%")
            cleanDestination = true
            artifactRules = "**=>dist"
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        noLessThan("teamcity.agent.work.dir.freeSpaceMb", "500")
    }
})
