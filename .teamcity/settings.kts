import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.
asdfg
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

version = "2019.2"

project {

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    params {
        password("token", "credentialsJSON:72682f1f-468c-4b1b-9f0e-b26753de06ec")
    }

    vcs {
        root(AbsoluteId("Issues_HttpsGithubComTcqaReposCalculatorRefsHeadsMaster"))
    }

    features {
        pullRequests {
            vcsRootExtId = "Issues_HttpsGithubComTcqaReposCalculatorRefsHeadsMaster"
            provider = github {
                authType = token {
                    token = "credentialsJSON:e8227979-5f7f-4188-94b9-1ed53ce8299d"
                }
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }
})
