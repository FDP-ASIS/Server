# Gradle

Gradle is an open-source build automation system which is used to automate building, testing, deployment etc. Builds upon the concepts of Apache Ant and Apache Maven and introduces a Groovy-based domain-specific language instead of the XML form used by Maven for declaring the project configuration. Gradle uses a directed acyclic graph to determine the order in which tasks can be run.

It was designed for multi-project builds, which can grow to be quite large. It supports incremental builds by intelligently determining which parts of the build tree are up to date; any task dependent only on those parts does not need to be re-executed.

“**Build**. **gradle**” are scripts where one can automate the tasks. For example, the simple task to copy some files from one directory to another can be performed by **Gradle build** script before the actual **build** process happens.

![](../.gitbook/assets/gradle.png)

