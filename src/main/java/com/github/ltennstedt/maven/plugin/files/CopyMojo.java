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
package com.github.ltennstedt.maven.plugin.files;

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
 * Mojo for copying files and directories
 *
 * @author Lars Tennstedt
 * @since 1
 *
 */
@Mojo(name = "copy")
public final class CopyMojo extends AbstractMojo {
    /**
     * Source file or directory
     */
    @Parameter(required = true)
    private File from;

    /**
     * Target directory
     */
    @Parameter(required = true)
    private File into;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        check();
        getLog().info(new StringBuilder("Copy ").append(from.getAbsolutePath()).append(" into ")
            .append(into.getAbsolutePath()).toString());
        try {
            if (into.isFile()) {
                FileUtils.copyFileToDirectory(from, into);
            } else {
                FileUtils.copyDirectory(from, into);
            }
        } catch (final IOException exception) {
            final String message = "copying failed";
            getLog().error(message);
            throw new MojoExecutionException(message, exception);
        }
    }

    private void check() throws MojoExecutionException {
        if (!from.exists()) {
            final String message = "from does not exists";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
        if (!from.canRead()) {
            final String message = "from not readable";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
        if (into.exists()) {
            if (into.isFile()) {
                final String message = "into exists and is a file";
                getLog().error(message);
                throw new MojoExecutionException(message);
            }
            if (!into.canWrite()) {
                final String message = "into not writeable";
                getLog().error(message);
                throw new MojoExecutionException(message);
            }
        }
        if (!into.mkdirs()) {
            final String message = "Directories could not be created";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("from", from).append("into", into).toString();
    }
}
