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
 * Mojo for deleting files and directories
 *
 * @author Lars Tennstedt
 * @since 1
 *
 */
@Mojo(name = "delete")
public final class DeleteMojo extends AbstractMojo {
    /**
     * File or directory which will be deleted
     */
    @Parameter(required = true)
    private File file;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        check();
        getLog().info(new StringBuilder("Delete ").append(file.getAbsolutePath()).toString());
        delete();
        getLog().info("Deletion successful");
    }

    private void check() throws MojoExecutionException {
        if (!file.exists()) {
            final String message = "delete does not exists";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
        if (!file.canWrite()) {
            final String message = "from not writable";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }
    }

    private void delete() throws MojoExecutionException {
        if (file.isFile()) {
            if (!file.delete()) {
                final String message = "Deletion failed";
                getLog().error(message);
                throw new MojoExecutionException(message);
            }
        } else {
            try {
                FileUtils.deleteDirectory(file);
            } catch (final IOException exception) {
                final String message = "Deletion failed";
                getLog().error(message);
                throw new MojoExecutionException(message, exception);
            }
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("file", file).toString();
    }
}
