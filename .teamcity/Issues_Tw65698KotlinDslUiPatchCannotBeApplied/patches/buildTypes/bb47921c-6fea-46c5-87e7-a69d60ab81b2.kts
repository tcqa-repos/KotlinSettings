package Issues_Tw65698KotlinDslUiPatchCannotBeApplied.patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Issues_Tw65698KotlinDslUiPatchCannotBeApplied_UseArtifacts'
accordingly, and delete the patch script.
*/
changeBuildType(AbsoluteId("Issues_Tw65698KotlinDslUiPatchCannotBeApplied_UseArtifacts")) {
    dependencies {
        expect(AbsoluteId("Issues_Tw65698KotlinDslUiPatchCannotBeApplied_BuildWithArtifacts")) {
            artifacts {
                buildRule = lastSuccessful()
                artifactRules = "* => a"
            }
            artifacts {
                buildRule = tag("qwe")
                artifactRules = "* => qwe"
            }
        }
        update(AbsoluteId("Issues_Tw65698KotlinDslUiPatchCannotBeApplied_BuildWithArtifacts")) {
            artifacts {
                buildRule = lastSuccessful()
                cleanDestination = true
                artifactRules = "* => a"
            }
            artifacts {
                buildRule = lastPinned()
                cleanDestination = true
                artifactRules = "* => qwe"
            }
        }

    }
}
