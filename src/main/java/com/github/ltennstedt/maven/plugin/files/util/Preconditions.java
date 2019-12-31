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

import static java.util.Objects.requireNonNull;

import com.google.common.annotations.Beta;
import java.io.File;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * Preconditions
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@Beta
public final class Preconditions {
    private Preconditions() {
    }

    /**
     * Checks {@code file}
     *
     * @param file {@code file}
     * @param log {@link Log}
     * @throws MojoExecutionException if {@code !file.exists || !file.canRead}
     * @throws NullPointerException if {@code file == null}
     * @throws NullPointerException if {@code log == null}
     * @since 0.0.1
     */
    public static void checkFile(final File file, final Log log) throws MojoExecutionException {
        requireNonNull(file, "file");
        requireNonNull(log, "log");
        if (!file.exists()) {
            final String message = "file does not exist";
            log.error(message);
            throw new MojoExecutionException(message);
        } else if (!file.canRead()) {
            final String message = "file not readable";
            log.error(message);
            throw new MojoExecutionException(message);
        }
    }

    /**
     * Checks {@code into}
     *
     * @param into {@code into}
     * @param log {@link Log}
     * @throws MojoExecutionException if {@code into.isFile || !into.canWrite}
     * @throws NullPointerException if {@code into == null}
     * @throws NullPointerException if {@code log == null}
     * @since 0.0.1
     */
    public static void checkInto(final File into, final Log log) throws MojoExecutionException {
        requireNonNull(into, "into");
        requireNonNull(log, "log");
        if (into.exists()) {
            if (into.isFile()) {
                final String message = "into is a file";
                log.error(message);
                throw new MojoExecutionException(message);
            }
            if (!into.canWrite()) {
                final String message = "into not writable";
                log.error(message);
                throw new MojoExecutionException(message);
            }
        }
    }
}
