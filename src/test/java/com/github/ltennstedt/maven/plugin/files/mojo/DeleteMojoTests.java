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

import com.google.common.base.MoreObjects;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class DeleteMojoTests {
    private static final File testarea = new File("testarea");
    private DeleteMojo mojo;

    @BeforeAll
    public static void setUpAll() throws IOException {
        testarea.mkdir();
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/delete"), testarea);
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/check"), testarea);
    }

    @BeforeEach
    public void setUpEach() {
        mojo = new DeleteMojo();
    }

    @Test
    public void executeFileShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final var file = new File("testarea/delete/fileToDelete.txt");
        mojo.setFile(file);
        mojo.execute();
        assertThat(file).doesNotExist();
    }

    @Test
    public void executeDirShouldSucceed() throws MojoFailureException, MojoExecutionException {
        final var file = new File("testarea/delete/dirToDelete");
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
        final var file = new File("testarea/check/notReadableFile.txt");
        file.setWritable(false);
        mojo.setFile(file);
        assertThatThrownBy(() -> mojo.check()).isExactlyInstanceOf(MojoExecutionException.class)
                .hasMessage("file not writable");
    }

    @Test
    public void toStringShouldSucceed() {
        mojo.setFile(new File("testarea/delete/fileToDelete.txt"));
        assertThat(mojo.toString()).isEqualTo(MoreObjects.toStringHelper(mojo).add("file", mojo.getFile()).toString());
    }

    @AfterAll
    public static void cleanUpAll() throws IOException {
        new File("testarea/check/notWritableDir").setWritable(true);
        FileUtils.deleteDirectory(testarea);
    }
}
