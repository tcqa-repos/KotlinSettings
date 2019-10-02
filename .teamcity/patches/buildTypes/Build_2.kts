package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, create a buildType with id = 'Build_2'
in the root project, and delete the patch script.
*/
create(DslContext.projectId, BuildType({
    id("Build_2")
    name = "Build"

    artifactRules = ".teamcity => artifacts.zip"

    vcs {
        root(RelativeId("HttpsGithubComTcqaReposMyProject"))
    }

    triggers {
        vcs {
        }
    }
}))

