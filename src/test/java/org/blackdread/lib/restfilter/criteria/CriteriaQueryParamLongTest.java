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
package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.blackdread.lib.restfilter.util.MultiValueMap;
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
class CriteriaQueryParamLongTest {

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
    void withLongFilter(boolean matchSubclass) {
        FilterCriteria criteria = new FilterCriteria();

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("filterField.equals", "1");
        expected.add("filter.equals", "1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withLongValue(boolean matchSubclass) {
        ValueCriteria criteria = new ValueCriteria();

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("valueField", "1");
        expected.add("value", "1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withLongArray(boolean matchSubclass) {
        ArrayCriteria criteria = new ArrayCriteria();

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("arrayField", "1");
        expected.add("arrayField", "2");
        expected.add("array", "1");
        expected.add("array", "2");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withLongList(boolean matchSubclass) {
        ListCriteria criteria = new ListCriteria();

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("fieldList", "1");
        expected.add("fieldList", "2");
        expected.add("list", "1");
        expected.add("list", "2");

        assertEquals(expected, result);
    }

    static class FilterCriteria {

        public FilterCriteria() {
            final LongFilter filter = new LongFilter();
            filter.setEquals(1L);
            filterField = filter;
        }

        LongFilter filterField;

        @CriteriaInclude
        public LongFilter getFilter() {
            return filterField;
        }

    }

    static class ValueCriteria {

        @CriteriaInclude
        Long valueField = 1L;

        @CriteriaInclude
        public Long getValue() {
            return 1L;
        }

    }

    static class ArrayCriteria {

        @CriteriaInclude(Long.class)
        Long[] arrayField = new Long[]{1L, 2L};

        @CriteriaInclude(Long.class)
        public Long[] getArray() {
            return new Long[]{1L, 2L};
        }

    }

    static class ListCriteria {

        @CriteriaInclude(Long.class)
        List<Long> fieldList = List2.of(1L, 2L);

        @CriteriaInclude(Long.class)
        public List<Long> getList() {
            return List2.of(1L, 2L);
        }

    }

}
