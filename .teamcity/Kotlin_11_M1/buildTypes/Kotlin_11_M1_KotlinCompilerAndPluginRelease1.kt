package Kotlin_11_M1.buildTypes

import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner
import jetbrains.buildServer.configs.kotlin.v10.IdeaRunner.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.FreeDiskSpace.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.VcsLabeling.*
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.freeDiskSpace
import jetbrains.buildServer.configs.kotlin.v10.buildFeatures.vcsLabeling
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.ideaRunner
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object Kotlin_11_M1_KotlinCompilerAndPluginRelease1 : Template({
    uuid = "4488ba20-a839-49a1-a3bd-4e9de120af72"
    extId = "Kotlin_11_M1_KotlinCompilerAndPluginRelease1"
    name = "Kotlin Compiler and Plugin - Release (1)"

    artifactRules = """
        out/artifacts
        out/artifacts/InjectorGenerator/injector-generator.jar
        dist/kotlin-compiler-*.zip
        dist/kotlin-compiler-javadoc.jar
        dist/kotlin-compiler-sources.jar
        dist/kotlin-compiler-for-maven.jar
        dist/kotlin-for-upsource.jar
        out/*.hprof
    """.trimIndent()
    buildNumberPattern = "%kotlin.build.number%"

    params {
        param("kotlin.ant.home", "%system.agent.home.dir%/plugins/ant")
        text("kotlin.bootstrap.home", "%system.kotlin.bootstrap.plugin.dir%/Kotlin/kotlinc", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        param("kotlin.build.number", "%dep.bt345.build.number%-%version.idea.readable.name%-%build.counter%")
        param("kotlin.jdk18", "%env.JDK_16%")
        param("kotlin.ultimate.dir", "ultimate")
        param("kotlin.ultimate.dummy.artifact", "Dummy")
        param("kotlin.ultimate.tests.run.configuration", "Ultimate Plugin Tests")
        param("release.branch.idea.suffix", "br143")
        param("system.ideaVersion", "142.4465.2")
        text("system.jps.kotlin.extra.annotation.paths", "%kotlin.bootstrap.home%/lib/kotlin-jdk-annotations.jar", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        text("system.kotlin.bootstrap.plugin.dir", "%teamcity.build.checkoutDir%/dependencies/bootstrap-compiler", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        text("system.path.macro.KOTLIN.BUNDLED", "%kotlin.bootstrap.home%", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        text("teamcity.ideaRunner.additional.lib.path", "%kotlin.bootstrap.home%/../lib/jps;%kotlin.bootstrap.home%/../lib/kotlin-runtime.jar;%kotlin.bootstrap.home%/../lib/kotlin-plugin.jar", display = ParameterDisplay.HIDDEN, allowEmpty = true)
        param("update.dependencies.action", "update")
        param("version.idea.readable.name", "Idea142")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_M11_KotlinBuild)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Fetch dependencies"
            id = "RUNNER_860"
            mode = antFile {
                path = "update_dependencies.xml"
            }
            targets = "%update.dependencies.action%"
            jdkHome = "%env.JDK_16%"
            param("build-file", """
                <!-- No Rest API temporary workaround -->
                
                <project name="Update Dependencies" default="update">
                    <property name="ideaVersion" value="139.1116"/>
                
                    <condition property="os.tag" value="win.zip">
                        <os family="windows"/>
                    </condition>
                
                    <condition property="os.tag" value="mac.zip">
                        <os family="mac"/>
                    </condition>
                
                    <condition property="os.tag" value="tar.gz">
                        <and>
                            <os family="unix"/>
                            <not>
                                <os family="mac"/>
                            </not>
                        </and>
                    </condition>
                
                    <property name="idea.sdk.fetch.needed" value="true"/>
                
                    <target name="update" depends="fetch-third-party,fetch-annotations" description="Update dependencies from public server">
                        <execute_update_with_id_resolve
                            teamcity.server.url="https://teamcity.jetbrains.com"
                            build.locator.request="buildType:bt410,status:SUCCESS,branch:idea/${'$'}{ideaVersion}"/>
                    </target>
                
                    <target name="jb_update" depends="fetch-third-party,fetch-annotations" description="Update dependencies from internal server">
                        <execute_update_with_id_resolve
                            teamcity.server.url="http://buildserver.labs.intellij.net"
                            build.locator.request="buildType:bt3498,status:SUCCESS,branch:/idea/${'$'}{ideaVersion}"/>
                    </target>
                
                    <target name="jb_update_continuous_139" depends="fetch-third-party,fetch-annotations">
                        <execute_update base.url="http://buildserver.labs.intellij.net/guestAuth/app/rest/builds/buildType:ijplatform_IjPlatformMaster_IdeaTrunk_CommunityDist,status:SUCCESS"/>
                    </target>
                
                    <target name="jb_update_continuous_140" depends="fetch-third-party,fetch-annotations">
                        <execute_update base.url="http://buildserver.labs.intellij.net/guestAuth/app/rest/builds/buildType:bt662,status:SUCCESS"/>
                    </target>
                
                    <macrodef name="get-maven-library">
                        <attribute name="prefix"/>
                        <attribute name="lib"/>
                        <attribute name="version"/>
                        <attribute name="bin" default="true"/>
                        <attribute name="src" default="true"/>
                        <attribute name="server" default="http://repository.jetbrains.com/remote-repos"/>
                        <attribute name="jar.name.base" default="@{lib}-@{version}"/>
                        <attribute name="target.jar.name.base" default="@{jar.name.base}"/>
                        <attribute name="path" default="@{prefix}/@{lib}/@{version}/@{jar.name.base}"/>
                        <attribute name="download" default="dependencies/download"/>
                        <attribute name="dependencies" default="dependencies"/>
                        <sequential>
                            <taskdef resource="net/sf/antcontrib/antcontrib.properties" classpath="${'$'}{basedir}/dependencies/ant-contrib.jar"/>
                            <if>
                                <istrue value="@{bin}"/>
                                <then>
                                    <get src="@{server}/@{path}.jar" dest="@{download}/@{jar.name.base}.jar" usetimestamp="true"/>
                                    <copy file="@{download}/@{jar.name.base}.jar" tofile="@{dependencies}/@{target.jar.name.base}.jar" overwrite="true"/>
                                </then>
                            </if>
                
                            <if>
                                <istrue value="@{src}"/>
                                <then>
                                    <get src="@{server}/@{path}-sources.jar" dest="@{download}/@{jar.name.base}-sources.jar" usetimestamp="true"/>
                                    <copy file="@{download}/@{jar.name.base}-sources.jar" tofile="@{dependencies}/@{target.jar.name.base}-sources.jar"
                                          overwrite="true"/>
                                </then>
                            </if>
                        </sequential>
                    </macrodef>
                
                    <macrodef name="get-ant-library">
                        <attribute name="version"/>
                        <attribute name="folderName"/>
                        <sequential>
                            <get src="http://archive.apache.org/dist/ant/binaries/apache-ant-@{version}-bin.tar.gz"
                                 dest="dependencies/download/apache-ant-@{version}-bin.tar.gz" usetimestamp="true"/>
                
                            <get src="http://archive.apache.org/dist/ant/source/apache-ant-@{version}-src.zip"
                                 dest="dependencies/apache-ant-@{version}-src.zip" usetimestamp="true"/>
                
                            <delete dir="dependencies/@{folderName}" failonerror="false"/>
                            <untar src="dependencies/download/apache-ant-@{version}-bin.tar.gz" dest="dependencies" compression="gzip"/>
                            <move file="dependencies/apache-ant-@{version}" tofile="dependencies/@{folderName}"/>
                        </sequential>
                    </macrodef>
                
                    <target name="fetch-third-party">
                        <mkdir dir="dependencies"/>
                        <mkdir dir="dependencies/download"/>
                
                        <!-- ProGuard -->
                        <get src="http://downloads.sourceforge.net/project/proguard/proguard/5.1/proguard5.1.zip"
                             dest="dependencies/download/proguard5.1.zip" usetimestamp="true"/>
                
                        <delete file="dependencies/proguard.jar" failonerror="false"/>
                        <unzip src="dependencies/download/proguard5.1.zip" dest="dependencies">
                            <patternset>
                                <include name="proguard5.1/lib/proguard.jar"/>
                            </patternset>
                            <mapper type="flatten"/>
                        </unzip>
                
                        <!-- ant contrib -->
                        <get src="http://heanet.dl.sourceforge.net/project/ant-contrib/ant-contrib/1.0b3/ant-contrib-1.0b3-bin.zip"
                             dest="dependencies/download/ant-contrib-1.0b3-bin.zip" usetimestamp="true"/>
                
                        <delete file="dependencies/ant-contrib.jar" failonerror="false"/>
                        <unzip src="dependencies/download/ant-contrib-1.0b3-bin.zip" dest="dependencies">
                            <patternset>
                                <include name="ant-contrib/ant-contrib-1.0b3.jar"/>
                            </patternset>
                            <mapper type="merge" to="ant-contrib.jar"/>
                        </unzip>
                
                        <!-- JarJar -->
                        <get src="http://jarjar.googlecode.com/files/jarjar-1.2.jar" dest="dependencies/download/jarjar-1.2.jar" usetimestamp="true"/>
                        <copy file="dependencies/download/jarjar-1.2.jar" tofile="dependencies/jarjar.jar" overwrite="true"/>
                
                        <!-- ant 1.7.0, 1.8.0 -->
                        <get-ant-library version="1.7.0" folderName="ant-1.7"/>
                        <get-ant-library version="1.8.0" folderName="ant-1.8"/>
                
                        <!-- dx.jar -->
                        <property name="android-build-tools.zip" value="build-tools_r21.1.1-linux.zip"/>
                        <get
                            src="https://dl-ssl.google.com/android/repository/${'$'}{android-build-tools.zip}"
                            dest="dependencies/download/${'$'}{android-build-tools.zip}"
                            usetimestamp="true"/>
                        <unzip src="dependencies/download/${'$'}{android-build-tools.zip}" dest="dependencies"/>
                
                        <property name="android-sources.tgz" value="dx.tar.gz"/>
                        <get
                            src="https://android.googlesource.com/platform/dalvik/+archive/android-5.0.0_r2/${'$'}{android-sources.tgz}"
                            dest="dependencies/download/${'$'}{android-sources.tgz}"
                            usetimestamp="true"/>
                        <delete dir="dependencies/dx-src" failonerror="false"/>
                        <untar src="dependencies/download/${'$'}{android-sources.tgz}" dest="dependencies/dx-src" compression="gzip"/>
                
                        <!-- jflex 1.4 -->
                        <mkdir dir="dependencies/jflex"/>
                        <get src="https://raw.github.com/JetBrains/intellij-community/master/tools/lexer/jflex-1.4/lib/JFlex.jar"
                             dest="dependencies/jflex/JFlex.jar" usetimestamp="true"/>
                        <get src="https://raw.github.com/JetBrains/intellij-community/master/tools/lexer/idea-flex.skeleton"
                             dest="dependencies/jflex/idea-flex.skeleton" usetimestamp="true"/>
                
                        <!-- jline -->
                        <get-maven-library prefix="jline" lib="jline" version="2.9" target.jar.name.base="jline"/>
                
                        <!-- jansi -->
                        <!--
                        <get-maven-library prefix="org/fusesource/jansi" lib="jansi" version="1.9"/>
                        -->
                
                        <!-- Guava 17 sources-->
                        <get-maven-library prefix="com/google/guava" lib="guava" version="17.0" bin="false"/>
                
                        <!-- ASM -->
                        <get src="https://raw.github.com/JetBrains/intellij-community/master/lib/src/asm5-src.zip"
                             dest="dependencies/asm5-src.zip"/>
                        <!-- <get-asm-sources-and-rename-packages asm.version="5.0.1"/> -->
                
                        <!-- Junit Sources -->
                        <get-maven-library prefix="junit" lib="junit" version="4.11" bin="false"/>
                        <get-maven-library prefix="org/hamcrest" lib="hamcrest-core" version="1.3" bin="false"/>
                
                        <!-- Protocol Buffers -->
                        <get-maven-library prefix="com/google/protobuf" lib="protobuf-java" version="2.5.0" bin="false"/>
                
                        <!-- CLI Parser -->
                        <get-maven-library prefix="com/github/spullara/cli-parser" lib="cli-parser" version="1.1.1"/>
                
                        <!-- Closure Compiler -->
                        <!-- A download url taken from http://code.google.com/p/closure-compiler/wiki/BinaryDownloads -->
                        <get src="http://dl.google.com/closure-compiler/compiler-20131014.zip"
                             dest="dependencies/download/closure-compiler.zip" usetimestamp="true"/>
                
                        <delete file="dependencies/closure-compiler.jar" failonerror="false"/>
                        <unzip src="dependencies/download/closure-compiler.zip" dest="dependencies">
                            <patternset>
                                <include name="compiler.jar"/>
                            </patternset>
                            <mapper type="merge" to="closure-compiler.jar"/>
                        </unzip>
                
                        <delete file="dependencies/android.jar" failonerror="false"/>
                        <get src="http://dl-ssl.google.com/android/repository/android-19_r02.zip"
                             dest="dependencies/download/android-sdk.zip" usetimestamp="true"/>
                        <unzip src="dependencies/download/android-sdk.zip" dest="dependencies">
                            <patternset>
                                <include name="**/android.jar"/>
                            </patternset>
                            <mapper type="flatten"/>
                        </unzip>
                
                        <!-- Bootstrap compiler -->
                        <get src="https://teamcity.jetbrains.com/guestAuth/repository/download/bt345/bootstrap.tcbuildtag/kotlin-plugin-{build.number}.zip"
                             dest="dependencies/download/bootstrap-compiler.zip" usetimestamp="true"/>
                        <delete dir="dependencies/bootstrap-compiler" failonerror="false"/>
                        <unzip src="dependencies/download/bootstrap-compiler.zip" dest="dependencies/bootstrap-compiler"/>
                        <taskdef resource="net/sf/antcontrib/antcontrib.properties" classpath="${'$'}{basedir}/dependencies/ant-contrib.jar"/>
                        <if>
                            <matches pattern="mac\.zip|tar\.gz" string="${'$'}{os.tag}"/>
                            <then>
                                <!-- Java can't manipulate permissions -->
                                <exec executable="find">
                                    <arg value="dependencies/bootstrap-compiler/Kotlin/kotlinc/bin"/>
                                    <arg line="-name 'kotlin*' ! -name '*.bat' -exec chmod a+x '{}' ;"/>
                                </exec>
                            </then>
                        </if>
                    </target>
                
                    <macrodef name="get-asm-sources-and-rename-packages">
                        <attribute name="asm.version"/>
                        <sequential>
                            <!-- Download ASM sources -->
                            <get-maven-library prefix="org/ow2/asm" lib="asm-debug-all" version="@{asm.version}" bin="false"/>
                
                            <!-- Rename packages in the sources -->
                            <delete dir="dependencies/download/asm-src" failonerror="false"/>
                            <unzip src="dependencies/download/asm-debug-all-@{asm.version}-sources.jar" dest="dependencies/download/asm-src">
                                <patternset>
                                    <include name="**/*"/>
                                </patternset>
                            </unzip>
                
                            <replaceregexp match="org\.objectweb\.asm" replace="org.jetbrains.org.objectweb.asm" flags="g">
                                <fileset dir="dependencies/download/asm-src/">
                                    <include name="**/*.java"/>
                                </fileset>
                            </replaceregexp>
                
                            <move file="dependencies/download/asm-src/org/objectweb/asm"
                                  tofile="dependencies/download/asm-src/org/jetbrains/org/objectweb/asm"/>
                
                            <zip destfile="dependencies/jetbrains-asm-all-@{asm.version}-src.zip" basedir="dependencies/download/asm-src"/>
                        </sequential>
                    </macrodef>
                
                    <macrodef name="execute_update_with_id_resolve">
                        <attribute name="teamcity.server.url"/>
                        <attribute name="build.locator.request"/>
                
                        <sequential>
                            <!--
                            <loadresource property="execute.build.id">
                                <url url="@{teamcity.server.url}/guestAuth/app/rest/builds/?locator=@{build.locator.request}"/>
                                <filterchain>
                                    <tokenfilter>
                                        <filetokenizer/>
                                        <replaceregex pattern="^(.*)\sid=&quot;(\d+)&quot;(.*)${'$'}" replace="\2" flags="s"/>
                                    </tokenfilter>
                                </filterchain>
                            </loadresource>
                
                            <echo message="IDEA build id: ${'$'}{execute.build.id}"/>
                            -->
                
                            <execute_update base.url="@{teamcity.server.url}/guestAuth/app/rest/builds/id:${'$'}{execute.build.id}"/>
                        </sequential>
                    </macrodef>
                
                    <macrodef name="execute_update">
                        <attribute name="base.url"/>
                
                        <sequential>
                            <taskdef resource="net/sf/antcontrib/antcontrib.properties" classpath="${'$'}{basedir}/dependencies/ant-contrib.jar"/>
                
                            <!--
                            <loadresource property="idea.build.number">
                                <url url="@{base.url}/artifacts/children"/>
                                <filterchain>
                                    <tokenfilter>
                                        <filetokenizer/>
                
                                        <replaceregex pattern="^(.*)ideaIC-([\w\.]+)\.win\.zip(.*)${'$'}" replace="\2" flags="s"/>
                                    </tokenfilter>
                                </filterchain>
                            </loadresource>
                            -->
                
                            <property name="idea.build.number" value="139.SNAPSHOT" />
                            <property name="idea.archive.name" value="ideaIC-${'$'}{idea.build.number}.${'$'}{os.tag}"/>
                
                            <echo message="IDEA build number: ${'$'}{idea.build.number}"/>
                            <echo message="IDEA archive file: ${'$'}{idea.archive.name}"/>
                
                            <property name="content.base.url" value="https://teamcity.jetbrains.com/guestAuth/repository/download/bt410/383328:id"/>
                
                            <property name="core" value="ideaSDK/core"/>
                            <property name="core-analysis" value="ideaSDK/core-analysis"/>
                            <property name="jps" value="ideaSDK/jps"/>
                            <property name="jps-test" value="${'$'}{jps}/test"/>
                
                            <if>
                                <istrue value="${'$'}{idea.sdk.fetch.needed}"/>
                
                                <then>
                                    <delete dir="ideaSDK" failonerror="false">
                                        <exclude name="config-idea/**"/>
                                        <exclude name="system-idea/**"/>
                                    </delete>
                
                                    <mkdir dir="${'$'}{core}"/>
                                    <mkdir dir="${'$'}{core-analysis}"/>
                                    <mkdir dir="${'$'}{jps}"/>
                                    <mkdir dir="${'$'}{jps-test}"/>
                                    <get src="${'$'}{content.base.url}/core/intellij-core.jar" dest="${'$'}{core}/intellij-core.jar" usetimestamp="true"/>
                                    <get src="${'$'}{content.base.url}/core/intellij-core-analysis.jar" dest="${'$'}{core-analysis}/intellij-core-analysis.jar"
                                         usetimestamp="true"/>
                                    <get src="${'$'}{content.base.url}/core/annotations.jar" dest="${'$'}{core}/annotations.jar" usetimestamp="true"/>
                                    <get src="${'$'}{content.base.url}/core/guava-17.0.jar" dest="${'$'}{core}/guava-17.0.jar" usetimestamp="true"/>
                                    <get src="${'$'}{content.base.url}/core/picocontainer.jar" dest="${'$'}{core}/picocontainer.jar" usetimestamp="true"/>
                                    <get src="${'$'}{content.base.url}/core/trove4j.jar" dest="${'$'}{core}/trove4j.jar" usetimestamp="true"/>
                                    <!--<get src="${'$'}{content.base.url}/core/snappy-java-1.0.5.jar" dest="${'$'}{core}/snappy-java-1.0.5.jar" usetimestamp="true"/>-->
                
                                    <get src="${'$'}{content.base.url}/jps/standalone-jps-IC-${'$'}{idea.build.number}.zip"
                                         dest="dependencies/download/standalone-jps.zip"
                                         usetimestamp="true"/>
                
                                    <property name="jps.extracted.dir" value="dependencies/download/standalone-jps"/>
                                    <unzip src="dependencies/download/standalone-jps.zip" dest="${'$'}{jps.extracted.dir}"/>
                
                                    <copy todir="${'$'}{jps}" flatten="true">
                                        <resources>
                                            <file file="${'$'}{jps.extracted.dir}/groovy-jps-plugin.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/groovy_rt.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/jdom.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/jgoodies-forms.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/jna.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/jps-builders.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/jps-model.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/log4j.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/nanoxml-2.2.3.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/protobuf-2.5.0.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/trove4j.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/ui-designer-jps-plugin.jar"/>
                                            <file file="${'$'}{jps.extracted.dir}/util.jar"/>
                                        </resources>
                                    </copy>
                
                                    <get src="${'$'}{content.base.url}/jps/jps-build-test-IC-${'$'}{idea.build.number}.jar" dest="${'$'}{jps-test}/jps-build-test.jar"
                                         usetimestamp="true"/>
                
                                    <get src="${'$'}{content.base.url}/${'$'}{idea.archive.name}" dest="dependencies/download/${'$'}{idea.archive.name}" usetimestamp="true"/>
                
                                    <delete file="dependencies/download/idea-sdk-sources.zip" failonerror="false"/>
                                    <get src="${'$'}{content.base.url}/sources.zip" dest="dependencies/download/idea-sdk-sources.zip" usetimestamp="true"/>
                                </then>
                            </if>
                
                            <if>
                                <matches pattern=".+\.win\.zip" string="${'$'}{idea.archive.name}"/>
                                <then>
                                    <unzip src="dependencies/download/${'$'}{idea.archive.name}" dest="ideaSDK"/>
                                </then>
                                <elseif>
                                    <matches pattern=".+\.mac\.zip" string="${'$'}{idea.archive.name}"/>
                                    <then>
                                        <unzip src="dependencies/download/${'$'}{idea.archive.name}" dest="ideaSDK">
                                            <cutdirsmapper dirs="2"/>
                                        </unzip>
                                        <!-- Java can't manipulate permissions -->
                                        <exec executable="chmod">
                                            <arg value="a+x"/>
                                            <arg path="ideaSDK/bin/fsnotifier"/>
                                            <arg path="ideaSDK/bin/inspect.sh"/>
                                            <arg path="ideaSDK/bin/printenv.py"/>
                                            <arg path="ideaSDK/bin/restarter"/>
                                        </exec>
                                    </then>
                                </elseif>
                                <else>
                                    <untar src="dependencies/download/${'$'}{idea.archive.name}" dest="ideaSDK" compression="gzip">
                                        <cutdirsmapper dirs="1"/>
                                    </untar>
                                    <!-- Java can't manipulate permissions -->
                                    <exec executable="chmod">
                                        <arg value="a+x"/>
                                        <arg path="ideaSDK/bin/fsnotifier"/>
                                        <arg path="ideaSDK/bin/fsnotifier64"/>
                                        <arg path="ideaSDK/bin/inspect.sh"/>
                                        <arg path="ideaSDK/bin/idea.sh"/>
                                    </exec>
                                </else>
                            </if>
                
                            <mkdir dir="ideaSDK/sources"/>
                            <copy file="dependencies/download/idea-sdk-sources.zip" tofile="ideaSDK/sources/sources.zip" overwrite="true"/>
                
                            <copy file="ideaSDK/lib/jdom.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/jna.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/log4j.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/xstream-1.4.3.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/xpp3-1.1.4-min.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/jsr166e.jar" todir="${'$'}{core}"/>
                            <copy file="ideaSDK/lib/asm-all.jar" todir="${'$'}{core}"/>
                
                            <!-- TODO temporary workaround since util-rt is not packaged into intellij-core.jar -->
                            <copy file="ideaSDK/lib/util.jar" todir="${'$'}{core}"/>
                
                            <!--
                                 This one needs to be deleted because otherwise it gets onto the classpath
                                 together with junit-4.10.jar and the classloading goes crazy that breaks
                                 many nice features of IDEA including diffs in the test console.
                            -->
                            <delete file="ideaSDK/lib/junit.jar"/>
                        </sequential>
                    </macrodef>
                
                    <target name="fetch-annotations">
                        <mkdir dir="dependencies/annotations"/>
                        <get
                            src="https://teamcity.jetbrains.com/guestAuth/repository/download/Kotlin_KAnnotator_InferJdkAnnotations/shipWithKotlin.tcbuildtag/kotlin-jdk-annotations.jar"
                            dest="dependencies/annotations/kotlin-jdk-annotations.jar" usetimestamp="true"/>
                        <get
                            src="https://teamcity.jetbrains.com/guestAuth/repository/download/Kotlin_KAnnotator_InferJdkAnnotations/shipWithKotlin.tcbuildtag/kotlin-android-sdk-annotations.jar"
                            dest="dependencies/annotations/kotlin-android-sdk-annotations.jar" usetimestamp="true"/>
                    </target>
                
                    <target name="get_android_studio">
                        <taskdef resource="net/sf/antcontrib/antcontrib.properties" classpath="${'$'}{basedir}/dependencies/ant-contrib.jar"/>
                
                        <condition property="android.os.tag" value="windows">
                            <os family="windows"/>
                        </condition>
                
                        <condition property="android.os.tag" value="mac">
                            <os family="mac"/>
                        </condition>
                
                        <condition property="android.os.tag" value="linux">
                            <and>
                                <os family="unix"/>
                                <not>
                                    <os family="mac"/>
                                </not>
                            </and>
                        </condition>
                
                        <if>
                            <not>
                                <and>
                                    <isset property="android.version"/>
                                    <isset property="android.build.version"/>
                                </and>
                            </not>
                            <then>
                                <loadresource property="android.version">
                                    <url url="http://tools.android.com/download/studio/canary/latest"/>
                                    <filterchain>
                                        <tokenfilter>
                                            <filetokenizer/>
                                            <replaceregex
                                                pattern="^(.*)https?://dl\.google\.com/dl/android/studio/ide-zips/([\d\.]+)/android-studio-ide(.*)${'$'}"
                                                replace="\2" flags="s"/>
                                        </tokenfilter>
                                    </filterchain>
                                </loadresource>
                                <loadresource property="android.build.version">
                                    <url url="http://tools.android.com/download/studio/canary/latest"/>
                                    <filterchain>
                                        <tokenfilter>
                                            <filetokenizer/>
                                            <replaceregex
                                                pattern="^(.*)https?://dl\.google\.com/dl/android/studio/ide-zips/[\d\.]+/android-studio-ide-([\d\.]+)-(.*)${'$'}"
                                                replace="\2" flags="s"/>
                                        </tokenfilter>
                                    </filterchain>
                                </loadresource>
                            </then>
                        </if>
                
                        <echo message="Download android studio: ${'$'}{android.version} ${'$'}{android.build.version}"/>
                
                        <property name="android.file.name" value="android-studio-ide-${'$'}{android.build.version}-${'$'}{android.os.tag}.zip"/>
                        <property name="android.studio.url"
                                  value="http://dl.google.com/dl/android/studio/ide-zips/${'$'}{android.version}/${'$'}{android.file.name}"/>
                        <property name="android.destination.dir" value="android-studio/sdk"/>
                
                        <mkdir dir="dependencies/download"/>
                
                        <get src="${'$'}{android.studio.url}" dest="dependencies/download" usetimestamp="true"/>
                
                        <delete dir="${'$'}{android.destination.dir}" failonerror="false" includeemptydirs="true">
                            <exclude name="config/**"/>
                            <exclude name="system/**"/>
                        </delete>
                
                        <unzip src="dependencies/download/${'$'}{android.file.name}" dest="${'$'}{android.destination.dir}">
                            <cutdirsmapper dirs="1"/>
                        </unzip>
                
                        <if>
                            <matches pattern=".+windows\.zip" string="${'$'}{android.file.name}"/>
                            <then>
                            </then>
                            <elseif>
                                <matches pattern=".+mac\.zip" string="${'$'}{android.file.name}"/>
                                <then>
                                    <exec executable="chmod">
                                        <arg value="a+x"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/fsnotifier"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/inspect.sh"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/printenv.py"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/update_studio.sh"/>
                                    </exec>
                                </then>
                            </elseif>
                            <elseif>
                                <matches pattern=".+linux\.zip" string="${'$'}{android.file.name}"/>
                                <then>
                                    <exec executable="chmod">
                                        <arg value="a+x"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/fsnotifier"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/fsnotifier64"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/inspect.sh"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/studio.sh"/>
                                        <arg path="${'$'}{android.destination.dir}/bin/update_studio.sh"/>
                                    </exec>
                                </then>
                            </elseif>
                            <else>
                                <fail message="File name '${'$'}{android.file.name}' wasn't matched"/>
                            </else>
                        </if>
                    </target>
                </project>
            """.trimIndent())
        }
        ant {
            name = "Pre build"
            id = "RUNNER_861"
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "pre_build"
            antHome = "%kotlin.ant.home%"
            jdkHome = "%env.JDK_16%"
        }
        ant {
            name = "Standalone compiler"
            id = "RUNNER_862"
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "build-artifacts"
            antHome = "%kotlin.ant.home%"
            antArguments = "-Dbootstrap.compiler.home=%kotlin.bootstrap.home%"
            jdkHome = "%env.JDK_16%"
            jvmArgs = "-Xmx1024m -ea"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("org.jfrog.artifactory.selectedDeployableServer.overrideDefaultDeployerCredentials", "true")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx10a7df6d479251d4de4363f3ccae2df8")
        }
        ideaRunner {
            id = "RUNNER_863"
            pathToProject = ""
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%kotlin.jdk18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = """
                -Xmx1000M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:ReservedCodeCacheSize=64m -XX:+UseCodeCacheFlushing
                -XX:MaxPermSize=500m
            """.trimIndent()
            targetJdkHome = "%env.JDK_16%"
            runConfigurations = "All Tests"
            artifactsToBuild = """
                KotlinPlugin
                KotlinBarePlugin
                InjectorGenerator
                IdeLazyResolver
                KotlinAndroidExtensions
            """.trimIndent()
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        ant {
            name = "Substitute compiler and runtime with release version"
            id = "RUNNER_864"
            mode = antScript {
                content = """
                    <project name="Kotlin" default="substitute">
                      <property name="kotlin.plugin.dir" value="out/artifacts/Kotlin"/>
                      <property name="kotlin.bare.plugin.dir" value="out/artifacts/BareKotlin"/>
                      <property name="kotlin.compiler.bootstrap.dir" value="${'$'}{kotlin.bootstrap.plugin.dir}/Kotlin"/>
                    
                      <macrodef name="substitute-kotlinc">
                        <attribute name="dir"/>
                        <sequential>
                          <delete dir="@{dir}/kotlinc" failonerror="false"/>
                    	  <copy todir="@{dir}/kotlinc">
                    	    <fileset dir="${'$'}{kotlin.compiler.bootstrap.dir}/kotlinc"/>
                    	  </copy>
                        </sequential>
                      </macrodef>
                      
                      <target name="substitute">
                        <substitute-kotlinc dir="${'$'}{kotlin.plugin.dir}"/>
                        <substitute-kotlinc dir="${'$'}{kotlin.bare.plugin.dir}"/>
                      </target>
                    </project>
                """.trimIndent()
            }
        }
        ant {
            name = "Ultimate Project: Fetch Dependencies"
            id = "RUNNER_865"
            mode = antScript {
                content = """
                    <project name="Build Ultimate Artifacts" default="update_dependencies" xmlns:if="ant:if" xmlns:unless="ant:unless">
                        <target name="update_dependencies">
                            <condition property="ultimate.build.xml.exists">
                                <available file="ultimate/update_dependencies.xml"/>
                            </condition>
                            <sequential if:true="${'$'}{ultimate.build.xml.exists}">
                                <ant antfile="update_dependencies.xml" dir="ultimate" />
                            </sequential>
                        </target>
                    </project>
                """.trimIndent()
            }
            jdkHome = "%env.JDK_16%"
        }
        ideaRunner {
            name = "Ultimate Project: Compile"
            id = "RUNNER_866"
            pathToProject = "%kotlin.ultimate.dir%"
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%kotlin.jdk18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = """
                -Xmx1000M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:ReservedCodeCacheSize=64m -XX:+UseCodeCacheFlushing
                -XX:MaxPermSize=500m
            """.trimIndent()
            targetJdkHome = "%env.JDK_16%"
            makeRequiredModulesOnly = true
            artifactsToBuild = "%kotlin.ultimate.dummy.artifact%"
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        ant {
            name = "Ultimate Project: Build Artifacts"
            id = "RUNNER_867"
            mode = antScript {
                content = """
                    <project name="Build Ultimate Artifacts" default="build_artifacts" xmlns:if="ant:if" xmlns:unless="ant:unless">
                        <target name="build_artifacts">
                            <condition property="ultimate.build.xml.exists">
                                <available file="ultimate/build.xml"/>
                            </condition>
                            <sequential if:true="${'$'}{ultimate.build.xml.exists}">
                                <ant antfile="build.xml" dir="ultimate" />
                                <delete file="out/artifacts/Kotlin"/>
                                <move file="ultimate/out/artifacts/Kotlin" todir="out/artifacts"/>
                            </sequential>
                        </target>
                    </project>
                """.trimIndent()
            }
            jdkHome = "%env.JDK_16%"
        }
        ant {
            name = "Ultimate Project: Prepare Tests"
            id = "RUNNER_868"
            mode = antScript {
                content = """
                    <project name="Build Ultimate Artifacts" default="build_artifacts" xmlns:if="ant:if" xmlns:unless="ant:unless">
                        <target name="build_artifacts">
                            <condition property="ultimate.build.xml.exists">
                                <available file="ultimate/build.xml"/>
                            </condition>
                            <sequential if:true="${'$'}{ultimate.build.xml.exists}">
                                <ant antfile="build.xml" dir="ultimate" target="patch_outer_plugin_xml_for_tests" />
                            </sequential>
                        </target>
                    </project>
                """.trimIndent()
            }
            jdkHome = "%env.JDK_16%"
        }
        ideaRunner {
            name = "Ultimate Project: Run Tests"
            id = "RUNNER_869"
            pathToProject = "%kotlin.ultimate.dir%"
            jdk {
                name = "1.6"
                path = "%env.JDK_16%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            jdk {
                name = "1.8"
                path = "%kotlin.jdk18%"
                patterns("jre/lib/*.jar", "lib/tools.jar")
                extAnnotationPatterns("%teamcity.tool.idea%/lib/jdkAnnotations.jar")
            }
            pathvars {
                variable("KOTLIN_BUNDLED", "%system.path.macro.KOTLIN.BUNDLED%")
            }
            jvmArgs = """
                -Xmx1000M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%teamcity.build.checkoutDir%/out
                -XX:ReservedCodeCacheSize=64m -XX:+UseCodeCacheFlushing
                -XX:MaxPermSize=500m
            """.trimIndent()
            targetJdkHome = "%env.JDK_16%"
            runConfigurations = "%kotlin.ultimate.tests.run.configuration%"
            makeRequiredModulesOnly = true
            reduceTestFeedback = IdeaRunner.TestPolicy.RECENTLY_FAILED_AND_MODIFIED
            param("teamcity.coverage.idea.excludePatterns", """
                #teamcity:patternsMode=regexp
                org.jetbrains.jet.cli.*
            """.trimIndent())
            param("teamcity.coverage.idea.includePatterns", "org.jetbrains.jet.*")
        }
        ant {
            name = "Post Build"
            id = "RUNNER_870"
            mode = antFile {
                path = "TeamCityBuild.xml"
            }
            targets = "post_build"
            jdkHome = "%env.JDK_16%"
        }
        ant {
            name = "Generate updatePlugins.xml"
            id = "RUNNER_871"
            mode = antScript {
                content = """
                    <project name="Generate updatePlugins.xml" default="generate">
                      <target name="generate">
                        <echoxml file="updatePlugins.xml">
                          <plugins>
                            <plugin id="org.jetbrains.kotlin" url="%teamcity.serverUrl%/guestAuth/repository/download/%system.teamcity.buildType.id%/%teamcity.build.id%:id/kotlin-plugin-%build.number%.zip" version="%build.number%" />
                            <plugin id="org.jetbrains.kotlin.bare" url="%teamcity.serverUrl%/guestAuth/repository/download/%system.teamcity.buildType.id%/%teamcity.build.id%:id/kotlin-bare-plugin-%build.number%.zip" version="%build.number%" />
                          </plugins>
                        </echoxml>
                    
                        <echo message="##teamcity[publishArtifacts 'updatePlugins.xml']" />
                      </target>
                    </project>
                """.trimIndent()
            }
            antArguments = "-v"
            jdkHome = "%env.JDK_16%"
        }
        ant {
            name = "Add build version to summary"
            id = "RUNNER_872"
            mode = antScript {
                content = """
                    <project name="Kotlin" default="status">
                      <target name="status">
                        <echo message="##teamcity[buildStatus text='Compiler: %dep.bt345.build.number% Idea: %system.ideaVersion% {build.status.text}']"/>
                      </target>
                    </project>
                """.trimIndent()
            }
        }
    }

    triggers {
        vcs {
            id = "vcsTrigger"
            branchFilter = "+:<default>"
        }
    }

    failureConditions {
        executionTimeoutMin = 240
    }

    features {
        vcsLabeling {
            id = "BUILD_EXT_120"
            vcsRootExtId = "__ALL__"
            labelingPattern = "%system.build.number%-%system.ideaVersion%"
        }
        freeDiskSpace {
            id = "jetbrains.agent.free.space"
            requiredSpace = "6gb"
            failBuild = false
        }
    }

    dependencies {
        artifacts(Kotlin.buildTypes.bt345) {
            id = "ARTIFACT_DEPENDENCY_93"
            buildRule = lastSuccessful("%release.tag.branch.name%")
            artifactRules = "kotlin-plugin-*.zip!**=>%system.kotlin.bootstrap.plugin.dir%"
        }
    }

    requirements {
        doesNotEqual("cloud.amazon.agent-name-prefix", "ubuntu-10.10", "RQ_107")
    }
})
