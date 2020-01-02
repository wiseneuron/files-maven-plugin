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
        // given
        final var file = new File("testarea/delete/fileToDelete.txt");
        mojo.setFile(file);

        // when
        mojo.execute();

        // then
        assertThat(file).doesNotExist();
    }

    @Test
    public void executeDirShouldSucceed() throws MojoFailureException, MojoExecutionException {
        // given
        final var file = new File("testarea/delete/dirToDelete");
        mojo.setFile(file);

        // when
        mojo.execute();

        // then
        assertThat(file).doesNotExist();
    }

    @Test
    public void toStringShouldSucceed() {
        // given
        mojo.setFile(new File("testarea/delete/fileToDelete.txt"));

        // when
        final var actual = mojo.toString();

        // then
        final var expected = MoreObjects.toStringHelper(mojo).add("file", mojo.getFile()).toString();
        assertThat(actual).isEqualTo(expected);
    }

    @AfterAll
    public static void cleanUpAll() throws IOException {
        new File("testarea/check/notWritableDir").setWritable(true);
        FileUtils.deleteDirectory(testarea);
    }
}
