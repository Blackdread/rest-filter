/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.validation.criteria.internal;

import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.validation.criteria.GreaterThanOrEqualForbidden;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class GreaterThanOrEqualForbiddenValidator implements ConstraintValidator<GreaterThanOrEqualForbidden, RangeFilter<?>> {

//    private static final Logger log = LoggerFactory.getLogger(GreaterThanOrEqualForbiddenValidator.class);

    @Override
    public void initialize(GreaterThanOrEqualForbidden constraintAnnotation) {
    }

    @Override
    public boolean isValid(RangeFilter<?> value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return value.getGreaterThanOrEqual() == null;
    }

}
