package Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1

import Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1")
    parentId("Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu")
    name = "SubProject1"

    buildType(Issues_Tw66783LockingUiViaKotlinDslLockAlsoEnablingSynchronisationForAllSu_SubProject1_Build)
})
