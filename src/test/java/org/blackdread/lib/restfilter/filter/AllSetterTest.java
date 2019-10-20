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
package org.blackdread.lib.restfilter.filter;

import org.blackdread.lib.restfilter.criteria.CriteriaUtilTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * <p>Created on 2019/10/08.</p>
 *
 * @author Yoann CAPLAIN
 */
class AllSetterTest {

    private static final Logger log = LoggerFactory.getLogger(AllSetterTest.class);

    private static Stream<Arguments> testBaseFilterProvider() {
        return Stream.of(
            Arguments.of(new Filter<Long>(), 1L, 2L),
            Arguments.of(new BooleanFilter(), true, false),
            Arguments.of(new UUIDFilter(), UUID.randomUUID(), UUID.randomUUID())
        );
    }

    @MethodSource("testBaseFilterProvider")
    @ParameterizedTest
    <T> void testBaseFilter(Filter<T> filter, T value, T value2) {
        CriteriaUtilTest.fillAll(filter, value, value2);
        assertEquals(value, filter.getEquals());
        assertEquals(value, filter.getNotEquals());
        assertEquals(List.of(value,value2), filter.getIn());
        assertEquals(List.of(value,value2), filter.getNotIn());
        assertEquals(true, filter.getSpecified());
    }

    private static Stream<Arguments> testAllRangeProvider() {
        return Stream.of(
            Arguments.of(new IntegerFilter(), 1, 2),
            Arguments.of(new LongFilter(), 1L, 2L),
            Arguments.of(new ShortFilter(), (short) 1, (short) 2),
            Arguments.of(new FloatFilter(), 1.0f, 2.0f),
            Arguments.of(new DoubleFilter(), 1.0, 2.0),
            Arguments.of(new BigDecimalFilter(), BigDecimal.ONE, BigDecimal.TEN),
            Arguments.of(new InstantFilter(), Instant.now(), Instant.now().plusSeconds(3600)),
            Arguments.of(new LocalDateFilter(), LocalDate.now(), LocalDate.now().plusDays(1)),
            Arguments.of(new ZonedDateTimeFilter(), ZonedDateTime.now(), ZonedDateTime.now().plusDays(1)),
            Arguments.of(new DurationFilter(), Duration.ofMinutes(5), Duration.ofMinutes(10))
        );
    }

    @MethodSource("testAllRangeProvider")
    @ParameterizedTest
    <T extends Comparable<? super T>> void testAllRange(RangeFilter<T> filter, T value, T value2) {
        CriteriaUtilTest.fillAll(filter, value, value2);
        assertEquals(value, filter.getEquals());
        assertEquals(value, filter.getNotEquals());
        assertEquals(List.of(value,value2), filter.getIn());
        assertEquals(List.of(value,value2), filter.getNotIn());
        assertEquals(true, filter.getSpecified());
        assertEquals(value, filter.getGreaterThan());
        assertEquals(value, filter.getGreaterThanOrEqual());
        assertEquals(value, filter.getLessThan());
        assertEquals(value, filter.getLessThanOrEqual());
    }

    @Test
    <T> void testString() {
        final StringFilter filter = new StringFilter();
        CriteriaUtilTest.fillAll(filter);
        assertEquals("any", filter.getEquals());
        assertEquals("any", filter.getNotEquals());
        assertEquals(List.of("any","any2"), filter.getIn());
        assertEquals(List.of("any","any2"), filter.getNotIn());
        assertEquals(true, filter.getSpecified());
        assertEquals("any", filter.getContains());
        assertEquals("any", filter.getNotContains());
    }

    @Test
    <T> void equals() {
        final StringFilter filter = new StringFilter();
        final LongFilter filter2 = new LongFilter();
        final LongFilter filter22 = new LongFilter();
        filter22.setEquals(6L);
        final BooleanFilter filter3 = new BooleanFilter();
        assertNotEquals(filter, filter2);
        assertNotEquals(filter2, filter3);
        assertNotEquals(filter2, filter22);
        assertNotEquals(filter3, filter2);
    }

}
