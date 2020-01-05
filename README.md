# files-maven-plugin
Maven plugin for copying, moving and deleting files and directories

[![Workflow Status](https://github.com/ltennstedt/files-maven-plugin/workflows/JAVA%20CI/badge.svg)]
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](http://shields.io)
[![JCenter](https://img.shields.io/badge/jcenter-2-green.svg)](http://shields.io)

The files-maven-plugin is a Maven plugin which copies, moves or deletes files and directories.

What file-maven-plugin will provide:
* Copying of files and directories
* Moving of files and directories
* Deletion of files and directories

What files-maven-plugin will NOT provide:
* Complete file management
* Renaming of files and directories
* A replacement for Ant or Gradle
* A replacement for the Maven Antrun, Maven Clean or Maven Resources Plugin

The files-maven-plugin is written in Java, is very simplistic and does not very much.

## Implementation details
* Java 11
* All classes are final.
* Uses `java.nio.file.*` for all file system operations
* Useful toString methods

## Information
* Maven site: https://ltennstedt.github.io/files-maven-plugin/index.html
* Maven plugin docs: https://ltennstedt.github.io/files-maven-plugin/plugin-info.html

files-maven-plugin is open source and free software and is licensed under the permissive Apache License.

## Usage

settings.xml
```xml
<profiles>
    <profile>
        <id>bintray</id>
        <pluginRepositories>
            <pluginRepository>
                <id>central</id>
                <snapshots>
                    <enabled>false</enabled>
                </snapshots>
                <name>bintray-plugins</name>
                <url>https://jcenter.bintray.com</url>
            </pluginRepository>
        </pluginRepositories>
    </profile>
</profiles>
<activeProfiles>
    <activeProfile>bintray</activeProfile>
</activeProfiles>
```

pom.xml
```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.ltennstedt</groupId>
            <artifactId>files-maven-plugin</artifactId>
            <version>2</version>
            <executions>
                <execution>
                    <id>copy</id>
                    <goals>
                        <goal>copy</goal>
                    </goals>
                    <configuration>
                        <file>file</file>
                        <into>into</into>
                    </configuration>
                </execution>
                <execution>
                    <id>move</id>
                    <goals>
                        <goal>move</goal>
                    </goals>
                    <configuration>
                        <file>file</file>
                        <into>into</into>
                    </configuration>
                </execution>
                <execution>
                    <id>delete</id>
                    <goals>
                        <goal>delete</goal>
                    </goals>
                    <configuration>
                        <file>file</file>
                    </configuration>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Building

#### Prerequisites
* JDK 11
* Maven 3.6
* Git (optional)
#
    git clone https://github.com/ltennstedt/files-maven-plugin.git
    cd files-maven-plugin
    mvn install

This will clone the remote Git repository, build files-maven-plugin and install it into your local Maven repository.

## Developing
The code formatting follows loosely the Google Java style guide found here on GitHub.    

#### Why files-maven-plugin?
I searched for a Maven plugin which was able to copy some directories and I could not simply find one. Possible 
solutions were the use of the Maven Antrun Plugin, the combination of the Maven Clean and Maven Resources Plugin, 
Ant or Gradle but none of them made me happy because I don't want to change the build automation tool or 
integrate a second one into my Maven projects so I decided to start writing this plugin.

#### Why AssertJ?
I find its assertions more readable in comparison to JUnit and Hamcrest and the fluent assertions are more IDE 
friendly.

#### Notes
* Please consider using free software
* Please consider using open standards
* Please consider using recent versions of software
* Please consider reporting bugs
* Please consider writing code, patches or documentation for free software projects
* Please consider joining communities via forums, mailing lists or irc
* Please consider donating to free software projects
* Please show some love with your code, tests and build file 

## Thanks to
* [Oracle](https://www.oracle.com/) for [Java](https://www.java.com/) and [OpenJDK](https://openjdk.java.net/)
* [Google](https://about.google/) for [Guava](https://github.com/google/guava/)
* the [Apache Software Foundation](http://apache.org/) for [Maven](http://maven.apache.org/), 
[Commons Lang](http://commons.apache.org/proper/commons-lang/), 
[Commons IO](http://commons.apache.org/proper/commons-io/) and the 
[Apache License](http://apache.org/licenses/LICENSE-2.0)
* Joel Costigliola for [AssertJ](https://joel-costigliola.github.io/assertj/)
* the JUnit team for [JUnit](https://junit.org/)
* Linus Torvalds for [Git](https://git-scm.com/)
* the developers of [Checkstyle](https://checkstyle.org/)
* the developers of [SpotBugs](https://spotbugs.github.io/)
* the developers of [PMD](https://pmd.github.io/)
* the developers of [JaCoCo](https://www.eclemma.org/jacoco/index.html) and 
[EclEmma](https://www.eclemma.org/index.html)
* GitHub for [GitHub](https://github.com/)
* Stack Exchange for [Stack Overflow](https://stackoverflow.com/)
* Shields.io for [Shields.io](https://shields.io/)
