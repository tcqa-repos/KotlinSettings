package Issues_Tw65698KotlinDslUiPatchCannotBeApplied

import Issues_Tw65698KotlinDslUiPatchCannotBeApplied.buildTypes.*
import Issues_Tw65698KotlinDslUiPatchCannotBeApplied.vcsRoots.*
import Issues_Tw65698KotlinDslUiPatchCannotBeApplied.vcsRoots.Issues_Tw65698KotlinDslUiPatchCannotBeApplied_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.versionedSettings

object Project : Project({
    id("Issues_Tw65698KotlinDslUiPatchCannotBeApplied")
    parentId("Issues")
    name = "TW-65698 Kotlin DSL UI patch cannot be applied"

    vcsRoot(Issues_Tw65698KotlinDslUiPatchCannotBeApplied_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster)

    buildType(Issues_Tw65698KotlinDslUiPatchCannotBeApplied_UseArtifacts)
    buildType(Issues_Tw65698KotlinDslUiPatchCannotBeApplied_BuildWithArtifacts)

    features {
        versionedSettings {
            id = "PROJECT_EXT_1"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = "${Issues_Tw65698KotlinDslUiPatchCannotBeApplied_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
