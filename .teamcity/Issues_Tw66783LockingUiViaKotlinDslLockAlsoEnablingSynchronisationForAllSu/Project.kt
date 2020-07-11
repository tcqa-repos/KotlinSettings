package Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu

import Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu.vcsRoots.*
import Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu.vcsRoots.Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.VersionedSettings
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.versionedSettings

object Project : Project({
    id("Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu")
    parentId("Issues")
    name = "TW-66783 Locking UI via Kotlin DSL lock also enabling synchronisation for all su"

    vcsRoot(Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster)

    features {
        versionedSettings {
            id = "PROJECT_EXT_1"
            mode = VersionedSettings.Mode.ENABLED
            buildSettingsMode = VersionedSettings.BuildSettingsMode.USE_CURRENT_SETTINGS
            rootExtId = "${Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_HttpsGithubComTcqaReposKotlinSettingsRefsHeadsMaster.id}"
            showChanges = false
            settingsFormat = VersionedSettings.Format.KOTLIN
            storeSecureParamsOutsideOfVcs = true
        }
    }
})
