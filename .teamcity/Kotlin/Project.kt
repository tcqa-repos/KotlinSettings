package Kotlin

import Kotlin.buildTypes.*
import Kotlin.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "2fff74fc-c6cd-4588-9d0e-016630fe8b18"
    extId = "Kotlin"
    parentId = "_Root"
    name = "Kotlin"

    vcsRoot(Kotlin_KotlinGithubForGradleIntegrationTests)
    vcsRoot(Kotlin_KotlinNetbeans)
    vcsRoot(Kotlin___github)
    vcsRoot(Kotlin_KotlinGithub1)
    vcsRoot(Vcs_Kotlin_ts2kt)
    vcsRoot(Kotlin_M11_KotlinBuild)
    vcsRoot(Kotlin_HttpsGithubComJetBrainsKotlinGit105)
    vcsRoot(Kotlin_KotlinGithubGoodwinnkMaster)
    vcsRoot(Kotlin_KotlinGithub11)
    vcsRoot(Kotlin_YouTackBot)
    vcsRoot(Kotlin_SdkmanMavenPlugin)
    vcsRoot(Kotlin_HttpsGithubComJetBrainsKotlinGit1051)
    vcsRoot(Kotlin_KotlinEclipse)
    vcsRoot(Kotlin_KotlinGithub2)
    vcsRoot(kotlin_examples___GitHub)
    vcsRoot(Kotlin_KotlinGithubKotlinBuild1)
    vcsRoot(KAnnotator___GitHub)

    buildType(Kotlin_CompilerAndPluginUltimateTestsOnly)
    buildType(Kotlin_DeployMavenArtifactsNew)
    buildType(bt390)
    buildType(Kotlin_DeployToNpm)
    buildType(Kotlin_CompilerAndPluginBootstrapNoTests)
    buildType(Kotlin_AndroidIntegrationTests)
    buildType(Kotlin_SDKManEaps)
    buildType(Kotlin_ts2kt)
    buildType(bt384)
    buildType(Kotlin_NetBeansPlugin)
    buildType(Kotlin_CompilerAndPluginAndroidStudio22)
    buildType(Kotlin_CheckoutSettings)
    buildType(Kotlin_DeployToJetBrainsPluginRepo)
    buildType(Kotlin_YouTrackBot)
    buildType(Kotlin_SDKMan)
    buildType(Kotlin_EclipsePlugin)
    buildType(Kotlin_GradleIntegrationTests)
    buildType(Kotlin_DeployMavenArtifactsBootstraps)
    buildType(bt345)
    buildType(bt411)
    buildType(Kotlin_GradleIntegrationTests_Android)
    buildType(bt438)
    buildType(bt446)

    template(Kotlin_Based_Template)
    template(Kotlin_KotlinCompilerAndPlugin)

    features {
        versionedSettings {
            id = "PROJECT_EXT_2"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = "HttpsGithubComTcqaReposVersionedSettings"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
    }
})
