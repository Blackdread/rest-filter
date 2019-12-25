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
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.blackdread.lib.restfilter.util.MultiValueMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamTest {

    private final Instant instant = Instant.parse("2019-10-19T15:50:10Z");
    private final LocalDate localDate = LocalDate.parse("2019-10-19");
    private final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
    private final Duration duration = Duration.parse("P2DT20.345S");

    private final Function<Instant, String> instantFormatter = DateTimeFormatter.ISO_INSTANT::format;
    private final Function<LocalDate, String> localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE::format;
    private final Function<LocalDateTime, String> localDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME::format;
    private final Function<ZonedDateTime, String> zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME::format;
    private final Function<Duration, String> durationFormatter = Duration::toString;

    private MyCriteria myCriteria;

    private CriteriaQueryParamBuilder builder;

    private CriteriaQueryParam criteriaQueryParam;

    @BeforeEach
    void setUp() {
        myCriteria = new MyCriteria();

        builder = new CriteriaQueryParamBuilder();

        criteriaQueryParam = builder.build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toInstant() {
        assertEquals("2019-10-19T15:50:10Z", instantFormatter.apply(instant));
        assertEquals("2019-10-19", localDateFormatter.apply(localDate));
        assertEquals("2019-10-19T17:50:10", localDateTimeFormatter.apply(localDateTime));
        assertEquals("2007-12-03T10:15:30+01:00[Europe/Paris]", zonedDateTimeFormatter.apply(zonedDateTime));
        assertEquals("PT48H20.345S", durationFormatter.apply(duration));

        assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDate));
        assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDateTime));
        assertEquals("2007-12-03T09:15:30Z", DateTimeFormatter.ISO_INSTANT.format(zonedDateTime));
    }

    @Test
    void noErrorWhenCriteriaEmpty() {
        MultiValueMap<String, String> result1 = criteriaQueryParam.buildQueryParamsMap(myCriteria);
        MultiValueMap<String, String> result2 = criteriaQueryParam.buildQueryParamsMap((Object) myCriteria);
        MultiValueMap<String, String> result3 = criteriaQueryParam.buildQueryParamsMap("theName", new LongFilter());
        List<FilterQueryParam> result4 = criteriaQueryParam.getFilterQueryParams(new HashMap<>());
        List<FilterQueryParam> result5 = criteriaQueryParam.getFilterQueryParams("theName", new LongFilter());

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        assertEquals(expected, result1);
        assertEquals(expected, result2);
        assertEquals(expected, result3);

        ArrayList<FilterQueryParam> expected2 = new ArrayList<>();
        assertEquals(expected2, result4);
        assertEquals(expected2, result5);
    }

    @Test
    void canBuildQueryParamsFromCriteria() {
        myCriteria.duration = new DurationFilter();
        myCriteria.duration.setEquals(Duration.ofMinutes(55));

        myCriteria.id = CriteriaUtil.buildEqualsCriteria(5L);
        myCriteria.name = CriteriaUtil.buildEqualsCriteria("aaa");
        myCriteria.name = CriteriaUtil.buildInCriteria(myCriteria.name, List2.of("bbb", "ccc"));
        myCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        final MultiValueMap<String, String> result = criteriaQueryParam.buildQueryParamsMap(myCriteria);

        final MultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("duration.equals", "PT55M");
        expected.add("name.equals", "aaa");
        expected.add("name.in", "bbb");
        expected.add("name.in", "ccc");
        expected.add("active.equals", "false");
        expected.add("id.equals", "5");

        assertEquals(expected, result);
    }

    @Test
    void canBuildQueryParamsFromObject() {
        myCriteria.duration = new DurationFilter();
        myCriteria.duration.setEquals(Duration.ofMinutes(55));

        myCriteria.id = CriteriaUtil.buildEqualsCriteria(5L);
        myCriteria.name = CriteriaUtil.buildEqualsCriteria("aaa");
        myCriteria.name = CriteriaUtil.buildInCriteria(myCriteria.name, List2.of("bbb", "ccc"));
        myCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        final MultiValueMap<String, String> result = criteriaQueryParam.buildQueryParamsMap((Object) myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("duration.equals", "PT55M");
        expected.add("name.equals", "aaa");
        expected.add("name.in", "bbb");
        expected.add("name.in", "ccc");
        expected.add("active.equals", "false");
        expected.add("id.equals", "5");

        assertEquals(expected, result);
    }

    @Test
    void customFormatterMatchByClassOnlyAndNoSubclass() {
        CustomLongFilter filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        criteriaQueryParam = builder
            .putCustomQueryParamFormatter(LongFilter.class, (fieldName, filter1) -> {
                throw new IllegalStateException("Will not be called");
            })
            .build();

        final UnsupportedFilterForQueryParamException exception = assertThrows(UnsupportedFilterForQueryParamException.class, () -> criteriaQueryParam.buildQueryParamsMap(myCriteria));
        assertEquals("Field/method 'id' did not match any formatter for filter 'class org.blackdread.lib.restfilter.criteria.CriteriaQueryParamTest$CustomLongFilter'", exception.getMessage());
        assertEquals("id", exception.getCriteriaName());
        assertEquals(filter, exception.getFilter());
        assertSame(filter, exception.getFilter());
    }

    @Test
    void defaultFormatterMatchByClassOnlyAndNoSubclass() {
        CustomLongFilter filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(true)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("id.equals", "1");
        expected.add("id.notEquals", "1");
        expected.add("id.in", "1");
        expected.add("id.in", "2");
        expected.add("id.notIn", "1");
        expected.add("id.notIn", "2");
        expected.add("id.specified", "true");
        expected.add("id.greaterThan", "1");
        expected.add("id.greaterThanOrEqual", "1");
        expected.add("id.lessThan", "1");
        expected.add("id.lessThanOrEqual", "1");

        assertEquals(expected, result);
    }

    @Test
    void defaultFormatterCanMatchSubclass() {
        CustomLongFilter filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        final UnsupportedFilterForQueryParamException exception = assertThrows(UnsupportedFilterForQueryParamException.class, () -> criteriaQueryParam.buildQueryParamsMap(myCriteria));
        assertEquals("Field/method 'id' did not match any formatter for filter 'class org.blackdread.lib.restfilter.criteria.CriteriaQueryParamTest$CustomLongFilter'", exception.getMessage());
    }

    private static class CustomLongFilter extends LongFilter {

    }

    @Test
    void buildWithManyEnum() {
        // todo configure multi enum type in builder
        MyEnumFilter filter = new MyEnumFilter();
        filter.setEquals(MyEnum.ENUM_VAL_1);

        MultiValueMap<String, String> result = builder
            .withTypeFormatter(MyEnum.class, myEnum -> "prefix-" + myEnum.name())
            .build()
            .buildQueryParamsMap("myEnum", filter);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();

        expected.add("myEnum.equals", "prefix-ENUM_VAL_1");
        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithEnum(boolean matchSubclass) {
        MyEnumFilter filter = new MyEnumFilter();
        CriteriaUtilTest.fillAll(filter, MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2);

        myCriteria.myEnum = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("myEnum.equals", "ENUM_VAL_1");
        expected.add("myEnum.notEquals", "ENUM_VAL_1");
        expected.add("myEnum.in", "ENUM_VAL_1");
        expected.add("myEnum.in", "ENUM_VAL_2");
        expected.add("myEnum.notIn", "ENUM_VAL_1");
        expected.add("myEnum.notIn", "ENUM_VAL_2");
        expected.add("myEnum.specified", "true");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithLong(boolean matchSubclass) {
        LongFilter filter = new LongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("id.equals", "1");
        expected.add("id.notEquals", "1");
        expected.add("id.in", "1");
        expected.add("id.in", "2");
        expected.add("id.notIn", "1");
        expected.add("id.notIn", "2");
        expected.add("id.specified", "true");
        expected.add("id.greaterThan", "1");
        expected.add("id.greaterThanOrEqual", "1");
        expected.add("id.lessThan", "1");
        expected.add("id.lessThanOrEqual", "1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithInteger(boolean matchSubclass) {
        IntegerFilter filter = new IntegerFilter();
        CriteriaUtilTest.fillAll(filter, 1, 2);

        myCriteria.count = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("count.equals", "1");
        expected.add("count.notEquals", "1");
        expected.add("count.in", "1");
        expected.add("count.in", "2");
        expected.add("count.notIn", "1");
        expected.add("count.notIn", "2");
        expected.add("count.specified", "true");
        expected.add("count.greaterThan", "1");
        expected.add("count.greaterThanOrEqual", "1");
        expected.add("count.lessThan", "1");
        expected.add("count.lessThanOrEqual", "1");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithDouble(boolean matchSubclass) {
        DoubleFilter filter = new DoubleFilter();
        CriteriaUtilTest.fillAll(filter, 1.01, 2.02);

        myCriteria.aDouble = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("aDouble.equals", "1.01");
        expected.add("aDouble.notEquals", "1.01");
        expected.add("aDouble.in", "1.01");
        expected.add("aDouble.in", "2.02");
        expected.add("aDouble.notIn", "1.01");
        expected.add("aDouble.notIn", "2.02");
        expected.add("aDouble.specified", "true");
        expected.add("aDouble.greaterThan", "1.01");
        expected.add("aDouble.greaterThanOrEqual", "1.01");
        expected.add("aDouble.lessThan", "1.01");
        expected.add("aDouble.lessThanOrEqual", "1.01");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithFloat(boolean matchSubclass) {
        FloatFilter filter = new FloatFilter();
        CriteriaUtilTest.fillAll(filter, 1.01f, 2.02f);

        myCriteria.aFloat = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("aFloat.equals", "1.01");
        expected.add("aFloat.notEquals", "1.01");
        expected.add("aFloat.in", "1.01");
        expected.add("aFloat.in", "2.02");
        expected.add("aFloat.notIn", "1.01");
        expected.add("aFloat.notIn", "2.02");
        expected.add("aFloat.specified", "true");
        expected.add("aFloat.greaterThan", "1.01");
        expected.add("aFloat.greaterThanOrEqual", "1.01");
        expected.add("aFloat.lessThan", "1.01");
        expected.add("aFloat.lessThanOrEqual", "1.01");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithBigDecimal(boolean matchSubclass) {
        BigDecimalFilter filter = new BigDecimalFilter();
        CriteriaUtilTest.fillAll(filter, BigDecimal.valueOf(1555.0351), BigDecimal.valueOf(155566.03519));

        myCriteria.total = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("total.equals", "1555.0351");
        expected.add("total.notEquals", "1555.0351");
        expected.add("total.in", "1555.0351");
        expected.add("total.in", "155566.03519");
        expected.add("total.notIn", "1555.0351");
        expected.add("total.notIn", "155566.03519");
        expected.add("total.specified", "true");
        expected.add("total.greaterThan", "1555.0351");
        expected.add("total.greaterThanOrEqual", "1555.0351");
        expected.add("total.lessThan", "1555.0351");
        expected.add("total.lessThanOrEqual", "1555.0351");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithBoolean(boolean matchSubclass) {
        BooleanFilter filter = new BooleanFilter();
        CriteriaUtilTest.fillAll(filter, true, false);

        myCriteria.active = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("active.equals", "true");
        expected.add("active.notEquals", "true");
        expected.add("active.in", "true");
        expected.add("active.in", "false");
        expected.add("active.notIn", "true");
        expected.add("active.notIn", "false");
        expected.add("active.specified", "true");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithDuration(boolean matchSubclass) {
        DurationFilter filter = new DurationFilter();
        Duration value1 = Duration.ofSeconds(959599774);
        Duration value2 = Duration.ofSeconds(314623697);
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = value1.toString();
        String v2 = value2.toString();

        myCriteria.duration = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("duration.equals", v1);
        expected.add("duration.notEquals", v1);
        expected.add("duration.in", v1);
        expected.add("duration.in", v2);
        expected.add("duration.notIn", v1);
        expected.add("duration.notIn", v2);
        expected.add("duration.specified", "true");
        expected.add("duration.greaterThan", v1);
        expected.add("duration.greaterThanOrEqual", v1);
        expected.add("duration.lessThan", v1);
        expected.add("duration.lessThanOrEqual", v1);

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithInstant(boolean matchSubclass) {
        InstantFilter filter = new InstantFilter();
        Instant value1 = Instant.parse("2019-01-05T09:05:35Z");
        Instant value2 = Instant.parse("1999-12-05T09:05:35Z");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = value1.toString();
        String v2 = value2.toString();

        myCriteria.createTime = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("createTime.equals", v1);
        expected.add("createTime.notEquals", v1);
        expected.add("createTime.in", v1);
        expected.add("createTime.in", v2);
        expected.add("createTime.notIn", v1);
        expected.add("createTime.notIn", v2);
        expected.add("createTime.specified", "true");
        expected.add("createTime.greaterThan", v1);
        expected.add("createTime.greaterThanOrEqual", v1);
        expected.add("createTime.lessThan", v1);
        expected.add("createTime.lessThanOrEqual", v1);

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithLocalDate(boolean matchSubclass) {
        LocalDateFilter filter = new LocalDateFilter();
        LocalDate value1 = LocalDate.parse("2019-01-05");
        LocalDate value2 = LocalDate.parse("1999-12-05");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = value1.toString();
        String v2 = value2.toString();

        myCriteria.localDate = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("localDate.equals", v1);
        expected.add("localDate.notEquals", v1);
        expected.add("localDate.in", v1);
        expected.add("localDate.in", v2);
        expected.add("localDate.notIn", v1);
        expected.add("localDate.notIn", v2);
        expected.add("localDate.specified", "true");
        expected.add("localDate.greaterThan", v1);
        expected.add("localDate.greaterThanOrEqual", v1);
        expected.add("localDate.lessThan", v1);
        expected.add("localDate.lessThanOrEqual", v1);

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithShort(boolean matchSubclass) {
        ShortFilter filter = new ShortFilter();
        short value1 = 1;
        short value2 = 2;
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = Short.toString(value1);
        String v2 = Short.toString(value2);

        myCriteria.aShort = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("aShort.equals", v1);
        expected.add("aShort.notEquals", v1);
        expected.add("aShort.in", v1);
        expected.add("aShort.in", v2);
        expected.add("aShort.notIn", v1);
        expected.add("aShort.notIn", v2);
        expected.add("aShort.specified", "true");
        expected.add("aShort.greaterThan", v1);
        expected.add("aShort.greaterThanOrEqual", v1);
        expected.add("aShort.lessThan", v1);
        expected.add("aShort.lessThanOrEqual", v1);

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithString(boolean matchSubclass) {
        StringFilter filter = new StringFilter();
        CriteriaUtilTest.fillAll(filter);
        String v1 = "any";
        String v2 = "any2";

        myCriteria.name = filter;
        myCriteria.name.setIgnoreCase(false);

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("name.equals", v1);
        expected.add("name.notEquals", v1);
        expected.add("name.in", v1);
        expected.add("name.in", v2);
        expected.add("name.notIn", v1);
        expected.add("name.notIn", v2);
        expected.add("name.specified", "true");
        expected.add("name.contains", v1);
        expected.add("name.notContains", v1);
        expected.add("name.ignoreCase", "false");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithUUID(boolean matchSubclass) {
        UUIDFilter filter = new UUIDFilter();
        UUID value1 = UUID.randomUUID();
        UUID value2 = UUID.randomUUID();
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = value1.toString();
        String v2 = value2.toString();

        myCriteria.uuid = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("uuid.equals", v1);
        expected.add("uuid.notEquals", v1);
        expected.add("uuid.in", v1);
        expected.add("uuid.in", v2);
        expected.add("uuid.notIn", v1);
        expected.add("uuid.notIn", v2);
        expected.add("uuid.specified", "true");
//        expected.add("uuid.contains", v1);
//        expected.add("uuid.notContains", v1);
//        expected.add("uuid.ignoreCase", "false");

        assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithZonedDateTime(boolean matchSubclass) {
        ZonedDateTimeFilter filter = new ZonedDateTimeFilter();
        ZonedDateTime value1 = ZonedDateTime.parse("2019-01-05T09:05:35+05:35");
        ZonedDateTime value2 = ZonedDateTime.parse("1999-12-05T09:05:35-10:10");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        String v1 = value1.toString();
        String v2 = value2.toString();

        myCriteria.zonedDateTime = filter;

        MultiValueMap<String, String> result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParamsMap(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("zonedDateTime.equals", v1);
        expected.add("zonedDateTime.notEquals", v1);
        expected.add("zonedDateTime.in", v1);
        expected.add("zonedDateTime.in", v2);
        expected.add("zonedDateTime.notIn", v1);
        expected.add("zonedDateTime.notIn", v2);
        expected.add("zonedDateTime.specified", "true");
        expected.add("zonedDateTime.greaterThan", v1);
        expected.add("zonedDateTime.greaterThanOrEqual", v1);
        expected.add("zonedDateTime.lessThan", v1);
        expected.add("zonedDateTime.lessThanOrEqual", v1);

        assertEquals(expected, result);
    }

    enum MyEnum {
        ENUM_VAL_1,
        ENUM_VAL_2,
        ENUM_VAL_3
    }

    static class MyEnumFilter extends Filter<MyEnum> {

    }

    static class MyCriteria implements Criteria {

        MyEnumFilter myEnum;

        LongFilter id;

        StringFilter name;

        InstantFilter createTime;

        BigDecimalFilter total;

        IntegerFilter count;

        LocalDateFilter localDate;

        ShortFilter aShort;

        DoubleFilter aDouble;

        FloatFilter aFloat;

        BooleanFilter active;

        UUIDFilter uuid;

        DurationFilter duration;

        ZonedDateTimeFilter zonedDateTime;

        @Override
        public Criteria copy() {
            throw new IllegalStateException("won't impl");
        }
    }
}
