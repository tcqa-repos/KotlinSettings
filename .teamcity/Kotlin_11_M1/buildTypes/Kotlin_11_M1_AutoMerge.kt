package Kotlin_11_M1.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.AutoMerge
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.AutoMerge.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.merge
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_11_M1_AutoMerge : BuildType({
    uuid = "067df8e2-28bc-4d2f-a7db-6ea2420d582a"
    extId = "Kotlin_11_M1_AutoMerge"
    name = "Auto Merge"
    description = "Merge with %teamcity.build.branch%"


    params {
        param("version.prefix", "0.13.1514")
    }

    vcs {
        root(Kotlin_11_M1.vcsRoots.Kotlin_11_M1_KotlinGithubMaster)

        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        ant {
            mode = antScript {
                content = """
                    <project name="Kotlin" default="dummy">
                      <target name="dummy">
                      </target>
                    </project>
                """.trimIndent()
            }
        }
    }

    triggers {
        vcs {
            branchFilter = "+:%release.tag.branch.name%"
        }
        finishBuildTrigger {
            buildTypeExtId = bt345.extId
            branchFilter = "+:%release.tag.branch.name%"
        }
    }

    features {
        merge {
            branchFilter = """
                +:refs/tags/build-*
                +:%release.tag.branch.name%
            """.trimIndent()
            destinationBranch = "%release.tag.branch.name%_/as22"
            commitMessage = "Automerge '%teamcity.build.branch%'"
            mergePolicy = AutoMerge.MergePolicy.FAST_FORWARD
        }
        merge {
            branchFilter = """
                +:refs/tags/build-*
                +:%release.tag.branch.name%
            """.trimIndent()
            destinationBranch = "%release.tag.branch.name%_/%CONST.release.branch.idea.suffix.br143%"
            commitMessage = "Automerge '%teamcity.build.branch%'"
            mergePolicy = AutoMerge.MergePolicy.FAST_FORWARD
        }
    }
})
