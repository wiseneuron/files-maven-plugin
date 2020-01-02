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
import java.util.Comparator;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Mojo for deleting files and directories
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@Beta
@Mojo(name = "delete")
public final class DeleteMojo extends AbstractMojo {
    /**
     * File or directory which will be deleted
     */
    @Parameter(required = true)
    private File file;

    /**
     * {@inheritDoc}
     *
     * @since 0.0.1
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Deleting " + file.getAbsolutePath());
        try (var walk = Files.walk(Path.of(file.getAbsolutePath()))) {
            walk.sorted(Comparator.reverseOrder()).forEach(w -> {
                try {
                    Files.delete(w);
                } catch (final IOException exception) {
                    throw new UncheckedIOException(exception);
                }
            });
        } catch (final IOException | UncheckedIOException exception) {
            final var message = "Deleting failed";
            getLog().error(message);
            throw new MojoExecutionException(message, exception);
        }
        getLog().info("Deletion successful");
    }

    /**
     * {@inheritDoc}
     *
     * @since 0.0.1
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("file", file).toString();
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
}
