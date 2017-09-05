package Kotlin.buildTypes

import Kotlin.buildTypes.bt345
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Trigger
import jetbrains.buildServer.configs.kotlin.v10.Trigger.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule

object Kotlin_DeployToNpm : BuildType({
    uuid = "d88daa92-18be-41d4-b282-b97feadb09e2"
    extId = "Kotlin_DeployToNpm"
    name = "Deploy to npm"
    paused = true

    buildNumberPattern = "%build.counter%-%dep.bt345.build.number%"

    params {
        param("system.kotlin.compiler.deploy.version", "0.0.0")
        param("system.kotlin.deploy.version", "%dep.bt345.build.number%")
        password("system.kotlin.npmjs.auth.token", "zxx82352b8ce01877a2b9859ab8dc8c55f453aceab7629d32e63ea7fdb88acbc59cd06abf4f6bb644fa", label = "kotlin.npmjs.auth.token")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

    }

    steps {
        ant {
            name = "download-nodejs-and-npm"
            mode = antFile {
                path = "node_utils.xml"
            }
            targets = "download-nodejs-and-npm"
        }
        ant {
            name = "unzip-jslib.jar"
            mode = antFile {
                path = "node_utils.xml"
            }
            targets = "unzip-jslib.jar"
        }
        ant {
            name = "publish-kotlin-js-to-npm"
            mode = antFile {
                path = "node_utils.xml"
            }
            targets = "publish-kotlin-js-to-npm"
        }
        ant {
            name = "publish-kotlin-compiler-to-npm"
            enabled = false
            mode = antFile {
                path = "node_utils.xml"
            }
            targets = "publish-kotlin-compiler-to-npm"
        }
    }

    triggers {
        trigger {
            type = "urlBuildTrigger"
            enabled = false
            param("url.build.trigger.url", "https://teamcity.jetbrains.com/guestAuth/repository/download/bt345/bootstrap.tcbuildtag/updatePlugins.xml")
        }
        schedule {
            schedulingPolicy = cron {
                minutes = "*/3"
            }
            triggerBuild = onWatchedBuildChange {
                buildType = bt345.extId
                watchedBuildRule = ScheduleTrigger.WatchedBuildRule.TAG
                watchedBuildTag = "bootstrap"
            }
            withPendingChangesOnly = false
            param("branchFilter", "+:<default>")
            param("dayOfWeek", "Sunday")
        }
    }

    dependencies {
        artifacts(Kotlin.buildTypes.bt345) {
            buildRule = tag("bootstrap")
            artifactRules = "kotlin-compiler-*.zip!/kotlinc/lib/kotlin-stdlib-js.jar=>%teamcity.build.checkoutDir%/dist/kotlinc/lib"
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
    }
})
