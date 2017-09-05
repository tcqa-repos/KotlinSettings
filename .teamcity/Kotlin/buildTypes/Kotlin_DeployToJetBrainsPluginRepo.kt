package Kotlin.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script

object Kotlin_DeployToJetBrainsPluginRepo : BuildType({
    uuid = "a3365cb6-233f-481b-89a6-371bfca3ce25"
    extId = "Kotlin_DeployToJetBrainsPluginRepo"
    name = "Deploy master To Plugin Repo"

    buildNumberPattern = "%build.counter%(%dep.bt345.build.number%)"

    params {
        text("1.username", "", label = "username", description = "Your username on plugins.jetbrains.com", display = ParameterDisplay.PROMPT, allowEmpty = false)
        password("2.password", "zxx775d03cbe80d301b", label = "password", description = "Your password on plugins.jetbrains.com", display = ParameterDisplay.PROMPT)
        text("3.channel", "", label = "channel", display = ParameterDisplay.PROMPT, allowEmpty = true)
    }

    vcs {
        checkoutMode = CheckoutMode.ON_SERVER
    }

    steps {
        script {
            name = "POST binaries"
            scriptContent = """
                set -e -x -v
                curl -k -i \
                    -F userName=%1.username% \
                    -F password=%2.password% \
                    -F pluginId=6954 \
                    -F file=@binaries/kotlin-plugin-%dep.bt345.build.number%.zip \
                    -F channel=%3.channel% http://plugins.jetbrains.com/plugin/uploadPlugin
                
                curl -k -i \
                    -F userName=%1.username% \
                    -F password=%2.password% \
                    -F pluginId=7717 \
                    -F file=@binaries/kotlin-android-extensions-plugin-%dep.bt345.build.number%.zip \
                    -F channel=%3.channel% http://plugins.jetbrains.com/plugin/uploadPlugin
            """.trimIndent()
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt345) {
            snapshot {
                reuseBuilds = ReuseBuilds.ANY
            }

            artifacts {
                cleanDestination = true
                artifactRules = """
                    kotlin-plugin-*.zip=>binaries
                    kotlin-android-extensions-plugin-*.zip=>binaries
                """.trimIndent()
            }
        }
    }

    requirements {
        equals("teamcity.agent.jvm.os.name", "Linux")
    }
})
