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

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Mojo for moving files and directories
 *
 * @author Lars Tennstedt
 * @since 1
 */
@Mojo(name = "move")
public final class MoveMojo extends AbstractMojo {
    /**
     * Source file or directory
     */
    @Parameter(required = true)
    File file;

    /**
     * Target directory
     */
    @Parameter(required = true)
    File into;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        checkFile();
        checkInto();
        getLog().info(new StringBuilder("Move ").append(file.getAbsolutePath()).append(" into ")
                .append(into.getAbsolutePath()).toString());
        try {
            if (file.isFile()) {
                FileUtils.copyFileToDirectory(file, into);
                file.delete();
            } else {
                FileUtils.copyDirectory(file, into);
                FileUtils.deleteDirectory(file);
            }
        } catch (final IOException exception) {
            final String message = "Moving failed";
            getLog().error(message);
            throw new MojoExecutionException(message, exception);
        }
        getLog().info("Moving successful");
    }

    void checkFile() throws MojoExecutionException {
        if (!file.exists()) {
            final String message = "file does not exist";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
        if (!file.canWrite()) {
            final String message = "file not writable";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
    }

    void checkInto() throws MojoExecutionException {
        if (into.exists()) {
            if (into.isFile()) {
                final String message = "into is a file";
                getLog().error(message);
                throw new MojoExecutionException(message);
            }
            if (!into.canWrite()) {
                final String message = "into not writable";
                getLog().error(message);
                throw new MojoExecutionException(message);
            }
        } else if (!into.mkdirs()) {
            final String message = "Directories could not be created";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("file", file).append("into", into).toString();
    }
}
