package VSTest.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.VSTestStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.VSTestStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.vstest

object VSTest_Build : BuildType({
    uuid = "961da43c-96c4-42bb-a752-61169ded38e0"
    extId = "VSTest_Build"
    name = "Build"

    vcs {
        root(VSTest.vcsRoots.VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit)

    }

    steps {
        step {
            type = "VS.Solution"
            param("build-file-path", "DotNetSampleProject.sln")
            param("msbuild.prop.Configuration", "Debug")
            param("msbuild_version", "12.0")
            param("run-platform", "x86")
            param("toolsVersion", "12.0")
            param("vs.version", "vs2013")
        }
        vstest {
            vstestPath = "%teamcity.dotnet.vstest.14.0%"
            includeTestFileNames = "**/bin/debug/*.dll"
        }
    }
})
