package Kotlin.buildTypes

import Kotlin.buildTypes.bt390
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
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
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_DeployMavenArtifactsNew : BuildType({
    uuid = "a23a9f10-57e5-4e5c-b046-9f6245e6b6ce"
    extId = "Kotlin_DeployMavenArtifactsNew"
    name = "Deploy Maven Artifacts (New)"

    allowExternalStatus = true
    buildNumberPattern = "%dep.bt345.build.number%"

    params {
        param("DeployVersion", "1.1-SNAPSHOT")
        param("jdk17.home", "%env.JDK_17%")
        param("jdk18.home", "%env.JDK_18%")
        password("kotlin.key", "credentialsJSON:44b57d29-21e2-4282-ab54-067b186e53c6", display = ParameterDisplay.HIDDEN)
        select("system.deploy-repo", "sonatype-nexus-snapshots",
                options = listOf("sonatype-nexus-snapshots-repo" to "sonatype-nexus-snapshots", "bintray-repo" to "bintray"))
        select("system.deploy-url", "http://oss.sonatype.org/service/local/staging/deploy/maven2/",
                options = listOf("sonatype-url (maven central)" to "http://oss.sonatype.org/service/local/staging/deploy/maven2/", "bintray-eap-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin", "bintray-eap-url (publish automatically)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin/;publish=1", "bintray-dev-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin", "bintray-eap-1.1-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap-1.1/kotlin"))
        password("system.kotlin.bintray.password", "credentialsJSON:b9a6d5db-5e01-44a1-8c9d-ad9d4ad696b1", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.bintray.user", "credentialsJSON:d67dd563-1873-4032-a677-6f645e354764", display = ParameterDisplay.HIDDEN)
        param("system.kotlin.key.name", "74CE0A0B")
        password("system.kotlin.key.passphrase", "credentialsJSON:10ca5873-d22f-4991-878e-464778281851", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.password", "credentialsJSON:2685bd0f-29c1-4f59-80ed-7ec4dbef257f", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.user", "credentialsJSON:f5a14b46-76c5-49a5-bd61-5f5e1fefb3c1", display = ParameterDisplay.HIDDEN)
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/dist/kotlinc")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Check build"
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
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "credentialsJSON:dc7b3645-c5f2-4edc-bd31-24075e1efa23")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
        }
        script {
            name = "Prepare gnupg"
            scriptContent = """
                cd libraries
                export HOME=${'$'}(pwd)
                rm -rf .gnupg
                cat >keyfile <<EOT
                %kotlin.key%
                EOT
                gpg --allow-secret-key-import --import keyfile
                rm -v keyfile
            """.trimIndent()
        }
        maven {
            name = "Set Version"
            goals = "versions:set"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-DnewVersion=%DeployVersion%"
            mavenVersion = auto()
            userSettingsSelection = "jb mirror"
            useOwnLocalRepo = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Libraries Deploy"
            goals = "deploy"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-Dinvoker.skip=true -DskipTests --activate-profiles noTest,sign-artifacts -e -X"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "userSettingsSelection:byPath"
            userSettingsPath = "%system.teamcity.build.checkoutDir%/libraries/maven-settings.xml"
            useOwnLocalRepo = true
            jdkHome = "%env.JDK_16%"
            jvmArgs = "-Xmx986m -XX:MaxPermSize=350m"
            param("teamcity.build.workingDir", "libraries")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "credentialsJSON:ca821363-e8f7-4770-a063-249ebe9c48bf")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "ilya.gorbunov@jetbrains.com")
        }
        script {
            name = "Cleanup"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            scriptContent = """
                cd libraries
                rm -rf .gnupg
            """.trimIndent()
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildTypeExtId = bt390.extId
            successfulOnly = true
        }
        vcs {
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = "+:<default>"
            watchChangesInDependencies = true
        }
    }

    failureConditions {
        executionTimeoutMin = 90
        errorMessage = true
    }

    features {
        feature {
            type = "perfmon"
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt390) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                cleanDestination = true
                artifactRules = "** => dist"
            }
        }
        artifacts(Kotlin.buildTypes.bt345) {
            artifactRules = """
                kotlin-compiler*.zip!**=>dist_bk
                kotlin-compiler-javadoc.jar=>dist_bk
                kotlin-compiler-sources.jar=>dist_bk
                internal/native-platform-uberjar.jar=>dependencies
            """.trimIndent()
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        noLessThan("teamcity.agent.work.dir.freeSpaceMb", "500")
    }
})
