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

    params {
        add {
            checkbox("param", "", display = ParameterDisplay.PROMPT,
                      checked = "true")
        }
    }

    vcs {

        check(showDependenciesChanges == false) {
            "Unexpected option value: showDependenciesChanges = $showDependenciesChanges"
        }
        showDependenciesChanges = true

        remove(RelativeId("HttpsGithubComTcqaReposMyProject"))
    }

    dependencies {
        add(RelativeId("Build_2")) {
            snapshot {
            }

            artifacts {
                buildRule = lastSuccessful("%teamcity.build.branch%")
                artifactRules = "*"
            }
        }

    }
}
