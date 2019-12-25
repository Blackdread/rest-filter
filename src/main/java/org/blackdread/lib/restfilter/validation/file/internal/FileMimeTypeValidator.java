/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.blackdread.lib.restfilter.validation.file.internal;

import org.apache.commons.lang3.NotImplementedException;
import org.blackdread.lib.restfilter.validation.file.FileMimeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.regex.Pattern;

/**
 * <p>Created on 2017/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class FileMimeTypeValidator {

    private static final Logger log = LoggerFactory.getLogger(FileMimeTypeValidator.class);

    // TODO to check
    private static final Pattern MIME_TYPE_REGEX = Pattern.compile("^[A-Za-z]+/[A-Za-z]+$");

    protected String[] allowedMimeTypes;

    protected boolean isInverse;

    public void initialize(final FileMimeType constraintAnnotation) {
        allowedMimeTypes = constraintAnnotation.value();
        isInverse = constraintAnnotation.inverse();
        validateParameters();
    }

    protected String getMimeType(final Path filePath) {
        throw new NotImplementedException("Not done for now, will see later");
    }

    private void validateParameters() {
        if (allowedMimeTypes == null)
            throw new IllegalArgumentException("allowedMimeTypes cannot be null");
        for (String s : allowedMimeTypes) {
            if (s == null)
                throw new IllegalArgumentException("Array of allowed mime types cannot contain null values");
            if (!MIME_TYPE_REGEX.matcher(s).matches())
                throw new IllegalArgumentException("Mime type passed does not respect pattern: " + MIME_TYPE_REGEX.pattern());
        }
    }
}
