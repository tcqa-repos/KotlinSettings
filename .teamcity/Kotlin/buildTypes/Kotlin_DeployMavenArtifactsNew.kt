package Kotlin.buildTypes

import Kotlin.buildTypes.bt390
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_DeployMavenArtifactsNew : BuildType({
    uuid = "a23a9f10-57e5-4e5c-b046-9f6245e6b6ce"
    extId = "Kotlin_DeployMavenArtifactsNew"
    name = "Deploy Maven Artifacts (New)"

    allowExternalStatus = true
    buildNumberPattern = "%dep.bt345.build.number%"

    params {
        param("DeployVersion", "1.1-SNAPSHOT")
        param("jdk17.home", "%env.JDK_17%")
        param("jdk18.home", "%env.JDK_18%")
        password("kotlin.key", "zxx0ead0f89b53aab8ca9f047db82e8c26bf851ffcd3e40676b82a038547d52fa59d89b34ab97cd6f4d1cd63adcb95f07bf267654297055936036d9b89ac2691b3040bbf26f32a059327dc405e9a0fb546a1cec961ff5725bed837fb1a3dc060bd0730c7d93c554ed1fa39171c0fc4eb8a7ae6a4b00ef78c47d07ec923e43fb5d854c2451fd668a711e4b92071cc6934abf402db9a60f87205a86d74a874a25f4ba1c55c594a49dac290d0085d93370282b22834a5361e8d8e1d26395b148abf208883a4a47ab42fd06cdf45bac20a2383be992a5f8c1ea65706da52fd94889fad284fd93f073ebff65fe39fca8675fcd245b7f755aa693c465f1cc2aba1f1f4ac329613238a08a2548e73b71c124ad28dc8118e12264f4cb1dbe9cbc1898ed179206f0a8a0d1d6d7709965d89319e91f6f288a2276f0e8745630f485c0e37404f483a9044924617a37742da3244541f6e7902c46f8f6dc627c9f65b7f70fffa20c52860faf696c81570ab252abb7087d268abe32073c274cd0dbc61357403802b37299e8bd683a5954c4cdf11f54d64ae84da32b632f03d1658283a42a8a6c3a4a191de1e8e9b5c7490a9e6b5b5cf98dc93cc02e992889a5c28a871388d23ba3c332b3017099c6a6921049e4362fce7787b9317a8ffdbb86884d22535e341ee95f8a44a8a647acc32bd4ce2536b02244a33dc3e1395e4344e8f66a21df6035e4566aa700dc1813e4d3496a237a09db2fadb115df26bd96dd733fedb1cffc2eeba6d54a5feae122df70de1b3dd0d56758c484fc9007b82a2b3b3887aac7b4408b53dcc8ad1bc4a40dd7de76786093cfce02f58f2129b2ab3f60fc855d7794338ecf3cf477c2d3c6b787d562279029de44146dbd3e20cd0be144e6dca01e524d9d5638b24b13b9b1c268b3f56f68b791bdcf94e6e1f9189a05b0c14dd2d4f9e074f0c064f43385a476345263421a9d2badcaa294dbc7230ce62f11ee879154677ace5b5021489b4f0455c5348c696c21aefdafe05f47bc71b8e0bf2dbca847e0728ae15d75bd3afca4259d6ce63c658192f4f5af36062c71837eef91805b9584f793ec32e8e573213fb352a730d300290035bd15cfbe10f97936f32cb94fa9b9890a3595a9523ee5a2732a9df1150d4e676bdffbc6f381ada3bd8cdac70a4d33aef925b77250e8a220ec83ff0769b3a6414d5de806bf69dc18c4872562b0f2fa1000c5b972db903b85ea27da637bd37b922b03ee6972e6e09d8bd4a30442b3f3f47603c3f7d757163b06f7ffea441864f079cbb572ca6c094ecd9bdb96997dc065d2699826ed98217c5af240883494f1a1881c792f01d9dc99245df8de549632d1377c950e4734dd3bcd62e4f99ea6b5dfc89edb2f9325a643703c1eef260d3f8ee6aadb8e70a3b0f30565db83291775a79e2fde1b58416094c3f21eee2c1b14485a71fb5215648a4b7f5d6db2efbfa93556be9c2e67503bee91e0fcc9794edf6c8bbb1cb659bdba4ee228ab6a8022f97d167584b9fc789f794b4d72ef806f2053cfb27243fce690ae9b34096b253c18b3a77775d9f8c453fdbfbefce8504bdf75f7b6b98813133c22cdfa496866955d7a190ea2034fa192b3f428d312617905c5a906f2b521d48610e636f1d048914865b1d7ab1bcb1b0dda75e70697bbd336e4102c95e5721edec9e301d9373f2a6e8582709c5074fb6a57f78544296fc4e54f7d0228862a35520db70ddb8b68f6e4cd3a05abdd78418ff297c612eacf192e687a50cd3aece5f3e7ba6b066460b250210de132120e99a596e8fe313e7abab3d85bce0b55bdfdd9a2c4f04340c5a2ddd50ccf80c60b25194c9b9f6194fc8d504c47c1c57e95c553fe592f9a710eedaa31adc7fccd8471ed6f03b1b59ecfa502cc6b12e102a34508ed1acb7fde7d13700935455353fdca4b3e1a6a34747dfa48090e155a2fb4c12e7b64715632dc1cc8b6e53b08c02e8a22e056145829312f50e99e5dde1020e8d8ceb4af40e43b50f689c0702123842328113ba9f1b69083d18d6323f0213323f220abd50467b3c191b8f212b4d3b53d058aee883ad81311869e18266632a5c01c5f0d3968618e0f6f702e477e9ec9420a10ebd77951ae1a85ea52cfbb5da8c0a886406e53e63d6b986d186e152805084cece5bab2f9d465ecb60cd6b5d7e814b2248f6ed21c62de57b3145ff72cc0cb9d2ed4f78831e173a88631fb6f1976defa99d256ac50a409338e87cc603502b9334a42287075cf3e345b3fe46ce7141a051c2a69520544e9c4a3ff3267afafe64bb2535076531717de0060a63efa6c5415f33b01fac1b2a2d23db37b852a42871ff32840170cdec3d90ea1ec4c1ebe47c52320e029c2d13d7dbe68fb8151085aa7177790987868a195a11da72168057255dddb1affd45b168f5b754632f60f42a53dfa3e7974aca25bf715250f8e27fc194db7fd21b178ca133d00e8657c126915b63207f2eaa5d0e61d8f1352e862809e246211f2bf9c6b1cc8372b67ab23c7f317e4d7426a6429512f4bacdbb1a3452ea86da97a052452dd203b037c66ac067b2eb1d516386e0f89167ee214b4caf3aa57e9921d78958988830e87b3f2e48984c6a67eac9344de7ef66cdeb241341957918bc8f3825177b1dfadce9f5104e40cba163cc6dd3ed64dece09e2ec922528b01867aab6046d088a474cec61d92bc0f96ae5a67992b8630737b33c314bb85bdaf09a81c83f56d48efd00442292682576bc5363dac1bf632c552b0756e85ba595a5b169e641825213c4e98099038c89c54afe81eaa813f8ee008abcc40a41a5190b13ad7aa8cda17d3773552bccf4599a5db2325cfa8664c65ff5b79b4a63ea78f30b7a3c4034d478c3546980fbf8238a291692ce8a57a7201a0d8e839b36fa591bcadfddf16a2acb7d014934076de5314002d18ee294368710c0076b57662935ada87c4a59dadd4d03feca25bf8b9fad36eca96978317bf766e0103091a5a448a86ae1aa4f17e745d329b94ee997824e44eacacfe1699f3b0c9e27b23e840e0b75f88827f249f860e0ccf4d5214ba9711f04a6b784702c5d9f49cfc5693ae05b65b29013501803dce732cae9c3e3f5d73807f9bccd7db1de037249f966028e0aeef7fabaef50c20e98e98370117e154b3a0f467473ebd85773ed13d31f0931b5bdff7b641670d465c8a0bda2bea021cd4f5b5ee72cac5b5d0163d91e906e1517e448c69e1563cb369fcd0563a75b633bc753fae0d15706a080b8ad5d39e85303ddf177ab2a27a649b4a5bc6c4c89e59cbbfa7677608c02007cc66149037cdbe80e6607c3dede4e186f7237acd38f53ef86b85e799084ce82c8a84e3ec9f5f68dad6eec2acc8494458a6683e70895837d367e98552be9a5610ec3553922b429cdd8cc8c2525be017a83ef06b0442547c06f1ef11a3d473fc8d31f1561c403ba098acb6edf7a0cd4a39a791166b1c7a90317a8ead2aeaadff56d56cbccdfbf2bf00c6771495a0384a95b84e2bfad15d248bd821ea525d0e4e4df42c61f844022e73bd90b3f29f969d1f96b2ca04826db5d0cdda1bac8b13265db4ac45cc430f36067922e518588345fc61215ddc6089a0c37bed50dbd6268569d72776910d4521c0b368db8d9fa675f8ad58380920d3b50000f8922e5d158e75670aec9bbd93485cc54992f38409c9944a51eb37a1b4cf92c8376df1ad47b8254a75a36aed737c757ed699b936509234d78d541c0dbf71d0b41624e67cd213c21e18da96bfaf7d35e3ddbe6187be4fd3ba2ff7f2c0ad5413ecd46dd4304560997603704e9c7108770e97a29029f981e4ad32bb40e86b84cdff2b598a6be45a992c119a5f0806490ae04568fd5663346bdbb774ebc36ac26151b2171c12b844e50397181be5bcf76b8d29f24c5e27866f12164c5e240b097f41394d835c50b990cfa6b1b37fe3655bb5c80cfddc50466677ba27e882343a19d6853a1bf28ac0a0d78369864ecaf7fd9134994c9bf45973b53021a159e8c71515c7b0afbfd47ce59288817e0bcd9d2154ab75422d8f7c05d4b70e795d3313886198c620ef1136d3f877891e74ec649201c5e884854ad5fccd44dd0bdca9a4e704ad54a1dad0ee033b5b15f28dfaa24f7e0935d515c7abab55fdf15db56e22f3eb8bc4f267a3bf6717c2fdc214041d2a20d9c65c17713bf4ccd9c8f5f8952de2f3e5bf96639be481b7e966b6d2d86b9bf72869db2805a927e872feb57d28fc994f955e331d1035b8db5e8c2d167c42f87c02b54f0ae566c1ed42261b02f0f30b0cace6e97bbe53d306412636208f901c75fbe40fbda922896c328bdb8a99638b1ef9af45539f3e2e100619164fa11d32dc7bd4eb082bc2fc6d66707d2ddb6389dd9d0fd6d44b8b939dbbd7f52d15640f17dc95d0406cead7189ac83d9ff72e7d0384b97bc6a26b7f98298c3380371ebd502c6a09738e5cd018e5d25b3033d3eb4d01788f0af85d87390e1e4b50ba0440ac26d44e8b20e12581fea34dcc8e51494522c907dca0175714db0b90f5cc515142c5c1ffe316dbb41617d5dff001bcb7d73a8a193fb83f612a196ae2ea993927f394f87eb15ddd05b96c8350867e5aa93cfa8c0bc9e2d0563120474c9847fcaeebef55d25e19e55991f0bfcb61dd8415978eb6306bab35bd06690845b013d40eaa6d03ef21f1d05286eeb28bef3e348eeef170b30064a0be017898befd2c09f726468a0ea510a039b0ec971855f84378020ba77985b7c7a7a63e9c1790993ea003c72498662cf817acac198eb24e7731d6c876970b55bd2fa3c941d30d4946460be1c755bac79bc3390e6ebf0511aad7f79975f5bd0d4b2d8e1b815b9b7f4b65a16f5f02aab5786c3ba1f360b8b3e50d408d9f3caa783c13cd1bdf6f251a84138cf2d9b14e08dc8f990aa96692863b6abc5d466bcb530356c9ddc0058503e3557bbc3a79e2a980990fa71615af964023bbe3c528dddbd8cac0cde9f3cc237abb67ecfe20c54af4fd02058b256a6eb2138a5bd76953ed08cd265c5bf48f2915c244787f047dc1fa8e69e746d353099762aa7c621b96e1ddabac32df25601ba7576d915f2e2bbe031b96172baf8cf4e19e81db4ad447d8b319024b6fcf10f04ddf704059dc9682cb07fd4492d34aaa5ed8a65dc6bce61395741f", display = ParameterDisplay.HIDDEN)
        select("system.deploy-repo", "sonatype-nexus-snapshots",
                options = listOf("sonatype-nexus-snapshots-repo" to "sonatype-nexus-snapshots", "bintray-repo" to "bintray"))
        select("system.deploy-url", "http://oss.sonatype.org/service/local/staging/deploy/maven2/",
                options = listOf("sonatype-url (maven central)" to "http://oss.sonatype.org/service/local/staging/deploy/maven2/", "bintray-eap-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin", "bintray-eap-url (publish automatically)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin/;publish=1", "bintray-dev-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin", "bintray-eap-1.1-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap-1.1/kotlin"))
        password("system.kotlin.bintray.password", "zxx1b35d0c43c199eac801a3594a00e0550dbf2e62380451d0375d657c25ad36e38811fa87a51633ad9775d03cbe80d301b", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.bintray.user", "zxx529dbb75f4cf3ef14423ab58cd8f54b9", display = ParameterDisplay.HIDDEN)
        param("system.kotlin.key.name", "74CE0A0B")
        password("system.kotlin.key.passphrase", "zxxdb04e8c00fb461ee2530dce3d6213862", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.password", "zxx987d5e89bfbe850ad07ccff7723de6a420de8cffa99994164dfc5cdbc40e8d0b", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.user", "zxx8c0820aa1a2b34efb2a183b41935b6b2e503ec685476af67", display = ParameterDisplay.HIDDEN)
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/dist/kotlinc")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Check build"
            mode = antScript {
                content = """
                    <project name="AssertRightBuild">
                      <target name="check">
                        <echo message="Branch: %teamcity.build.vcs.branch.Kotlin_KotlinGithub2%"/>
                        <fail message="Do not remote run this build" if="build.is.personal" />
                    
                        <fail message="Builds to Maven Central should be published only from master and release branches">
                          <condition>
                            <and>
                            <equals arg1="%system.deploy-repo%" arg2="sonatype-nexus-snapshots"/>
                            <not>
                              <equals arg1="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" arg2="refs/heads/master"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="/bootstrap"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M13"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M14"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" substring="M15"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="beta\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="bintray"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="rc\d*${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="1.0.\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin_KotlinGithub2%" pattern="1.1-M\d+${'$'}"/>
                            </not>
                            </and>
                          </condition>
                        </fail>
                    
                        </target>
                    </project>
                """.trimIndent()
            }
            targets = "check"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx07d67df0d96aad3350e276915eed3f06863eb00272fa1d7f")
        }
        script {
            name = "Prepare gnupg"
            scriptContent = """
                cd libraries
                export HOME=${'$'}(pwd)
                rm -rf .gnupg
                cat >keyfile <<EOT
                %kotlin.key%
                EOT
                gpg --allow-secret-key-import --import keyfile
                rm -v keyfile
            """.trimIndent()
        }
        maven {
            name = "Set Version"
            goals = "versions:set"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-DnewVersion=%DeployVersion%"
            userSettingsSelection = "jb mirror"
            useOwnLocalRepo = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Libraries Deploy"
            goals = "deploy"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-Dinvoker.skip=true -DskipTests --activate-profiles noTest,sign-artifacts -e -X"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "userSettingsSelection:byPath"
            userSettingsPath = "%system.teamcity.build.checkoutDir%/libraries/maven-settings.xml"
            useOwnLocalRepo = true
            param("jvmArgs", "-Xmx986m -XX:MaxPermSize=350m")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "ilya.gorbunov@jetbrains.com")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx8c2a7eb4026210ed775d03cbe80d301b")
            param("target.jdk.home", "%env.JDK_16%")
            param("teamcity.build.workingDir", "libraries")
        }
        script {
            name = "Cleanup"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            scriptContent = """
                cd libraries
                rm -rf .gnupg
            """.trimIndent()
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildTypeExtId = bt390.extId
            successfulOnly = true
        }
        vcs {
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = "+:<default>"
            watchChangesInDependencies = true
        }
    }

    failureConditions {
        executionTimeoutMin = 90
        errorMessage = true
    }

    features {
        feature {
            type = "perfmon"
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt390) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                cleanDestination = true
                artifactRules = "** => dist"
            }
        }
        artifacts(Kotlin.buildTypes.bt345) {
            artifactRules = """
                kotlin-compiler*.zip!**=>dist_bk
                kotlin-compiler-javadoc.jar=>dist_bk
                kotlin-compiler-sources.jar=>dist_bk
                internal/native-platform-uberjar.jar=>dependencies
            """.trimIndent()
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        noLessThan("teamcity.agent.work.dir.freeSpaceMb", "500")
    }
})
