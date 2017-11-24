# files-maven-plugin
A Maven plugin for copying, moving and deleting files and directories

[![Build Status](https://travis-ci.org/ltennstedt/files-maven-plugin.svg?branch=master)](https://travis-ci.org/ltennstedt/files-maven-plugin)
[![codecov](https://codecov.io/gh/ltennstedt/files-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/ltennstedt/files-maven-plugin)
[![License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](http://shields.io)

The files-maven-plugin is a Maven plugin which copies, moves or deletes files and directories.

What file-maven-plugin will provide:
* Copying of files and directories
* Moving of files and directories
* Deletion of files and directories

What files-maven-plugin will not provide:
* Complete file management
* Renaming of files and directories
* A replacement for Ant

The files-maven-plugin is written in Java and licensed under the permissive Apache Licsense and therefore free software.

## Implementation details
* Java 8
* All leaf classes are final.
* Useful toString methods

## Information
* Maven site: https://ltennstedt.github.io/files-maven-plugin/index.html
* Maven plugin docs: https://ltennstedt.github.io/files-maven-plugin/plugin-info.html

files-maven-plugin is open source and free software and is licensed under the permissive Apache License.

files-maven-plugin is still in a very early state and a work in progress.

## Building

#### Prerequisites
* JDK
* Maven
* Git (optional)
#
    git clone https://github.com/ltennstedt/files-maven-plugin.git
    cd files-maven-plugin
    mvn install

This will clone the remote Git repository, build files-maven-plugin and install it into your local Maven repository.

## Developing

The code formatting follows loosely the Google Java style guide found here on GitHub.    

#### Why files-maven-plugin?
I searched for a Maven plugin which was able to copy some directories and I could not simply find one.


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
* Please show some love for your code, tests and build file 

## Thanks to
* Oracle for the JVM, Java and OpenJDK
* the Eclipse Foundation for the Eclipse IDE
* Google for Error Prone
* the Apache Software Foundation for Maven, Commons Lang, Commons IO and the Apache License
* Joel Costigliola for AssertJ
* the JUnit team for JUnit
* Linus Torvalds for Git
* the developers of Checkstyle
* the developers of SpotBugs
* the developers of PMD
* the developers of JaCoCo and EclEmma
* GitHub for GitHub
* Travis CI for Travis CI
* Codecov for Codecov
* Stack Exchange for Stack Overflow
* Shields.io for Shields.io
* Judd Vinet and Aaron Griffin for Arch Linux
