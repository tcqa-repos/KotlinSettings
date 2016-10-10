package VSTest

import VSTest.buildTypes.MSTest_Build
import VSTest.buildTypes.VSTest_Build
import VSTest.vcsRoots.VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit
import jetbrains.buildServer.configs.kotlin.v10.Project

object Project : Project({
    uuid = "a0fbae37-31ad-4e0d-93fa-0704d79aeeaa"
    extId = "VSTest"
    parentId = "_Root"
    name = "VSTest"

    vcsRoot(VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit)

    buildType(VSTest_Build)
    buildType(MSTest_Build)
})
