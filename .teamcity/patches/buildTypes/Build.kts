package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    check(name == "Build") {
        "Unexpected name: '$name'"
    }
    name = "Deploy"

    check(enablePersonalBuilds == true) {
        "Unexpected option value: enablePersonalBuilds = $enablePersonalBuilds"
    }
    enablePersonalBuilds = false

    check(type == BuildTypeSettings.Type.REGULAR) {
        "Unexpected option value: type = $type"
    }
    type = BuildTypeSettings.Type.DEPLOYMENT

    check(maxRunningBuilds == 0) {
        "Unexpected option value: maxRunningBuilds = $maxRunningBuilds"
    }
    maxRunningBuilds = 1

    vcs {
        expectEntry(RelativeId("HttpsGithubComTcqaReposMyProject"))
        root(RelativeId("HttpsGithubComTcqaReposMyProject"), "+:test")
    }
}
