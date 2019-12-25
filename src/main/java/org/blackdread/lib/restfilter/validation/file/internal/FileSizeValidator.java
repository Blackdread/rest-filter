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

import org.blackdread.lib.restfilter.validation.file.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2017/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public abstract class FileSizeValidator {

    private static final Logger log = LoggerFactory.getLogger(FileSizeValidator.class);

    protected long minBytes;

    protected long maxBytes;

    public void initialize(final FileSize constraintAnnotation) {
        minBytes = constraintAnnotation.min();
        maxBytes = constraintAnnotation.max();
        validateParameters();
    }

    private void validateParameters() {
        if (minBytes < 0)
            throw new IllegalArgumentException("Min cannot be negative");
        if(maxBytes < 0)
            throw new IllegalArgumentException("Max cannot be negative");
        if(maxBytes < minBytes)
            throw new IllegalArgumentException("Max cannot be lower than minBytes");
    }
}
