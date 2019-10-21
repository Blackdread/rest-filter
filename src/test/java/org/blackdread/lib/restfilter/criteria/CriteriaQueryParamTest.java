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

    private CriteriaQueryParam defaultCriteriaQueryParam;

    @BeforeEach
    void setUp() {
        myCriteria = new MyCriteria();

        builder = new CriteriaQueryParamBuilder();

        defaultCriteriaQueryParam = builder.build();
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
        MultiValueMap<String, String> result1 = defaultCriteriaQueryParam.buildQueryParams(myCriteria);
        MultiValueMap<String, String> result2 = defaultCriteriaQueryParam.buildQueryParams((Object) myCriteria);
        MultiValueMap<String, String> result3 = defaultCriteriaQueryParam.buildQueryParams("theName", new LongFilter());
        List<FilterQueryParam> result4 = defaultCriteriaQueryParam.getFilterQueryParams(new HashMap<>());
        List<FilterQueryParam> result5 = defaultCriteriaQueryParam.getFilterQueryParams("theName", new LongFilter());

        LinkedMultiValueMap<String, String> expected = new LinkedMultiValueMap<>();
        Assertions.assertEquals(expected,result1);
        Assertions.assertEquals(expected,result2);
        Assertions.assertEquals(expected,result3);

        ArrayList<FilterQueryParam> expected2 = new ArrayList<>();
        Assertions.assertEquals(expected2,result4);
        Assertions.assertEquals(expected2,result5);
    }

    @Test
    void canBuildQueryParamsFromCriteria() {

    }

    @Test
    void canBuildQueryParamsFromObject() {

    }

    @Test
    void buildWithEnum() {

    }

    @Test
    void buildWithLong() {

    }

    @Test
    void buildWithInteger() {

    }

    @Test
    void buildWithDouble() {

    }

    @Test
    void buildWithFloat() {

    }

    @Test
    void buildWithBigDecimal() {

    }

    @Test
    void buildWithBoolean() {

    }

    @Test
    void buildWithDuration() {

    }

    @Test
    void buildWithInstant() {

    }

    @Test
    void buildWithLocalDate() {

    }

    @Test
    void buildWithShort() {

    }

    @Test
    void buildWithString() {

    }

    @Test
    void buildWithUUID() {

    }

    @Test
    void buildWithZonedDateTime() {

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
