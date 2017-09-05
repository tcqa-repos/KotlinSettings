package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.ScheduleTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.schedule

object Kotlin_YouTrackBot : BuildType({
    uuid = "b37f3bac-110b-4a4b-b747-9d756719199a"
    extId = "Kotlin_YouTrackBot"
    name = "YouTrack Bot"


    params {
        param("env.slack.settings", """"channel": "#kotlin-standup", "link_names": 1""")
        param("env.slack.url", "https://hooks.slack.com/services/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")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_YouTackBot)

        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        step {
            type = "kotlinc"
            executionMode = BuildStep.ExecutionMode.DEFAULT
            param("KOTLIN_TAG", "bootstrap")
        }
        script {
            name = "Collect Issues"
            scriptContent = """curl https://youtrack.jetbrains.com/rest/issue/byproject/KT?filter=State:Submitted\&max=1000 --silent > issues.xml"""
        }
        ideaRunner {
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%teamcity.build.checkoutDir%/.kotlin/Kotlin/")
            }
            jvmArgs = "-Xmx256m"
            runConfigurations = "org.jetbrains.youtrack.bot.RunnerKt"
        }
    }

    triggers {
        schedule {
            schedulingPolicy = weekly {
                dayOfWeek = ScheduleTrigger.DAY.Thursday
                hour = 16
                minute = 30
            }
            triggerBuild = always()
            withPendingChangesOnly = false
            param("revisionRule", "lastFinished")
        }
        schedule {
            schedulingPolicy = weekly {
                dayOfWeek = ScheduleTrigger.DAY.Monday
                hour = 16
                minute = 30
            }
            triggerBuild = always()
            withPendingChangesOnly = false
            param("revisionRule", "lastFinished")
        }
    }

    requirements {
        contains("system.agent.name", "ubuntu")
    }
})
