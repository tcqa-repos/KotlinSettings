package VSTest

import VSTest.buildTypes.*
import VSTest.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.Project

object Project : Project({
    uuid = "a0fbae37-31ad-4e0d-93fa-0704d79aeeaa"
    extId = "VSTest"
    parentId = "_Root"
    name = "VSTest"

    vcsRoot(VSTest_HttpsJuliaReshBitbucketOrgOrybakDotnetplaygroundGit)

    buildType(VSTest_Build)
})
