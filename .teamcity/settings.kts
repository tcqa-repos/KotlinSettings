import jetbrains.buildServer.configs.kotlin.v2019_2.*

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

project {

    buildType(Rake)
}

object Rake : BuildType({
    name = "Rake"

    steps {
        step {
            type = "rake-runner"
            param("ui.rakeRunner.rake.trace.invoke.exec.stages.enabled", "true")
            param("ui.rakeRunner.frameworks.shoulda.enabled", "true")
            param("ui.rakeRunner.rake.tasks.names", "hello_world")
            param("use-custom-build-file", "true")
            param("ui.rakeRunner.frameworks.testspec.enabled", "true")
            param("build-file", """
                desc "print hello world!"        # description.
                task "hello_world" do            # rake task name.
                   p "hello world!!"                 # print "hello world!"
                end
            """.trimIndent())
            param("ui.rakeRunner.additional.rake.cmd.params", "sss")
            param("ui.rakeRunner.ruby.use.mode", "path")
            param("ui.rakeRunner.ruby.interpreter.path", "sss")
        }
    }
})
