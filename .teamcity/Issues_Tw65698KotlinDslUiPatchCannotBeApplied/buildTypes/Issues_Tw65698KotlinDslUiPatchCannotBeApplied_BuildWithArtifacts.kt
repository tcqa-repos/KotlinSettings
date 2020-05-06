package Issues_Tw65698KotlinDslUiPatchCannotBeApplied.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Issues_Tw65698KotlinDslUiPatchCannotBeApplied_BuildWithArtifacts : BuildType({
    name = "build with artifactss"

    artifactRules = "**/* => a.zip"

    vcs {
        root(AbsoluteId("Issues_HttpsGithubComTcqaReposReports"))

        checkoutMode = CheckoutMode.ON_SERVER
    }
})
