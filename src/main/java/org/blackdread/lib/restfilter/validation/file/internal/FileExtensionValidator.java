/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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

import org.apache.commons.lang3.StringUtils;
import org.blackdread.lib.restfilter.validation.file.FileExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.regex.Pattern;

/**
 * <p>Created on 2017/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class FileExtensionValidator {

    private static final Logger log = LoggerFactory.getLogger(FileExtensionValidator.class);

    private static final Pattern EXTENSION_REGEX = Pattern.compile("^[A-Za-z]+$");

    protected String[] allowedExtensions;

    protected boolean isInverse;

    protected boolean isIgnoreCase;

    public void initialize(final FileExtension constraintAnnotation) {
        allowedExtensions = constraintAnnotation.value();
        isInverse = constraintAnnotation.inverse();
        isIgnoreCase = constraintAnnotation.isIgnoreCase();
        validateParameters();
    }

    protected String getExtension(final String filename) {
        final String extension = getFileExtension(filename);
        if (StringUtils.isBlank(extension)) {
            throw new IllegalStateException("File uploaded did not have an extension");
        }
        return extension;
    }

    private void validateParameters() {
        if (allowedExtensions == null)
            throw new IllegalArgumentException("allowedExtensions cannot be null");
        for (String s : allowedExtensions) {
            if (s == null)
                throw new IllegalArgumentException("Array of allowed extensions cannot contain null values");
            if (!EXTENSION_REGEX.matcher(s).matches())
                throw new IllegalArgumentException("Extension passed does not respect pattern: " + EXTENSION_REGEX.pattern());
        }
    }

    private static String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
