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
import org.blackdread.lib.restfilter.filter.InstantFilter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamInstantTest {

    private CriteriaQueryParamBuilder builder;

    private CriteriaQueryParam criteriaQueryParam;

    public static final Instant instant1 = Instant.parse("2019-10-27T20:10:10Z");
    public static final Instant instant2 = Instant.parse("2020-02-02T02:10:10Z");

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
    void withEnumFilter(boolean matchSubclass) {
        var criteria = new FilterCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("filterField.equals", "2019-10-27T20:10:10Z");
        expected.add("filter.equals", "2019-10-27T20:10:10Z");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumValue(boolean matchSubclass) {
        var criteria = new ValueCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("valueField", "2019-10-27T20:10:10Z");
        expected.add("value", "2019-10-27T20:10:10Z");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumArray(boolean matchSubclass) {
        var criteria = new ArrayCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<String, String>();
        expected.add("arrayField", "2019-10-27T20:10:10Z");
        expected.add("arrayField", "2020-02-02T02:10:10Z");
        expected.add("array", "2019-10-27T20:10:10Z");
        expected.add("array", "2020-02-02T02:10:10Z");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumList(boolean matchSubclass) {
        var criteria = new ListCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("fieldList", "2019-10-27T20:10:10Z");
        expected.add("fieldList", "2020-02-02T02:10:10Z");
        expected.add("list", "2019-10-27T20:10:10Z");
        expected.add("list", "2020-02-02T02:10:10Z");

        assertEquals(expected, result);
    }

    static class FilterCriteria {

        public FilterCriteria() {
            final InstantFilter filter = new InstantFilter();
            filter.setEquals(instant1);
            filterField = filter;
        }

        InstantFilter filterField;

        @CriteriaInclude
        public InstantFilter getFilter() {
            return filterField;
        }

    }

    static class ValueCriteria {

        @CriteriaInclude
        Instant valueField = instant1;

        @CriteriaInclude
        public Instant getValue() {
            return instant1;
        }

    }

    static class ArrayCriteria {

        @CriteriaInclude(Instant.class)
        Instant[] arrayField = new Instant[]{instant1, instant2};

        @CriteriaInclude(Instant.class)
        public Instant[] getArray() {
            return new Instant[]{instant1, instant2};
        }

    }

    static class ListCriteria {

        @CriteriaInclude(Instant.class)
        List<Instant> fieldList = List.of(instant1, instant2);

        @CriteriaInclude(Instant.class)
        public List<Instant> getList() {
            return List.of(instant1, instant2);
        }

    }

}
