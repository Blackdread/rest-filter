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
import org.blackdread.lib.restfilter.filter.Filter;
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
class CriteriaQueryParamExtraTest {

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
    void withEnumFilter(boolean matchSubclass) {
        var criteria = new EnumFilterCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("enumField.equals", "ENUM_VAL_1");
        expected.add("enum.equals", "ENUM_VAL_1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumValue(boolean matchSubclass) {
        var criteria = new EnumValueCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("enumField", "ENUM_VAL_1");
        expected.add("enum", "ENUM_VAL_1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumArray(boolean matchSubclass) {
        var criteria = new EnumArrayCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<String, String>();
        expected.add("enumArray", "ENUM_VAL_1");
        expected.add("enumArray", "ENUM_VAL_2");
        expected.add("array", "ENUM_VAL_1");
        expected.add("array", "ENUM_VAL_2");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void withEnumList(boolean matchSubclass) {
        var criteria = new EnumListCriteria();

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(criteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("enumList", "ENUM_VAL_1");
        expected.add("enumList", "ENUM_VAL_2");
        expected.add("list", "ENUM_VAL_1");
        expected.add("list", "ENUM_VAL_2");

        assertEquals(expected, result);
    }

    static class EnumFilterCriteria {

        public EnumFilterCriteria() {
            final MyEnumFilter filter = new MyEnumFilter();
            filter.setEquals(MyEnum.ENUM_VAL_1);
            enumField = filter;
        }

        MyEnumFilter enumField;

        @CriteriaInclude
        public MyEnumFilter getEnum() {
            return enumField;
        }

    }

    static class EnumValueCriteria {

        @CriteriaInclude
        MyEnum enumField = MyEnum.ENUM_VAL_1;

        @CriteriaInclude
        public MyEnum getEnum() {
            return MyEnum.ENUM_VAL_1;
        }

    }

    static class EnumArrayCriteria {

        @CriteriaInclude(MyEnum.class)
        MyEnum[] enumArray = new MyEnum[]{MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2};

        @CriteriaInclude(MyEnum.class)
        public MyEnum[] getArray() {
            return new MyEnum[]{MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2};
        }

    }

    static class EnumListCriteria {

        @CriteriaInclude(MyEnum.class)
        List<MyEnum> enumList = List.of(MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2);

        @CriteriaInclude(MyEnum.class)
        public List<MyEnum> getList() {
            return List.of(MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2);
        }

    }

    enum MyEnum {
        ENUM_VAL_1,
        ENUM_VAL_2,
        ENUM_VAL_3
    }

    static class MyEnumFilter extends Filter<MyEnum> {

    }

}
