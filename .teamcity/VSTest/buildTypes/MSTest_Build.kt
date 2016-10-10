package VSTest.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.BuildStep
import jetbrains.buildServer.configs.kotlin.v10.BuildType
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.mstest

object MSTest_Build : BuildType({
    uuid = "961da43c-96c4-42bb-a752-61169ded38e1"
    extId = "MSTest_Build"
    name = "Build_2"

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
        mstest {
            name = "test"
            executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
            mstestPath = "%teamcity.dotnet.mstest.10.0%"
            includeTestFileNames = "**/bin/**/*Tests*.dll"
        }
    }
})
