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

package com.github.ltennstedt.maven.plugin.files.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import com.github.ltennstedt.maven.plugin.files.mojo.CopyMojo;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PreconditionsTests {
    private static final File testarea = new File("testarea");
    private final CopyMojo mojo = new CopyMojo();

    @BeforeAll
    public static void setUpAll() throws IOException {
        testarea.mkdir();
        FileUtils.copyDirectoryToDirectory(new File("src/test/resources/check"), testarea);
    }

    @Test
    public void checkFileFileIsNullShouldThrowException() {
        // when
        final var actual =
                catchThrowableOfType(() -> Preconditions.checkFile(null, mojo.getLog()), NullPointerException.class);

        // then
        assertThat(actual).hasMessage("file").hasNoCause();
    }

    @Test
    public void checkFileLogIsNullShouldThrowException() {
        // when
        final var actual =
                catchThrowableOfType(() -> Preconditions.checkFile(new File(""), null), NullPointerException.class);

        // then
        assertThat(actual).hasMessage("log").hasNoCause();
    }

    @Test
    public void checkFileDoesNotExistShouldThrowException() {
        // given
        mojo.setFile(new File("nonExistingFile"));

        // when
        final var actual = catchThrowableOfType(() -> Preconditions.checkFile(mojo.getFile(), mojo.getLog()),
                MojoExecutionException.class);

        // then
        assertThat(actual).hasMessage("file does not exist").hasNoCause();
    }

    @Test
    public void checkFileNotReadableShouldThrowException() {
        // given
        final var file = new File("testarea/check/notReadableFile.txt");
        file.setReadable(false);
        mojo.setFile(file);

        // when
        final var actual = catchThrowableOfType(() -> Preconditions.checkFile(mojo.getFile(), mojo.getLog()),
                MojoExecutionException.class);

        // then
        assertThat(actual).hasMessage("file not readable").hasNoCause();
    }

    @Test
    public void checkIntoIntoIsNullShouldThrowException() {
        // when
        final var actual =
                catchThrowableOfType(() -> Preconditions.checkInto(null, mojo.getLog()), NullPointerException.class);

        // then
        assertThat(actual).hasMessage("into").hasNoCause();
    }

    @Test
    public void checkIntoLogIsNullShouldThrowException() {
        // when
        final var actual =
                catchThrowableOfType(() -> Preconditions.checkInto(new File(""), null), NullPointerException.class);

        // then
        assertThat(actual).hasMessage("log").hasNoCause();
    }

    @Test
    public void checkIntoIsFileShouldThrowException() {
        // given
        mojo.setInto(new File("testarea/check/intoFile.txt"));

        // when
        final var actual = catchThrowableOfType(() -> Preconditions.checkInto(mojo.getInto(), mojo.getLog()),
                MojoExecutionException.class);

        // then
        assertThat(actual).hasMessage("into is a file").hasNoCause();
    }

    @Test
    public void checkIntoNotWrtitableShouldThrowException() {
        // given
        final var into = new File("testarea/check/notWritableDir");
        into.setWritable(false);
        mojo.setInto(into);

        // when
        final var actual = catchThrowableOfType(() -> Preconditions.checkInto(mojo.getInto(), mojo.getLog()),
                MojoExecutionException.class);

        // then
        assertThat(actual).hasMessage("into not writable").hasNoCause();
    }

    @AfterAll
    public static void cleanUpAll() throws IOException {
        new File("testarea/check/notWritableDir").setWritable(true);
        FileUtils.deleteDirectory(testarea);
    }
}
