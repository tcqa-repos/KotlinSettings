package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Build'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Build")) {
    params {
        remove {
            password("token", "credentialsJSON:72682f1f-468c-4b1b-9f0e-b26753de06ec")
        }
        add {
            password("token2", "zxx7afcd44dcb03c6e4fa98ac935deb1e2d3399972361ec799f043252a8d133e9e9cfde454640af56f3775d03cbe80d301b")
        }
    }

    features {
        val feature1 = find<PullRequests> {
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
        feature1.apply {
            provider = github {
                serverUrl = ""
                authType = token {
                    token = "zxxd6fe2decc9a937bf775d03cbe80d301b"
                }
                filterTargetBranch = ""
                filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER
            }
        }
    }
}
