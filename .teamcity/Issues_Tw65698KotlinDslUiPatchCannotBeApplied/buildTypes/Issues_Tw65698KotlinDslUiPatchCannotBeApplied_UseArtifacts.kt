package Issues_Tw65698KotlinDslUiPatchCannotBeApplied.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Issues_Tw65698KotlinDslUiPatchCannotBeApplied_UseArtifacts : BuildType({
    name = "use artifacts"

    dependencies {
        artifacts(AbsoluteId("AnsiStyleColorCodesInBuildLog_Ant")) {
            id = "qwe"
            buildRule = lastSuccessful()
            artifactRules = "*"
        }
        dependency(Issues_Tw65698KotlinDslUiPatchCannotBeApplied_BuildWithArtifacts) {
            artifacts {
                id = "a"
                buildRule = lastSuccessful()
                artifactRules = "* => a"
            }
            artifacts {
                id = "b"
                buildRule = tag("qwe")
                artifactRules = "* => qwe"
            }
        }
    }
})
