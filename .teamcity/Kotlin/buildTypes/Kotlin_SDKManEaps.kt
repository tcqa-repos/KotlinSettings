package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven

object Kotlin_SDKManEaps : BuildType({
    uuid = "f70d27eb-9f09-4659-b16e-2bd975f11854"
    extId = "Kotlin_SDKManEaps"
    name = "SDKMan (eaps)"

    buildNumberPattern = "%build.counter% %kotlin.version%"

    params {
        param("github.release.tag", "build-%kotlin.version%")
        password("key", "zxx56f05899d0f1998fc9d4e21a7122c156945611e3990d0696d4d8bb0e913e20c5775d03cbe80d301b")
        text("kotlin.version", "1.1-M01", display = ParameterDisplay.PROMPT, allowEmpty = true)
        param("sdkman-plugin-version", "1.1-SNAPSHOT")
        param("sdkman.host", "vendors.sdkman.io")
        password("token", "zxxec9fac3a6689be98f81ec9c425569f93a11c318a258f2d80e0bb35edb2a994718a5f54f034180ae38c57e09edfd73886e03650dda3cfbc5a834da5fd8898078b775d03cbe80d301b")
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
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("target.jdk.home", "%env.JDK_18%")
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
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("target.jdk.home", "%env.JDK_18%")
        }
        maven {
            name = "Set version as default"
            enabled = false
            goals = "io.sdkman:sdkman-maven-plugin:%sdkman-plugin-version%:default"
            runnerArgs = """
                -Dsdkman.api.host=sdkman-vendor.herokuapp.com
                -Dsdkman.consumer.key=%key%
                -Dsdkman.consumer.token=%token%
                -Dsdkman.candidate=kotlin
                -Dsdkman.default=%kotlin.version%
            """.trimIndent()
            mavenVersion = bundled_3_2()
            userSettingsPath = ""
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("target.jdk.home", "%env.JDK_18%")
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
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("target.jdk.home", "%env.JDK_18%")
        }
    }
})
