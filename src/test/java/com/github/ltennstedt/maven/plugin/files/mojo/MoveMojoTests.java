/*
 * Copyright 2017 Lars Tennstedt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.ltennstedt.maven.plugin.files.mojo;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.base.MoreObjects;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class MoveMojoTests {
    private static final File testarea = new File("testarea");
    private MoveMojo mojo;

    @BeforeAll
    public static void setUpAll() throws IOException {
        testarea.mkdir();
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/move"), testarea);
    }

    @BeforeEach
    public void setUpEach() {
        mojo = new MoveMojo();
    }

    @Test
    public void executeFileShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final File file = new File("testarea/move/fileToMove.txt");
        mojo.setFile(file);
        mojo.setInto(new File("testarea/into"));
        mojo.execute();
        assertThat(new File("testarea/into/fileToMove.txt")).isFile()
                .hasSameContentAs(new File("src/test/resources/move/fileToMove.txt"));
        assertThat(file).doesNotExist();
    }

    @Test
    public void executeDirShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final File file = new File("testarea/move/dirToMove");
        mojo.setFile(file);
        mojo.setInto(new File("testarea/into"));
        mojo.execute();
        assertThat(new File("testarea/into")).isDirectory();
        assertThat(new File("testarea/into/subdir/file.txt"))
                .hasSameContentAs(new File("src/test/resources/copy/dirToCopy/subdir/file.txt"));
        assertThat(new File("testarea/into/file.txt"))
                .hasSameContentAs(new File("src/test/resources/copy/dirToCopy/file.txt"));
        assertThat(file).doesNotExist();
    }

    @Test
    public void toStringShouldSucceed() {
        mojo.setFile(new File("testarea/move/fileToMove.txt"));
        mojo.setInto(new File("testarea/into"));
        assertThat(mojo.toString()).isEqualTo(
                MoreObjects.toStringHelper(mojo).add("file", mojo.getFile()).add("into", mojo.getInto()).toString());
    }

    @AfterEach
    public void cleanUpEach() throws IOException {
        FileUtils.deleteDirectory(new File("testarea/into"));
    }

    @AfterAll
    public static void cleanUpAll() throws IOException {
        FileUtils.deleteDirectory(testarea);
    }
}
