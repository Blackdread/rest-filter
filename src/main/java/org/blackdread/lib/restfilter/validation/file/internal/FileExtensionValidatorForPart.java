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

import org.blackdread.lib.restfilter.validation.file.FileExtension;

import javax.servlet.http.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * <p>Created on 2017/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public class FileExtensionValidatorForPart extends FileExtensionValidator implements ConstraintValidator<FileExtension, Part> {

    @Override
    public boolean isValid(final Part value, final ConstraintValidatorContext context) {
        if (value == null)
            return true;
        final String extension = getExtension(value.getSubmittedFileName());

        final boolean match = Arrays.stream(allowedExtensions)
            .anyMatch(allowedExtension -> isIgnoreCase ? allowedExtension.equalsIgnoreCase(extension) : allowedExtension.equals(extension));

        if (isInverse)
            return !match;
        return match;
    }
}