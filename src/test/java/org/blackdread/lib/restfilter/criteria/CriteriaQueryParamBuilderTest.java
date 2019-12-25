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
import org.blackdread.lib.restfilter.filter.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

class CriteriaQueryParamBuilderTest {

    private CriteriaQueryParamBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new CriteriaQueryParamBuilder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canGuessFilterTypeFromClass() {
        Class<?> longFilterClass = LongFilter.class;
        Class<?> stringFilterClass = StringFilter.class;
        Class<?> instantFilterClass = InstantFilter.class;

        Assertions.assertTrue(RangeFilter.class.isAssignableFrom(longFilterClass));
        Assertions.assertTrue(Filter.class.isAssignableFrom(longFilterClass));

        Assertions.assertTrue(StringFilter.class.isAssignableFrom(stringFilterClass));
        Assertions.assertFalse(RangeFilter.class.isAssignableFrom(stringFilterClass));
        Assertions.assertTrue(Filter.class.isAssignableFrom(stringFilterClass));

        Assertions.assertTrue(RangeFilter.class.isAssignableFrom(instantFilterClass));
        Assertions.assertTrue(Filter.class.isAssignableFrom(instantFilterClass));
    }

    @Test
    void canLazilyBuildFormatters() {
        Map<Class, Function<Object, String>> typeFormatters = new HashMap<>();
        typeFormatters.put(Enum.class, o -> ((Enum) o).name());
        typeFormatters.put(CustomEnum2.class, o -> "hello");
        typeFormatters.put(Long.class, o -> ((Long) o).toString());
        typeFormatters.put(BigDecimal.class, o -> ((BigDecimal) o).toString());
        typeFormatters.put(Instant.class, o -> ((Instant) o).toString());

        Map<Class<? extends Filter>, FilterQueryParamFormatter> defaultFilterClassFormatterMap = new LinkedHashMap<>(16);

        Map<Class<? extends Filter>, Class> typeFormatterClassByFilterClassMap = new HashMap<>();
        typeFormatterClassByFilterClassMap.put(CustomFilterEnum.class, Enum.class);
        typeFormatterClassByFilterClassMap.put(CustomFilterEnum2.class, CustomEnum2.class);
        typeFormatterClassByFilterClassMap.put(LongFilter.class, Long.class);
        typeFormatterClassByFilterClassMap.put(BigDecimalFilter.class, BigDecimal.class);
        typeFormatterClassByFilterClassMap.put(InstantFilter.class, Instant.class);

        typeFormatterClassByFilterClassMap.forEach((filterClass, formatterTypeClass) -> {
            if (StringFilter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass, (fieldName, filter) -> FilterQueryParamUtil.buildStringFilterQueryParams(fieldName, (StringFilter) filter, typeFormatters.get(formatterTypeClass), typeFormatters.get(Boolean.class)));
            } else if (RangeFilter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass, (fieldName, filter) -> FilterQueryParamUtil.buildRangeFilterQueryParams(fieldName, (RangeFilter) filter, typeFormatters.get(formatterTypeClass), typeFormatters.get(Boolean.class)));
            } else if (Filter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass, (fieldName, filter) -> FilterQueryParamUtil.buildFilterQueryParams(fieldName, filter, typeFormatters.get(formatterTypeClass), typeFormatters.get(Boolean.class)));
            } else {
                throw new IllegalStateException("Unsupported filter class: " + filterClass.getName());
            }
        });

        Assertions.assertEquals(5, defaultFilterClassFormatterMap.size());

        LongFilter longFilter = new LongFilter();
        longFilter.setIn(List2.of(5L, 6L));
        BigDecimalFilter bigDecimalFilter = new BigDecimalFilter();
        bigDecimalFilter.setEquals(BigDecimal.TEN);
        CustomFilterEnum customFilterEnum = new CustomFilterEnum();
        customFilterEnum.setNotEquals(CustomEnum.CUSTOM_ENUM_2);
        InstantFilter instantFilter = new InstantFilter();
        Instant now = Instant.parse("2019-10-22T09:39:07Z");
        instantFilter.setEquals(now);
        instantFilter.setGreaterThan(now);
        CustomFilterEnum2 customFilterEnum2 = new CustomFilterEnum2();
        customFilterEnum2.setEquals(CustomEnum2.CUSTOM_ENUM2_1);
        customFilterEnum2.setNotEquals(CustomEnum2.CUSTOM_ENUM2_2);

        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(CustomFilterEnum.class).getFilterQueryParams("myField", longFilter));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(CustomFilterEnum.class).getFilterQueryParams("myField", bigDecimalFilter));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(CustomFilterEnum.class).getFilterQueryParams("myField", instantFilter));
        List<FilterQueryParam> resultEnum = defaultFilterClassFormatterMap.get(CustomFilterEnum.class).getFilterQueryParams("myField", customFilterEnum);
        Assertions.assertEquals("[FilterQueryParamImpl{criteriaName='myField', filterPropertyName='notEquals', paramValue='CUSTOM_ENUM_2', paramValues=[CUSTOM_ENUM_2]}]", resultEnum.toString());

        // does not throw as there is no cast
        Assertions.assertDoesNotThrow(() -> defaultFilterClassFormatterMap.get(CustomFilterEnum2.class).getFilterQueryParams("myField", longFilter));
        Assertions.assertDoesNotThrow(() -> defaultFilterClassFormatterMap.get(CustomFilterEnum2.class).getFilterQueryParams("myField", customFilterEnum));
        Assertions.assertDoesNotThrow(() -> defaultFilterClassFormatterMap.get(CustomFilterEnum2.class).getFilterQueryParams("myField", bigDecimalFilter));
        Assertions.assertDoesNotThrow(() -> defaultFilterClassFormatterMap.get(CustomFilterEnum2.class).getFilterQueryParams("myField", instantFilter));
        List<FilterQueryParam> resultCustomEnum2 = defaultFilterClassFormatterMap.get(CustomFilterEnum2.class).getFilterQueryParams("myField", customFilterEnum2);
        Assertions.assertEquals("[FilterQueryParamImpl{criteriaName='myField', filterPropertyName='equals', paramValue='hello', paramValues=[hello]}, FilterQueryParamImpl{criteriaName='myField', filterPropertyName='notEquals', paramValue='hello', paramValues=[hello]}]", resultCustomEnum2.toString());

        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(LongFilter.class).getFilterQueryParams("myField", customFilterEnum));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(LongFilter.class).getFilterQueryParams("myField", bigDecimalFilter));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(LongFilter.class).getFilterQueryParams("myField", instantFilter));
        List<FilterQueryParam> resultLong = defaultFilterClassFormatterMap.get(LongFilter.class).getFilterQueryParams("myField", longFilter);
        Assertions.assertEquals("[FilterQueryParamImpl{criteriaName='myField', filterPropertyName='in', paramValue='5,6', paramValues=[5, 6]}]", resultLong.toString());

        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(BigDecimalFilter.class).getFilterQueryParams("myField", longFilter));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(BigDecimalFilter.class).getFilterQueryParams("myField", customFilterEnum));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(BigDecimalFilter.class).getFilterQueryParams("myField", instantFilter));
        List<FilterQueryParam> resultBigDecimal = defaultFilterClassFormatterMap.get(BigDecimalFilter.class).getFilterQueryParams("myField", bigDecimalFilter);
        Assertions.assertEquals("[FilterQueryParamImpl{criteriaName='myField', filterPropertyName='equals', paramValue='10', paramValues=[10]}]", resultBigDecimal.toString());

        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(InstantFilter.class).getFilterQueryParams("myField", longFilter));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(InstantFilter.class).getFilterQueryParams("myField", customFilterEnum));
        Assertions.assertThrows(ClassCastException.class, () -> defaultFilterClassFormatterMap.get(InstantFilter.class).getFilterQueryParams("myField", bigDecimalFilter));
        List<FilterQueryParam> resultInstant = defaultFilterClassFormatterMap.get(InstantFilter.class).getFilterQueryParams("myField", instantFilter);
        Assertions.assertEquals("[FilterQueryParamImpl{criteriaName='myField', filterPropertyName='equals', paramValue='2019-10-22T09:39:07Z', paramValues=[2019-10-22T09:39:07Z]}, FilterQueryParamImpl{criteriaName='myField', filterPropertyName='greaterThan', paramValue='2019-10-22T09:39:07Z', paramValues=[2019-10-22T09:39:07Z]}]", resultInstant.toString());

    }

    enum CustomEnum {
        CUSTOM_ENUM_1,
        CUSTOM_ENUM_2
    }

    enum CustomEnum2 {
        CUSTOM_ENUM2_1,
        CUSTOM_ENUM2_2
    }

    static class CustomFilterEnum extends Filter<CustomEnum> {
    }

    static class CustomFilterEnum2 extends Filter<CustomEnum2> {
    }

    @Test
    void withTypeFormatter() throws NoSuchFieldException, IllegalAccessException {
        builder
            .withTypeFormatter(Enum.class, Enum::name)
            .withTypeFormatter(Long.class, Object::toString)
            .withTypeFormatter(BigDecimal.class, BigDecimal::toString)
            .withTypeFormatter(Instant.class, Instant::toString);

        Field field = builder.getClass().getDeclaredField("typeFormatterBySimpleTypeMap");
        field.setAccessible(true);
        Map<Class, Function<Object, String>> o = (Map<Class, Function<Object, String>>) field.get(builder);

        Assertions.assertEquals("CUSTOM_ENUM_1", o.get(Enum.class).apply(CriteriaQueryParamGenericTest.CustomEnum.CUSTOM_ENUM_1));
        Assertions.assertEquals("49996", o.get(BigDecimal.class).apply(BigDecimal.valueOf(49996)));
        Assertions.assertEquals("44", o.get(Integer.class).apply(44));
        Assertions.assertThrows(NullPointerException.class, () -> o.get(Object.class).apply(44));
    }

    @Test
    void withTypeProperlyAddedAndPassed() {
        final CriteriaQueryParam criteriaQueryParam = builder
            .withTypeFormatter(Long.class, aLong -> {
                throw new IllegalArgumentException("withTypeFormatter long");
            })
            .withBigDecimalFormatter(a -> {
                throw new IllegalArgumentException("withBigDecimalFormatter");
            })
            .withBooleanFormatter(a -> {
                throw new IllegalArgumentException("withBooleanFormatter");
            })
            .withDoubleFormatter(a -> {
                throw new IllegalArgumentException("withDoubleFormatter");
            })
            .withDurationFormatter(a -> {
                throw new IllegalArgumentException("withDurationFormatter");
            })
            .withEnumFormatter(a -> {
                throw new IllegalArgumentException("withEnumFormatter");
            })
            .withFloatFormatter(a -> {
                throw new IllegalArgumentException("withFloatFormatter");
            })
            .withInstantFormatter(a -> {
                throw new IllegalArgumentException("withInstantFormatter");
            })
            .withLocalDateFormatter(a -> {
                throw new IllegalArgumentException("withLocalDateFormatter");
            })
            .withLocalDateTimeFormatter(a -> {
                throw new IllegalArgumentException("withLocalDateTimeFormatter");
            })
            .withUUIDFormatter(a -> {
                throw new IllegalArgumentException("withUUIDFormatter");
            })
            .withZonedDateTimeFormatter(a -> {
                throw new IllegalArgumentException("withZonedDateTimeFormatter");
            })
            .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setEquals(1L)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new BigDecimalFilter().setEquals(BigDecimal.TEN)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new BooleanFilter().setEquals(true)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new DoubleFilter().setEquals(4.0)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new DurationFilter().setEquals(Duration.ofDays(5))));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new CustomFilterEnum().setEquals(CustomEnum.CUSTOM_ENUM_2)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new FloatFilter().setEquals(1.0f)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new InstantFilter().setEquals(Instant.now())));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new LocalDateFilter().setEquals(LocalDate.now())));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new UUIDFilter().setEquals(UUID.randomUUID())));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new ZonedDateTimeFilter().setEquals(ZonedDateTime.now())));
    }

    @Test
    void withCustomQueryParamFormatterMap() {
        Map<Class<? extends Filter>, FilterQueryParamFormatter> map = new HashMap<>();
        map.put(LongFilter.class, (fieldName, filter) -> {
            throw new IllegalArgumentException("long");
        });
        map.put(StringFilter.class, (fieldName, filter) -> {
            throw new IllegalArgumentException("string");
        });
        final CriteriaQueryParam criteriaQueryParam = builder
            .withCustomQueryParamFormatterMap(map)
            .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setEquals(1L)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new StringFilter().setEquals("a")));

        final CriteriaQueryParam criteriaQueryParam2 = builder
            .withCustomQueryParamFormatterMap(new HashMap<>())
            .build();

        Assertions.assertDoesNotThrow(() -> criteriaQueryParam2.getFilterQueryParams("a", new LongFilter().setEquals(1L)));
        Assertions.assertDoesNotThrow(() -> criteriaQueryParam2.getFilterQueryParams("a", new StringFilter().setEquals("a")));
    }

    @Test
    void putCustomQueryParamFormatter() {
        final CriteriaQueryParam criteriaQueryParam = builder
            .putCustomQueryParamFormatter(LongFilter.class, (fieldName, filter) -> {
                throw new IllegalArgumentException("long");
            })
            .putCustomQueryParamFormatter(StringFilter.class, (fieldName, filter) -> {
                throw new IllegalArgumentException("string");
            })
            .build();
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setEquals(1L)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new StringFilter().setEquals("a")));
    }

    @Test
    void removeCustomQueryParamFormatter() {
        final CriteriaQueryParam criteriaQueryParam = builder
            .putCustomQueryParamFormatter(LongFilter.class, (fieldName, filter) -> {
                throw new IllegalArgumentException("long");
            })
            .putCustomQueryParamFormatter(StringFilter.class, (fieldName, filter) -> {
                throw new IllegalArgumentException("string");
            })
            .removeCustomQueryParamFormatter(LongFilter.class)
            .build();
        Assertions.assertDoesNotThrow(() -> criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setEquals(1L)));
        Assertions.assertThrows(IllegalArgumentException.class, () -> criteriaQueryParam.getFilterQueryParams("a", new StringFilter().setEquals("a")));
    }
}
