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
package org.blackdread.lib.restfilter.validation;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.criteria.Criteria;
import org.blackdread.lib.restfilter.criteria.CriteriaUtil;
import org.blackdread.lib.restfilter.criteria.CriteriaUtilTest;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.validation.criteria.InForbidden;
import org.blackdread.lib.restfilter.validation.criteria.internal.InForbiddenValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
class ValidationInForbiddenTest {

    private static final Logger log = LoggerFactory.getLogger(ValidationInForbiddenTest.class);

    private Validator validator;

    private MyCriteria myCriteria;

    @BeforeAll
    static void setUpClass() {
    }

    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.ENGLISH);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        myCriteria = new MyCriteria();
    }

    @Test
    void hasViolation() {
        myCriteria.longFilter = CriteriaUtil.buildInCriteria(List2.of(5L, 6L));

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(1, constraintViolations.size());
        assertEquals("In filter is not allowed", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void hasNoViolationForOthers() {
        myCriteria.longFilter = new LongFilter();
        CriteriaUtilTest.fillAll(myCriteria.longFilter);
        myCriteria.longFilter.setIn(null);

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(Collections.emptySet(), constraintViolations);
    }

    @Test
    void violationCanBeTranslated() {
        Locale.setDefault(Locale.FRANCE);

        myCriteria.longFilter = CriteriaUtil.buildInCriteria(List2.of(5L, 6L));

        final Set<ConstraintViolation<MyCriteria>> constraintViolations = validator.validate(myCriteria);

        assertEquals(1, constraintViolations.size());
        assertEquals("Le filtre In n'est pas autorisé", constraintViolations.iterator().next().getMessage());
    }

    @Test
    void validatorAcceptNull() {
        final InForbiddenValidator validator = new InForbiddenValidator();
        final boolean valid = validator.isValid(null, null);
        assertTrue(valid);
    }


    private static class MyCriteria implements Criteria {

        @InForbidden
        private LongFilter longFilter;

        @Override
        public Criteria copy() {
            return null;
        }
    }
}
