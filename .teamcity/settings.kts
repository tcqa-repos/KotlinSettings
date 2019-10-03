import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    vcsRoot(HttpsGithubComTcqaReposMyProject)
    vcsRoot(HttpsGithubComTcqaReposMyProject_2)

    buildType(Build_2)
    buildType(Build)
}

object Build : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        checkbox("param", "", display = ParameterDisplay.PROMPT,
                  checked = "true")
    }

    vcs {
        root(HttpsGithubComTcqaReposMyProject)

        showDependenciesChanges = true
    }

    dependencies {
        dependency(Build_2) {
            snapshot {
            }

            artifacts {
                buildRule = lastSuccessful("%teamcity.build.branch%")
                artifactRules = "*"
            }
        }
    }
})

object Build_2 : BuildType({
    name = "Build"

    artifactRules = ".teamcity => artifacts.zip"

    vcs {
        root(HttpsGithubComTcqaReposMyProject_2)
    }

    triggers {
        vcs {
            enabled = false
        }
    }
})

object HttpsGithubComTcqaReposMyProject : GitVcsRoot({
    name = "https://github.com/tcqa-repos/MyProject"
    url = "https://github.com/tcqa-repos/MyProject"
    branchSpec = "+:*"
})

object HttpsGithubComTcqaReposMyProject_2 : GitVcsRoot({
    name = "https://github.com/tcqa-repos/My.Project"
    url = "https://github.com/tcqa-repos/My.Project"
})
