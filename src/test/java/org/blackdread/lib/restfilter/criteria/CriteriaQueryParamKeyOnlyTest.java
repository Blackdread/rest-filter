/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;
import org.blackdread.lib.restfilter.criteria.parser.CriteriaFieldParserUtil;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test if can have a way to specify a query param without any value -&gt; only key
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamKeyOnlyTest {

    private CriteriaQueryParamBuilder builder;

    private CriteriaQueryParam criteriaQueryParam;

    @BeforeEach
    void setUp() {
        builder = new CriteriaQueryParamBuilder();

        criteriaQueryParam = builder.build();
    }

    @AfterEach
    void tearDown() {
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void canHaveKeyOnly(boolean matchSubclass) {
        var criteria = new MyCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.addAll("boolTrue", Collections.emptyList());
        expected.addAll("boolean", Collections.emptyList());

        assertEquals(expected, result);
    }

    @Test
    void throwsWhenKeyOnlyOnWrongType() {
        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> CriteriaFieldParserUtil.getCriteriaData(Throws1Criteria.class));
        final IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> CriteriaFieldParserUtil.getCriteriaData(Throws2Criteria.class));
        final IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> CriteriaFieldParserUtil.getCriteriaData(Throws3Criteria.class));
        assertEquals("Key only option is only available on boolean field and method return type", ex1.getMessage());
        assertEquals("Key only option is only available on boolean field and method return type", ex2.getMessage());
        assertEquals("Key only option is only available on boolean field and method return type", ex3.getMessage());
    }

    static class MyCriteria {

        @CriteriaInclude(keyOnly = true)
        Boolean boolTrue = true;

        @CriteriaInclude(keyOnly = true)
        Boolean boolFalse = false;

        @CriteriaInclude(keyOnly = true)
        public Boolean getBoolean() {
            return true;
        }

        @CriteriaInclude(keyOnly = true)
        public Boolean getBooleanFalse() {
            return false;
        }

    }

    static class Throws1Criteria {

        @CriteriaInclude(keyOnly = true)
        Instant instant = Instant.now();

    }

    static class Throws2Criteria {

        @CriteriaInclude(keyOnly = true)
        Long valueLong = 1L;

    }

    static class Throws3Criteria {

        @CriteriaInclude(keyOnly = true)
        public StringFilter getStringFilter() {
            return new StringFilter();
        }

    }

}
