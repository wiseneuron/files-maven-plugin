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

import com.github.ltennstedt.maven.plugin.files.util.Preconditions;
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
 */
@Mojo(name = "copy")
public final class CopyMojo extends AbstractMojo {
    /**
     * Source file or directory
     */
    @Parameter(required = true)
    private File file;

    /**
     * Target directory
     */
    @Parameter(required = true)
    private File into;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Preconditions.checkFile(file, getLog());
        Preconditions.checkInto(into, getLog());
        getLog().info(new StringBuilder("Copy ").append(file.getAbsolutePath()).append(" into ")
            .append(into.getAbsolutePath()).toString());
        try {
            if (file.isFile()) {
                FileUtils.copyFileToDirectory(file, into);
            } else {
                FileUtils.copyDirectory(file, into);
            }
        } catch (final IOException exception) {
            final String message = "Copying failed";
            getLog().error(message);
            throw new MojoExecutionException(message, exception);
        }
        getLog().info("Copying successful");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("file", file).append("into", into).toString();
    }

    public File getFile() {
        return file;
    }

    public void setFile(final File file) {
        this.file = file;
    }

    public File getInto() {
        return into;
    }

    public void setInto(final File into) {
        this.into = into;
    }
}
