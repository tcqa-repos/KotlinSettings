import jetbrains.buildServer.configs.kotlin.v2019_2.*

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

version = "2020.1"

project {

    buildType(UseArtifacts)
    buildType(BuildWithArtifacts)
}

object BuildWithArtifacts : BuildType({
    name = "build with artifactss"

    artifactRules = "**/* => a.zip"

    vcs {
        root(AbsoluteId("Issues_HttpsGithubComTcqaReposReports"))

        checkoutMode = CheckoutMode.ON_SERVER
    }
})

object UseArtifacts : BuildType({
    name = "use artifacts"

    dependencies {
        artifacts(AbsoluteId("AnsiStyleColorCodesInBuildLog_Ant")) {
            id = "qwerty"
            buildRule = lastSuccessful()
            artifactRules = "*"
        }
        dependency(BuildWithArtifacts) {
            artifacts {
                id = "a"
                buildRule = lastSuccessful()
                artifactRules = "* => a"
            }
            artifacts {
                id = "b"
                buildRule = lastSuccessful()
                artifactRules = "* => b"
            }
        }
    }
})
