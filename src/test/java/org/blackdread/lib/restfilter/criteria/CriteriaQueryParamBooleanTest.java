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
import org.blackdread.lib.restfilter.filter.BooleanFilter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamBooleanTest {

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
    void withBooleanFilter(boolean matchSubclass) {
        var criteria = new FilterCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("filterField.equals", "true");
        expected.add("filter.equals", "true");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withBooleanValue(boolean matchSubclass) {
        var criteria = new ValueCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("valueField", "true");
        expected.add("value", "true");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withBooleanArray(boolean matchSubclass) {
        var criteria = new ArrayCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<String, String>();
        expected.add("arrayField", "true");
        expected.add("arrayField", "false");
        expected.add("array", "true");
        expected.add("array", "false");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withBooleanList(boolean matchSubclass) {
        var criteria = new ListCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("fieldList", "true");
        expected.add("fieldList", "false");
        expected.add("list", "true");
        expected.add("list", "false");

        assertEquals(expected, result);
    }

    static class FilterCriteria {

        public FilterCriteria() {
            final BooleanFilter filter = new BooleanFilter();
            filter.setEquals(true);
            filterField = filter;
        }

        BooleanFilter filterField;

        @CriteriaInclude
        public BooleanFilter getFilter() {
            return filterField;
        }

    }

    static class ValueCriteria {

        @CriteriaInclude
        Boolean valueField = true;

        @CriteriaInclude
        public Boolean getValue() {
            return true;
        }

    }

    static class ArrayCriteria {

        @CriteriaInclude(Boolean.class)
        Boolean[] arrayField = new Boolean[]{true, false};

        @CriteriaInclude(Boolean.class)
        public Boolean[] getArray() {
            return new Boolean[]{true, false};
        }

    }

    static class ListCriteria {

        @CriteriaInclude(Boolean.class)
        List<Boolean> fieldList = List.of(true, false);

        @CriteriaInclude(Boolean.class)
        public List<Boolean> getList() {
            return List.of(true, false);
        }

    }

}
