package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven

object Kotlin_SDKMan : BuildType({
    uuid = "891c7026-6aac-4502-b1dc-17b95eced41e"
    extId = "Kotlin_SDKMan"
    name = "SDKMan"

    buildNumberPattern = "%build.counter% %kotlin.version%"

    params {
        param("github.release.tag", "%kotlin.version%")
        password("key", "credentialsJSON:c088f462-cfd8-4dd3-8505-145bf89a1b74")
        text("kotlin.version", "1.0.0-beta-3595", display = ParameterDisplay.PROMPT, allowEmpty = true)
        param("sdkman-plugin-version", "1.1-SNAPSHOT")
        param("sdkman.host", "vendors.sdkman.io")
        password("token", "credentialsJSON:be6aea26-3840-4e72-8ab9-fc2294fea18c")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_SdkmanMavenPlugin)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        maven {
            name = "Install maven plugin"
            goals = "clean install"
            mavenVersion = bundled_3_2()
            userSettingsPath = ""
            jdkHome = "%env.JDK_18%"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Release kotlin to sdkman"
            goals = "io.sdkman:sdkman-maven-plugin:%sdkman-plugin-version%:release"
            runnerArgs = """
                -Dsdkman.api.host=%sdkman.host%
                -Dsdkman.consumer.key=%key%
                -Dsdkman.consumer.token=%token%
                -Dsdkman.candidate=kotlin
                -Dsdkman.version=%kotlin.version%
                -Dsdkman.url=https://github.com/JetBrains/kotlin/releases/download/%github.release.tag%/kotlin-compiler-%kotlin.version%.zip
            """.trimIndent()
            mavenVersion = bundled_3_2()
            userSettingsPath = ""
            jdkHome = "%env.JDK_18%"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Set version as default"
            goals = "io.sdkman:sdkman-maven-plugin:%sdkman-plugin-version%:default"
            runnerArgs = """
                -Dsdkman.api.host=%sdkman.host%
                -Dsdkman.consumer.key=%key%
                -Dsdkman.consumer.token=%token%
                -Dsdkman.candidate=kotlin
                -Dsdkman.default=%kotlin.version%
            """.trimIndent()
            mavenVersion = bundled_3_2()
            userSettingsPath = ""
            jdkHome = "%env.JDK_18%"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Announce on sdkman feed"
            goals = "io.sdkman:sdkman-maven-plugin:%sdkman-plugin-version%:announce"
            runnerArgs = """
                -Dsdkman.api.host=%sdkman.host%
                -Dsdkman.consumer.key=%key%
                -Dsdkman.consumer.token=%token%
                -Dsdkman.candidate=kotlin
                -Dsdkman.default=%kotlin.version%
                -Dsdkman.version=%kotlin.version%
                -Dsdkman.hashtag=kotlin
            """.trimIndent()
            mavenVersion = bundled_3_2()
            userSettingsPath = ""
            jdkHome = "%env.JDK_18%"
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
    }
})
