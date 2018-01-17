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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public final class DeleteMojoTest {
    private static final File testarea = new File("testarea");
    private DeleteMojo mojo;

    @BeforeClass
    public static void setUpClass() throws IOException {
        testarea.mkdir();
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/delete"), testarea);
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/check"), testarea);
    }

    @Before
    public void setUp() {
        mojo = new DeleteMojo();
    }

    @Test
    public void executeFileShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final File file = new File("testarea/delete/fileToDelete.txt");
        mojo.setFile(file);
        mojo.execute();
        assertThat(file).doesNotExist();
    }

    @Test
    public void executeDirShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final File file = new File("testarea/delete/dirToDelete");
        mojo.setFile(file);
        mojo.execute();
        assertThat(file).doesNotExist();
    }

    @Test
    public void checkFileDoesNotExistShouldThrowException() {
        mojo.setFile(new File("nonExistingFile"));
        assertThatThrownBy(() -> mojo.check()).isExactlyInstanceOf(MojoExecutionException.class)
                .hasMessage("file does not exist");
    }

    @Test
    public void checkFileNotReadableShouldThrowException() {
        final File file = new File("testarea/check/notReadableFile.txt");
        file.setWritable(false);
        mojo.setFile(file);
        assertThatThrownBy(() -> mojo.check()).isExactlyInstanceOf(MojoExecutionException.class)
                .hasMessage("file not writable");
    }

    @Test
    public void toStringShouldSucceed() {
        final File file = new File("testarea/delete/fileToDelete.txt");
        mojo.setFile(file);
        assertThat(mojo.toString()).isEqualTo(new ToStringBuilder(mojo).append("file", mojo.getFile()).toString());
    }

    @AfterClass
    public static void cleanUpClass() throws IOException {
        new File("testarea/check/notWritableDir").setWritable(true);
        FileUtils.deleteDirectory(testarea);
    }
}
