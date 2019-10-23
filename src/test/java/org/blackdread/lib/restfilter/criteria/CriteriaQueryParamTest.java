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

import org.blackdread.lib.restfilter.filter.*;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.blackdread.lib.restfilter.util.MultiValueMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals("2019-10-19T15:50:10Z", instantFormatter.apply(instant));
        Assertions.assertEquals("2019-10-19", localDateFormatter.apply(localDate));
        Assertions.assertEquals("2019-10-19T17:50:10", localDateTimeFormatter.apply(localDateTime));
        Assertions.assertEquals("2007-12-03T10:15:30+01:00[Europe/Paris]", zonedDateTimeFormatter.apply(zonedDateTime));
        Assertions.assertEquals("PT48H20.345S", durationFormatter.apply(duration));

        Assertions.assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDate));
        Assertions.assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDateTime));
        Assertions.assertEquals("2007-12-03T09:15:30Z", DateTimeFormatter.ISO_INSTANT.format(zonedDateTime));
    }

    @Test
    void noErrorWhenCriteriaEmpty() {
        MultiValueMap<String, String> result1 = criteriaQueryParam.buildQueryParams(myCriteria);
        MultiValueMap<String, String> result2 = criteriaQueryParam.buildQueryParams((Object) myCriteria);
        MultiValueMap<String, String> result3 = criteriaQueryParam.buildQueryParams("theName", new LongFilter());
        List<FilterQueryParam> result4 = criteriaQueryParam.getFilterQueryParams(new HashMap<>());
        List<FilterQueryParam> result5 = criteriaQueryParam.getFilterQueryParams("theName", new LongFilter());

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        Assertions.assertEquals(expected, result1);
        Assertions.assertEquals(expected, result2);
        Assertions.assertEquals(expected, result3);

        ArrayList<FilterQueryParam> expected2 = new ArrayList<>();
        Assertions.assertEquals(expected2, result4);
        Assertions.assertEquals(expected2, result5);
    }

    @Test
    void canBuildQueryParamsFromCriteria() {
        myCriteria.duration = new DurationFilter();
        myCriteria.duration.setEquals(Duration.ofMinutes(55));

        myCriteria.id = CriteriaUtil.buildEqualsCriteria(5L);
        myCriteria.name = CriteriaUtil.buildEqualsCriteria("aaa");
        myCriteria.name = CriteriaUtil.buildInCriteria(myCriteria.name, List.of("bbb", "ccc"));
        myCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        var result = criteriaQueryParam.buildQueryParams(myCriteria);

        var expected = new LinkedMultiValueMap<>();
        expected.add("duration.equals", "PT55M");
        expected.add("name.equals", "aaa");
        expected.add("name.in", "bbb");
        expected.add("name.in", "ccc");
        expected.add("active.equals", "false");
        expected.add("id.equals", "5");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void canBuildQueryParamsFromObject() {
        myCriteria.duration = new DurationFilter();
        myCriteria.duration.setEquals(Duration.ofMinutes(55));

        myCriteria.id = CriteriaUtil.buildEqualsCriteria(5L);
        myCriteria.name = CriteriaUtil.buildEqualsCriteria("aaa");
        myCriteria.name = CriteriaUtil.buildInCriteria(myCriteria.name, List.of("bbb", "ccc"));
        myCriteria.active = CriteriaUtil.buildEqualsCriteria(false);

        var result = criteriaQueryParam.buildQueryParams((Object) myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("duration.equals", "PT55M");
        expected.add("name.equals", "aaa");
        expected.add("name.in", "bbb");
        expected.add("name.in", "ccc");
        expected.add("active.equals", "false");
        expected.add("id.equals", "5");

        Assertions.assertEquals(expected, result);
    }

    @Test
    void customFormatterMatchByClassOnlyAndNoSubclass() {
        var filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        criteriaQueryParam = builder
            .putCustomQueryParamFormatter(LongFilter.class, (fieldName, filter1) -> {
                throw new IllegalStateException("Will not be called");
            })
            .build();

        final UnsupportedFilterForQueryParamException exception = Assertions.assertThrows(UnsupportedFilterForQueryParamException.class, () -> criteriaQueryParam.buildQueryParams(myCriteria));
        Assertions.assertEquals("Field 'id' did not match any formatter for filter 'class org.blackdread.lib.restfilter.criteria.CriteriaQueryParamTest$CustomLongFilter'", exception.getMessage());
    }

    @Test
    void defaultFormatterMatchByClassOnlyAndNoSubclass() {
        var filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(true)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @Test
    void defaultFormatterCanMatchSubclass() {
        var filter = new CustomLongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        final UnsupportedFilterForQueryParamException exception = Assertions.assertThrows(UnsupportedFilterForQueryParamException.class, () -> criteriaQueryParam.buildQueryParams(myCriteria));
        Assertions.assertEquals("Field 'id' did not match any formatter for filter 'class org.blackdread.lib.restfilter.criteria.CriteriaQueryParamTest$CustomLongFilter'", exception.getMessage());
    }

    private static class CustomLongFilter extends LongFilter {

    }

    @Test
    void buildWithManyEnum() {
        // todo configure multi enum type in builder
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithEnum(boolean matchSubclass) {
        var filter = new MyEnumFilter();
        CriteriaUtilTest.fillAll(filter, MyEnum.ENUM_VAL_1, MyEnum.ENUM_VAL_2);

        myCriteria.myEnum = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("myEnum.equals", "ENUM_VAL_1");
        expected.add("myEnum.notEquals", "ENUM_VAL_1");
        expected.add("myEnum.in", "ENUM_VAL_1");
        expected.add("myEnum.in", "ENUM_VAL_2");
        expected.add("myEnum.notIn", "ENUM_VAL_1");
        expected.add("myEnum.notIn", "ENUM_VAL_2");
        expected.add("myEnum.specified", "true");

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithLong(boolean matchSubclass) {
        var filter = new LongFilter();
        CriteriaUtilTest.fillAll(filter, 1L, 2L);

        myCriteria.id = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithInteger(boolean matchSubclass) {
        var filter = new IntegerFilter();
        CriteriaUtilTest.fillAll(filter, 1, 2);

        myCriteria.count = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithDouble(boolean matchSubclass) {
        var filter = new DoubleFilter();
        CriteriaUtilTest.fillAll(filter, 1.01, 2.02);

        myCriteria.aDouble = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithFloat(boolean matchSubclass) {
        var filter = new FloatFilter();
        CriteriaUtilTest.fillAll(filter, 1.01f, 2.02f);

        myCriteria.aFloat = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithBigDecimal(boolean matchSubclass) {
        var filter = new BigDecimalFilter();
        CriteriaUtilTest.fillAll(filter, BigDecimal.valueOf(1555.0351), BigDecimal.valueOf(155566.03519));

        myCriteria.total = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithBoolean(boolean matchSubclass) {
        var filter = new BooleanFilter();
        CriteriaUtilTest.fillAll(filter, true, false);

        myCriteria.active = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        expected.add("active.equals", "true");
        expected.add("active.notEquals", "true");
        expected.add("active.in", "true");
        expected.add("active.in", "false");
        expected.add("active.notIn", "true");
        expected.add("active.notIn", "false");
        expected.add("active.specified", "true");

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithDuration(boolean matchSubclass) {
        var filter = new DurationFilter();
        var value1 = Duration.ofSeconds(959599774);
        var value2 = Duration.ofSeconds(314623697);
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = value1.toString();
        var v2 = value2.toString();

        myCriteria.duration = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithInstant(boolean matchSubclass) {
        var filter = new InstantFilter();
        var value1 = Instant.parse("2019-01-05T09:05:35Z");
        var value2 = Instant.parse("1999-12-05T09:05:35Z");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = value1.toString();
        var v2 = value2.toString();

        myCriteria.createTime = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithLocalDate(boolean matchSubclass) {
        var filter = new LocalDateFilter();
        var value1 = LocalDate.parse("2019-01-05");
        var value2 = LocalDate.parse("1999-12-05");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = value1.toString();
        var v2 = value2.toString();

        myCriteria.localDate = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithShort(boolean matchSubclass) {
        var filter = new ShortFilter();
        short value1 = 1;
        short value2 = 2;
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = Short.toString(value1);
        var v2 = Short.toString(value2);

        myCriteria.aShort = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithString(boolean matchSubclass) {
        var filter = new StringFilter();
        CriteriaUtilTest.fillAll(filter);
        var v1 = "any";
        var v2 = "any2";

        myCriteria.name = filter;
        myCriteria.name.setIgnoreCase(false);

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithUUID(boolean matchSubclass) {
        var filter = new UUIDFilter();
        var value1 = UUID.randomUUID();
        var value2 = UUID.randomUUID();
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = value1.toString();
        var v2 = value2.toString();

        myCriteria.uuid = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void buildWithZonedDateTime(boolean matchSubclass) {
        var filter = new ZonedDateTimeFilter();
        var value1 = ZonedDateTime.parse("2019-01-05T09:05:35+05:35");
        var value2 = ZonedDateTime.parse("1999-12-05T09:05:35-10:10");
        CriteriaUtilTest.fillAll(filter, value1, value2);
        var v1 = value1.toString();
        var v2 = value2.toString();

        myCriteria.zonedDateTime = filter;

        var result = builder
            .matchSubclassForDefaultFilterFormatters(matchSubclass)
            .build()
            .buildQueryParams(myCriteria);

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

        Assertions.assertEquals(expected, result);
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
