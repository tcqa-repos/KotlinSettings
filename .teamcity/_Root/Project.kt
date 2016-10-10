package _Root

import _Root.vcsRoots.*
import _Root.vcsRoots.HttpsGithubComTcqaReposKotlinSettings
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.VersionedSettings.*
import jetbrains.buildServer.configs.kotlin.v10.projectFeatures.versionedSettings

object Project : Project({
    uuid = "7cca9528-b322-416a-b8bb-34aa8904d6b0"
    extId = "_Root"
    name = "<Root project>"
    description = "Contains all other projects"

    vcsRoot(HttpsGithubComTcqaReposKotlinSettings)

    features {
        versionedSettings {
            id = "PROJECT_EXT_1"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = HttpsGithubComTcqaReposKotlinSettings.extId
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
        }
    }

    cleanup {
        preventDependencyCleanup = false
    }
})
