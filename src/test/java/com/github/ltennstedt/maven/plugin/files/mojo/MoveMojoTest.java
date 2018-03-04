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

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public final class MoveMojoTest {
    private static final File testarea = new File("testarea");
    private MoveMojo mojo;

    @BeforeClass
    public static void setUpClass() throws IOException {
        testarea.mkdir();
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/move"), testarea);
    }

    @Before
    public void setUp() {
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
        final File file = new File("testarea/move/fileToMove.txt");
        mojo.setFile(file);
        mojo.setInto(new File("testarea/into"));
        assertThat(mojo.toString()).isEqualTo(
            new ToStringBuilder(mojo).append("file", mojo.getFile()).append("into", mojo.getInto()).toString());
    }

    @After
    public void cleanUp() throws IOException {
        FileUtils.deleteDirectory(new File("testarea/into"));
    }

    @AfterClass
    public static void cleanUpClass() throws IOException {
        FileUtils.deleteDirectory(testarea);
    }
}
