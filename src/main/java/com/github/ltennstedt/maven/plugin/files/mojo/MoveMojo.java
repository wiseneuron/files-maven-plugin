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

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Mojo for moving files and directories
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@Beta
@Mojo(name = "move")
public final class MoveMojo extends AbstractMojo {
    /**
     * Source file or directory
     *
     * @since 0.0.1
     */
    @Parameter(required = true)
    private File file;

    /**
     * Target directory
     *
     * @since 0.0.1
     */
    @Parameter(required = true)
    private File into;

    /**
     * {@inheritDoc}
     *
     * @since 0.0.1
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Moving " + file.getAbsolutePath() + " into " + into.getAbsolutePath());
        final var start = Path.of(file.getAbsolutePath());
        final var target = Path.of(into.getAbsolutePath());
        try {
            Files.createDirectories(target);
            if (Files.isDirectory(start)) {
                try (var walk = Files.walk(start)) {
                    walk.forEach(w -> {
                        try {
                            if (Files.isDirectory(w)) {
                                Files.copy(w, target.resolve(start.relativize(w)), StandardCopyOption.REPLACE_EXISTING);
                            } else {
                                Files.move(w, target.resolve(start.relativize(w)), StandardCopyOption.REPLACE_EXISTING);
                            }
                        } catch (final IOException exception) {
                            throw new UncheckedIOException(exception);
                        }
                    });
                }
                try (var walk = Files.walk(Path.of(file.getAbsolutePath()))) {
                    walk.sorted(Comparator.reverseOrder()).forEach(w -> {
                        try {
                            Files.delete(w);
                        } catch (final IOException exception) {
                            throw new UncheckedIOException(exception);
                        }
                    });
                }
            } else {
                Files.move(start, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (final IOException | UncheckedIOException exception) {
            final var message = "Moving failed";
            getLog().error(message);
            throw new MojoExecutionException(message, exception);
        }
        getLog().info("Moving successful");
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.0.1
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("file", file).add("into", into).toString();
    }

    @VisibleForTesting
    File getFile() {
        return file;
    }

    @VisibleForTesting
    void setFile(final File file) {
        assert file != null;
        this.file = file;
    }

    @VisibleForTesting
    File getInto() {
        return into;
    }

    @VisibleForTesting
    void setInto(final File into) {
        assert into != null;
        this.into = into;
    }
}
