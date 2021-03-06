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

import org.blackdread.lib.restfilter.filter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/05/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CriteriaUtilTest {

    private Filter<Long> filterLong;
    private RangeFilter<Long> rangeFilterLong;

    private ShortFilter shortFilter;
    private IntegerFilter integerFilter;
    private LongFilter longFilter;
    private DoubleFilter doubleFilter;
    private FloatFilter floatFilter;
    private BigDecimalFilter bigDecimalFilter;

    private BooleanFilter booleanFilter;

    private DurationFilter durationFilter;
    private InstantFilter instantFilter;
    private LocalDateFilter localDateFilter;
    private ZonedDateTimeFilter zonedDateTimeFilter;

    private StringFilter stringFilter;
    private UUIDFilter uuidFilter;

    private static final Instant NOW = Instant.ofEpochSecond(50);
    private static final LocalDate NOW_DATE = LocalDate.ofEpochDay(50);
    private static final LocalDateTime NOW_DATETIME = LocalDateTime.ofInstant(NOW, ZoneOffset.UTC);
    private static final Duration DURATION = Duration.ofSeconds(50);
    private static final BigDecimal DECIMAL = BigDecimal.valueOf(50L);
    private static final short SHORT = 50;
    private static final double DOUBLE = 50.0;
    private static final float FLOAT = 50.0f;

    private static final List<Long> longList = Arrays.asList(1L, 2L);
    private static final List<Integer> intList = Arrays.asList(1, 2);
    private static final List<String> stringList = Arrays.asList("any", "any2");
    private static final List<Boolean> boolList = Arrays.asList(true, false);
    private static final List<Instant> instantList = Arrays.asList(NOW, NOW);
    private static final List<LocalDate> dateList = Arrays.asList(NOW_DATE, NOW_DATE);
    private static final List<LocalDateTime> dateTimeList = Arrays.asList(NOW_DATETIME, NOW_DATETIME);
    private static final List<Duration> durationList = Arrays.asList(DURATION, DURATION);
    private static final List<Short> shortList = Arrays.asList(SHORT, SHORT);
    private static final List<BigDecimal> decimalList = Arrays.asList(DECIMAL, DECIMAL);
    private static final List<Double> doubleList = Arrays.asList(DOUBLE, DOUBLE);
    private static final List<Float> floatList = Arrays.asList(FLOAT, FLOAT);

    @BeforeEach
    void setUp() {
        filterLong = new Filter<>();
        rangeFilterLong = new RangeFilter<>();

        shortFilter = new ShortFilter();
        integerFilter = new IntegerFilter();
        longFilter = new LongFilter();
        doubleFilter = new DoubleFilter();
        floatFilter = new FloatFilter();
        bigDecimalFilter = new BigDecimalFilter();

        booleanFilter = new BooleanFilter();

        durationFilter = new DurationFilter();
        instantFilter = new InstantFilter();
        localDateFilter = new LocalDateFilter();
        zonedDateTimeFilter = new ZonedDateTimeFilter();

        stringFilter = new StringFilter();
        uuidFilter = new UUIDFilter();
    }

    public static <T> void fillAll(final Filter<T> filter, final T value, final T value2) {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setIn(Arrays.asList(value, value2));
        filter.setNotIn(Arrays.asList(value, value2));
        filter.setSpecified(true);
    }

    public static void fillAll(final LongFilter filter) {
        filter.setEquals(1L);
        filter.setNotEquals(1L);
        filter.setIn(Arrays.asList(1L, 2L));
        filter.setNotIn(Arrays.asList(1L, 2L));
        filter.setSpecified(true);
        filter.setGreaterThan(1L);
        filter.setGreaterThanOrEqual(1L);
        filter.setLessThan(1L);
        filter.setLessThanOrEqual(1L);
    }

    public static <T extends Comparable<? super T>> void fillAll(final RangeFilter<T> filter, final T value, final T value2) {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setIn(Arrays.asList(value, value2));
        filter.setNotIn(Arrays.asList(value, value2));
        filter.setSpecified(true);
        filter.setGreaterThan(value);
        filter.setGreaterThanOrEqual(value);
        filter.setLessThan(value);
        filter.setLessThanOrEqual(value);
    }

    public static void fillAll(final StringFilter filter) {
        filter.setEquals("any");
        filter.setNotEquals("any");
        filter.setIn(Arrays.asList("any", "any2"));
        filter.setNotIn(Arrays.asList("any", "any2"));
        filter.setSpecified(true);
        filter.setContains("any");
        filter.setNotContains("any");
    }

    private static void assertAllNullExceptEquals(final Filter<?> filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
    }

    private static void assertAllNullExceptEquals(final RangeFilter<?> filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptEquals(final StringFilter filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getContains());
        assertNull(filter.getNotContains());
    }

    private static void assertAllNullExceptIn(final Filter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
    }

    private static void assertAllNullExceptIn(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptIn(final StringFilter filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getContains());
    }

    private static void assertAllNullExceptGreaterThan(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNotNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptGreaterThanOrEqual(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNotNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptLessThan(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNotNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptLessThanOrEqual(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNull(filter.getNotEquals());
        assertNull(filter.getIn());
        assertNull(filter.getNotIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNotNull(filter.getLessThanOrEqual());
    }

    @Test
    void createCriteria() {
        assertNotNull(CriteriaUtil.createCriteria(Filter.class));
        assertNotNull(CriteriaUtil.createCriteria(RangeFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(ShortFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(IntegerFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(LongFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(DoubleFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(FloatFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(BigDecimalFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(DurationFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(InstantFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(LocalDateFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(ZonedDateTimeFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(StringFilter.class));
        assertNotNull(CriteriaUtil.createCriteria(UUIDFilter.class));
    }

    @Test
    void isDefinedIgnoreNull() {
        assertFalse(CriteriaUtil.isEqualsDefined(null));
        assertFalse(CriteriaUtil.isInDefined(null));
        assertFalse(CriteriaUtil.isSpecifiedDefined(null));
        assertFalse(CriteriaUtil.isContainsDefined(null));
        assertFalse(CriteriaUtil.isAnyRangeDefined(null));
        assertFalse(CriteriaUtil.isGreaterThanDefined(null));
        assertFalse(CriteriaUtil.isGreaterThanOrEqualDefined(null));
        assertFalse(CriteriaUtil.isLessThanDefined(null));
        assertFalse(CriteriaUtil.isLessThanOrEqualDefined(null));
    }

    @Test
    void isCriteriaOverlapForFilterFalseForNothing() {
        assertFalse(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForFilterFalseForNull() {
        assertFalse(CriteriaUtil.isCriteriaOverlap((Filter) null));
    }

    @Test
    void isCriteriaOverlapForFilterFalseForEquals() {
        filterLong.setEquals(1L);
        assertFalse(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForFilterFalseForIn() {
        filterLong.setIn(Arrays.asList(1L, 2L));
        assertFalse(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForFilterWithEqIn() {
        filterLong.setEquals(1L);
        filterLong.setIn(Arrays.asList(1L, 2L));

        assertTrue(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForFilterWithEqSpecified() {
        filterLong.setEquals(1L);
        filterLong.setSpecified(true);

        assertTrue(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForFilterWithInSpecified() {
        filterLong.setIn(Arrays.asList(1L, 2L));
        filterLong.setSpecified(true);

        assertTrue(CriteriaUtil.isCriteriaOverlap(filterLong));
    }

    @Test
    void isCriteriaOverlapForRangeFilter() {

    }

    @Test
    void isCriteriaOverlapForStringFilterFalseForNothing() {
        assertFalse(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterFalseForNull() {
        assertFalse(CriteriaUtil.isCriteriaOverlap((StringFilter) null));
    }

    @Test
    void isCriteriaOverlapForStringFilterFalseForEquals() {
        stringFilter.setEquals("any");
        assertFalse(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterFalseForIn() {
        stringFilter.setIn(Arrays.asList("any", "any2"));
        assertFalse(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterWithEqIn() {
        stringFilter.setEquals("any");
        stringFilter.setIn(Arrays.asList("any", "any2"));
        assertTrue(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterWithEqSpecified() {
        stringFilter.setEquals("any");
        stringFilter.setSpecified(true);
        assertTrue(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterWithInSpecified() {
        stringFilter.setIn(Arrays.asList("any", "any2"));
        stringFilter.setSpecified(true);
        assertTrue(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterWithInContains() {
        stringFilter.setIn(Arrays.asList("any", "any2"));
        stringFilter.setContains("any");
        assertTrue(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isCriteriaOverlapForStringFilterWithContainsSpecified() {
        stringFilter.setContains("any");
        stringFilter.setSpecified(true);
        assertTrue(CriteriaUtil.isCriteriaOverlap(stringFilter));
    }

    @Test
    void isEqualsDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isEqualsDefined(longFilter));
        longFilter.setEquals(null);
        assertFalse(CriteriaUtil.isEqualsDefined(longFilter));
    }

    @Test
    void isInDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isInDefined(longFilter));
        longFilter.setIn(null);
        assertFalse(CriteriaUtil.isInDefined(longFilter));
    }

    @Test
    void isContainsDefined() {
        fillAll(stringFilter);
        assertTrue(CriteriaUtil.isContainsDefined(stringFilter));
        stringFilter.setContains(null);
        assertFalse(CriteriaUtil.isContainsDefined(stringFilter));
    }

    @Test
    void isSpecifiedDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isSpecifiedDefined(longFilter));
        longFilter.setSpecified(null);
        assertFalse(CriteriaUtil.isSpecifiedDefined(longFilter));
    }

    @Test
    void isGreaterThanDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isGreaterThanDefined(longFilter));
        longFilter.setGreaterThan(null);
        assertFalse(CriteriaUtil.isGreaterThanDefined(longFilter));
    }

    @Test
    void isGreaterThanOrEqualDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isGreaterThanOrEqualDefined(longFilter));
        longFilter.setGreaterThanOrEqual(null);
        assertFalse(CriteriaUtil.isGreaterThanOrEqualDefined(longFilter));
    }

    @Test
    void isLessThanDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isLessThanDefined(longFilter));
        longFilter.setLessThan(null);
        assertFalse(CriteriaUtil.isLessThanDefined(longFilter));
    }

    @Test
    void isLessThanOrEqualDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isLessThanOrEqualDefined(longFilter));
        longFilter.setLessThanOrEqual(null);
        assertFalse(CriteriaUtil.isLessThanOrEqualDefined(longFilter));
    }

    @Test
    void isAnyRangeDefined() {
        fillAll(longFilter);
        assertTrue(CriteriaUtil.isAnyRangeDefined(longFilter));
        longFilter.setLessThan(null);
        assertTrue(CriteriaUtil.isAnyRangeDefined(longFilter));
        longFilter.setLessThanOrEqual(null);
        assertTrue(CriteriaUtil.isAnyRangeDefined(longFilter));
        longFilter.setGreaterThan(null);
        assertTrue(CriteriaUtil.isAnyRangeDefined(longFilter));
        longFilter.setGreaterThanOrEqual(null);
        assertFalse(CriteriaUtil.isAnyRangeDefined(longFilter));
    }

    @Test
    void buildEqualsCriteriaThrowsOnNullValue() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildEqualsCriteria((Long) null));
    }

    @Test
    void buildEqualsCriteriaOrThrowThrowsOnValueMismatch() {
        longFilter.setEquals(6L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildEqualsCriteriaOrThrow(longFilter, 5L));
    }

    @Test
    void buildEqualsCriteria() {
        final LongFilter filter1 = CriteriaUtil.buildEqualsCriteria(1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getEquals());
        assertAllNullExceptEquals(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildEqualsCriteria(1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getEquals());
        assertAllNullExceptEquals(filter2);

        final StringFilter filter3 = CriteriaUtil.buildEqualsCriteria("any");
        assertNotNull(filter3);
        assertEquals("any", filter3.getEquals());
        assertAllNullExceptEquals(filter3);

        final BooleanFilter filter4 = CriteriaUtil.buildEqualsCriteria(true);
        assertNotNull(filter4);
        assertEquals(true, filter4.getEquals());
        assertAllNullExceptEquals(filter4);

        final InstantFilter filter5 = CriteriaUtil.buildEqualsCriteria(NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getEquals());
        assertAllNullExceptEquals(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildEqualsCriteria(NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getEquals());
        assertAllNullExceptEquals(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildEqualsCriteria(DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getEquals());
        assertAllNullExceptEquals(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildEqualsCriteria(FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getEquals());
        assertAllNullExceptEquals(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildEqualsCriteria(DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getEquals());
        assertAllNullExceptEquals(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildEqualsCriteria(SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getEquals());
        assertAllNullExceptEquals(filter10);
    }

    @Test
    void buildEqualsCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildEqualsCriteriaOrThrow(longFilter, 1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getEquals());
        assertEquals(longFilter, filter1);
        assertAllNullExceptEquals(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildEqualsCriteriaOrThrow(integerFilter, 1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getEquals());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptEquals(filter2);

        final StringFilter filter3 = CriteriaUtil.buildEqualsCriteriaOrThrow(stringFilter, "any");
        assertNotNull(filter3);
        assertEquals("any", filter3.getEquals());
        assertEquals(stringFilter, filter3);
        assertAllNullExceptEquals(filter3);

        final BooleanFilter filter4 = CriteriaUtil.buildEqualsCriteriaOrThrow(booleanFilter, true);
        assertNotNull(filter4);
        assertEquals(true, filter4.getEquals());
        assertEquals(booleanFilter, filter4);
        assertAllNullExceptEquals(filter4);

        final InstantFilter filter5 = CriteriaUtil.buildEqualsCriteriaOrThrow(instantFilter, NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getEquals());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptEquals(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildEqualsCriteriaOrThrow(localDateFilter, NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getEquals());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptEquals(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildEqualsCriteriaOrThrow(doubleFilter, DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getEquals());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptEquals(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildEqualsCriteriaOrThrow(floatFilter, FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getEquals());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptEquals(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildEqualsCriteriaOrThrow(bigDecimalFilter, DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getEquals());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptEquals(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildEqualsCriteriaOrThrow(shortFilter, SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getEquals());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptEquals(filter10);
    }

    @Test
    void buildInCriteriaFromClass() {
        final LongFilter filter = CriteriaUtil.buildInCriteria(LongFilter.class, longList);
        assertNotNull(filter);
        assertEquals(longList, filter.getIn());
        assertAllNullExceptIn(filter);
    }

    @Test
    void buildInCriteria() {

        final LongFilter filter1 = CriteriaUtil.buildInCriteria(longList);
        assertNotNull(filter1);
        assertEquals(longList, filter1.getIn());
        assertAllNullExceptIn(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildInCriteriaInteger(intList);
        assertNotNull(filter2);
        assertEquals(intList, filter2.getIn());
        assertAllNullExceptIn(filter2);

        final StringFilter filter3 = CriteriaUtil.buildInCriteriaString(stringList);
        assertNotNull(filter3);
        assertEquals(stringList, filter3.getIn());
        assertAllNullExceptIn(filter3);

//        final BooleanFilter filter4 = CriteriaUtil.buildInCriteriaBoolean(boolList);
//        assertNotNull(filter4);
//        assertEquals(boolList, filter4.getIn());
//        assertAllNullExceptIn(filter4);

        final InstantFilter filter5 = CriteriaUtil.buildInCriteriaInstant(instantList);
        assertNotNull(filter5);
        assertEquals(instantList, filter5.getIn());
        assertAllNullExceptIn(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildInCriteriaLocalDate(dateList);
        assertNotNull(filter6);
        assertEquals(dateList, filter6.getIn());
        assertAllNullExceptIn(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildInCriteriaDouble(doubleList);
        assertNotNull(filter7);
        assertEquals(doubleList, filter7.getIn());
        assertAllNullExceptIn(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildInCriteriaFloat(floatList);
        assertNotNull(filter8);
        assertEquals(floatList, filter8.getIn());
        assertAllNullExceptIn(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildInCriteriaBigDecimal(decimalList);
        assertNotNull(filter9);
        assertEquals(decimalList, filter9.getIn());
        assertAllNullExceptIn(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildInCriteriaShort(shortList);
        assertNotNull(filter10);
        assertEquals(shortList, filter10.getIn());
        assertAllNullExceptIn(filter10);
    }

    @Test
    void buildInCriteriaThrowOnNullValues() {
        assertThrows(NullPointerException.class, () -> CriteriaUtil.buildInCriteria(longFilter, null));
    }

    @Test
    void buildInCriteriaThrowOnEmptyValues() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildInCriteria(longFilter, Collections.emptyList()));
    }

    @Test
    void buildInCriteriaFiltered() {
        longFilter.setIn(Arrays.asList(5L, 44949L, 16161616L));
        final LongFilter filter1 = CriteriaUtil.buildInCriteriaFiltered(longFilter, longList);
        assertNotNull(filter1);
        assertEquals(longList, filter1.getIn());
        assertAllNullExceptIn(filter1);

        integerFilter.setIn(Arrays.asList(5, 44949, 16161616));
        final IntegerFilter filter2 = CriteriaUtil.buildInCriteriaFiltered(integerFilter, intList);
        assertNotNull(filter2);
        assertEquals(intList, filter2.getIn());
        assertAllNullExceptIn(filter2);

        stringFilter.setIn(Arrays.asList("sefefe", "rdgrgrg", "adwdwdwdw"));
        final StringFilter filter3 = CriteriaUtil.buildInCriteriaFiltered(stringFilter, stringList);
        assertNotNull(filter3);
        assertEquals(stringList, filter3.getIn());
        assertAllNullExceptIn(filter3);

//        final BooleanFilter filter4 = CriteriaUtil.buildInCriteriaFiltered(null, boolList);
//        assertNotNull(filter4);
//        assertEquals(boolList, filter4.getIn());
//        assertAllNullExceptIn(filter4);

        instantFilter.setIn(Arrays.asList(Instant.ofEpochSecond(5L), Instant.ofEpochSecond(44949L), Instant.ofEpochSecond(16161616L)));
        final InstantFilter filter5 = CriteriaUtil.buildInCriteriaFiltered(instantFilter, instantList);
        assertNotNull(filter5);
        assertEquals(instantList, filter5.getIn());
        assertAllNullExceptIn(filter5);

        localDateFilter.setIn(Arrays.asList(LocalDate.ofEpochDay(5L), LocalDate.ofEpochDay(44949L)));
        final LocalDateFilter filter6 = CriteriaUtil.buildInCriteriaFiltered(localDateFilter, dateList);
        assertNotNull(filter6);
        assertEquals(dateList, filter6.getIn());
        assertAllNullExceptIn(filter6);

        doubleFilter.setIn(Arrays.asList(5.699, 44949.055, 16161616.0));
        final DoubleFilter filter7 = CriteriaUtil.buildInCriteriaFiltered(doubleFilter, doubleList);
        assertNotNull(filter7);
        assertEquals(doubleList, filter7.getIn());
        assertAllNullExceptIn(filter7);

        floatFilter.setIn(Arrays.asList(5.54f, 44949.01f, 16161616.0f));
        final FloatFilter filter8 = CriteriaUtil.buildInCriteriaFiltered(floatFilter, floatList);
        assertNotNull(filter8);
        assertEquals(floatList, filter8.getIn());
        assertAllNullExceptIn(filter8);

        bigDecimalFilter.setIn(Arrays.asList(BigDecimal.valueOf(5L), BigDecimal.valueOf(44949L), BigDecimal.valueOf(16161616L)));
        final BigDecimalFilter filter9 = CriteriaUtil.buildInCriteriaFiltered(bigDecimalFilter, decimalList);
        assertNotNull(filter9);
        assertEquals(decimalList, filter9.getIn());
        assertAllNullExceptIn(filter9);

        shortFilter.setIn(Arrays.asList((short) 5, (short) 244, (short) 22));
        final ShortFilter filter10 = CriteriaUtil.buildInCriteriaFiltered(shortFilter, shortList);
        assertNotNull(filter10);
        assertEquals(shortList, filter10.getIn());
        assertAllNullExceptIn(filter10);
    }

    @Test
    void buildInCriteriaFilteredThrowOnNullValues() {
        assertThrows(NullPointerException.class, () -> CriteriaUtil.buildInCriteriaFiltered(longFilter, null));
    }

    @Test
    void buildInCriteriaFilteredThrowOnEmptyValues() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildInCriteriaFiltered(longFilter, Collections.emptyList()));
    }

    @Test
    void buildInCriteriaFilteredAddAllIfNoValuesAlready() {
        final LongFilter filter = CriteriaUtil.buildInCriteriaFiltered(longFilter, longList);
        assertEquals(longList, filter.getIn());
    }

    @Test
    void buildInCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildInCriteriaOrThrow(longFilter, longList);
        assertNotNull(filter1);
        assertEquals(longList, filter1.getIn());
        assertEquals(longFilter, filter1);
        assertAllNullExceptIn(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildInCriteriaOrThrow(integerFilter, intList);
        assertNotNull(filter2);
        assertEquals(intList, filter2.getIn());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptIn(filter2);

        final StringFilter filter3 = CriteriaUtil.buildInCriteriaOrThrow(stringFilter, stringList);
        assertNotNull(filter3);
        assertEquals(stringList, filter3.getIn());
        assertEquals(stringFilter, filter3);
        assertAllNullExceptIn(filter3);

//        final BooleanFilter filter4 = CriteriaUtil.buildInCriteriaOrThrow(booleanFilter, boolList);
//        assertNotNull(filter4);
//        assertEquals(boolList, filter4.getEquals());
//        assertEquals(booleanFilter, filter4);
//        assertAllNullExceptIn(filter4);

        final InstantFilter filter5 = CriteriaUtil.buildInCriteriaOrThrow(instantFilter, instantList);
        assertNotNull(filter5);
        assertEquals(instantList, filter5.getIn());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptIn(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildInCriteriaOrThrow(localDateFilter, dateList);
        assertNotNull(filter6);
        assertEquals(dateList, filter6.getIn());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptIn(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildInCriteriaOrThrow(doubleFilter, doubleList);
        assertNotNull(filter7);
        assertEquals(doubleList, filter7.getIn());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptIn(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildInCriteriaOrThrow(floatFilter, floatList);
        assertNotNull(filter8);
        assertEquals(floatList, filter8.getIn());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptIn(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildInCriteriaOrThrow(bigDecimalFilter, decimalList);
        assertNotNull(filter9);
        assertEquals(decimalList, filter9.getIn());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptIn(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildInCriteriaOrThrow(shortFilter, shortList);
        assertNotNull(filter10);
        assertEquals(shortList, filter10.getIn());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptIn(filter10);
    }

    @Test
    void buildInCriteriaOrThrowNoReplace() {
        final LongFilter filter1 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, longList);
        assertNotNull(filter1);
        assertEquals(longList, filter1.getIn());
        assertEquals(longFilter, filter1);
        assertAllNullExceptIn(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(integerFilter, intList);
        assertNotNull(filter2);
        assertEquals(intList, filter2.getIn());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptIn(filter2);

        final StringFilter filter3 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(stringFilter, stringList);
        assertNotNull(filter3);
        assertEquals(stringList, filter3.getIn());
        assertEquals(stringFilter, filter3);
        assertAllNullExceptIn(filter3);

//        final BooleanFilter filter4 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(booleanFilter, boolList);
//        assertNotNull(filter4);
//        assertEquals(boolList, filter4.getEquals());
//        assertEquals(booleanFilter, filter4);
//        assertAllNullExceptIn(filter4);

        final InstantFilter filter5 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(instantFilter, instantList);
        assertNotNull(filter5);
        assertEquals(instantList, filter5.getIn());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptIn(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(localDateFilter, dateList);
        assertNotNull(filter6);
        assertEquals(dateList, filter6.getIn());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptIn(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(doubleFilter, doubleList);
        assertNotNull(filter7);
        assertEquals(doubleList, filter7.getIn());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptIn(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(floatFilter, floatList);
        assertNotNull(filter8);
        assertEquals(floatList, filter8.getIn());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptIn(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(bigDecimalFilter, decimalList);
        assertNotNull(filter9);
        assertEquals(decimalList, filter9.getIn());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptIn(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildInCriteriaOrThrowNoReplace(shortFilter, shortList);
        assertNotNull(filter10);
        assertEquals(shortList, filter10.getIn());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptIn(filter10);
    }

    @Test
    void buildInCriteriaOrThrowNoReplaceThrowsOnNullValue() {
        assertThrows(NullPointerException.class, () -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, null));
    }

    @Test
    void buildInCriteriaOrThrowNoReplaceThrowsOnEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, Collections.emptyList()));
    }

    @Test
    void buildInCriteriaOrThrowThrowsOnInValueNotInAllowed() {
        longFilter.setIn(Arrays.asList(44949494L, 5494L));
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, longList));
        assertEquals("Some in filter specified are not allowed", ex.getMessage());
    }

    @Test
    void buildInCriteriaOrThrowDoNotThrowsOnInValueInAllowed() {
        longFilter.setIn(Collections.singletonList(longList.get(0)));
        assertDoesNotThrow(() -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, longList));
    }

    @Test
    void buildInCriteriaOrThrowDoNotThrowsOnEqualValueInAllowed() {
        longFilter.setEquals(longList.get(0));
        assertDoesNotThrow(() -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, longList));
    }

    @Test
    void buildInCriteriaOrThrowThrowsOnEqualValueNotInAllowed() {
        longFilter.setEquals(44949494L);
        final IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, longList));
        assertEquals("Equals filter specified is not allowed", ex.getMessage());
    }

    @Test
    void buildInCriteriaOrThrowChangeToEqualWhenOnlyValueIn() {
        final LongFilter filter = CriteriaUtil.buildInCriteriaOrThrow(longFilter, Collections.singletonList(55L));
        assertNotNull(filter.getEquals());
        assertEquals(55L, filter.getEquals());
        assertAllNullExceptEquals(filter);
    }

    @Test
    void buildInCriteriaOrThrowNoReplaceChangeToEqualWhenOnlyValueIn() {
        final LongFilter filter = CriteriaUtil.buildInCriteriaOrThrowNoReplace(longFilter, Collections.singletonList(55L));
        assertNotNull(filter.getEquals());
        assertEquals(55L, filter.getEquals());
        assertAllNullExceptEquals(filter);
    }

    @Test
    void checkInNotEmpty() {
        assertThrows(IllegalStateException.class, () -> CriteriaUtil.checkInNotEmpty(filterLong));
        filterLong.setIn(Collections.emptyList());
        assertThrows(IllegalStateException.class, () -> CriteriaUtil.checkInNotEmpty(filterLong));
        filterLong.setIn(Arrays.asList(1L, 2L));
        assertDoesNotThrow(() -> CriteriaUtil.checkInNotEmpty(filterLong));
    }

    @Test
    void buildContainsCriteria() {
        final StringFilter any = CriteriaUtil.buildContainsCriteria("any");
        assertNotNull(any.getContains());
        assertEquals("any", any.getContains());
    }

    @Test
    void buildContainsCriteria1() {
        final StringFilter any = CriteriaUtil.buildContainsCriteria(null, "any");
        assertNotNull(any.getContains());
        assertEquals("any", any.getContains());
    }

    @Test
    void buildContainsCriteriaOrThrow() {
        stringFilter.setContains("onlyAllowed");
        final StringFilter any = CriteriaUtil.buildContainsCriteriaOrThrow(stringFilter, "onlyAllowed");
    }

    @Test
    void buildContainsCriteriaOrThrowNotCaseSensitive() {
        stringFilter.setContains("ONLYALLOWED");
        final StringFilter any = CriteriaUtil.buildContainsCriteriaOrThrow(stringFilter, "onlyAllowed");
    }

    @Test
    void buildContainsCriteriaOrThrowThrowsOnBadValue() {
        stringFilter.setContains("notAllowed");
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildContainsCriteriaOrThrow(stringFilter, "onlyAllowed"));
    }

    @Test
    void buildSpecifiedCriteria() {
        final LongFilter filter = CriteriaUtil.buildSpecifiedCriteria(LongFilter.class, null, true, false);
        assertNotNull(filter);
        assertTrue(filter.getSpecified());
    }

    @Test
    void buildGreaterThanCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(longFilter, 1L);
        assertEquals(1L, filter1.getGreaterThan());
        assertEquals(longFilter, filter1);
        assertAllNullExceptGreaterThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(integerFilter, 1);
        assertEquals(1, filter2.getGreaterThan());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptGreaterThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(instantFilter, NOW);
        assertEquals(NOW, filter5.getGreaterThan());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptGreaterThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getGreaterThan());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptGreaterThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getGreaterThan());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptGreaterThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getGreaterThan());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptGreaterThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getGreaterThan());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptGreaterThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanCriteriaOrThrow(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getGreaterThan());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptGreaterThan(filter10);
    }

    @Test
    void buildGreaterThanCriteriaOrThrowThrowsIfDifferent() {
        longFilter.setGreaterThan(1L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildGreaterThanCriteriaOrThrow(longFilter, 5L));
        longFilter.setGreaterThan(8L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildGreaterThanCriteriaOrThrow(longFilter, 5L));
        longFilter.setGreaterThan(5L);
        assertDoesNotThrow(() -> CriteriaUtil.buildGreaterThanCriteriaOrThrow(longFilter, 5L));
    }

    @Test
    void buildGreaterThanCriteriaOrThrowOrMore() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(longFilter, 1L);
        assertEquals(1L, filter1.getGreaterThan());
        assertEquals(longFilter, filter1);
        assertAllNullExceptGreaterThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(integerFilter, 1);
        assertEquals(1, filter2.getGreaterThan());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptGreaterThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(instantFilter, NOW);
        assertEquals(NOW, filter5.getGreaterThan());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptGreaterThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getGreaterThan());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptGreaterThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getGreaterThan());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptGreaterThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getGreaterThan());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptGreaterThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getGreaterThan());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptGreaterThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getGreaterThan());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptGreaterThan(filter10);
    }

    @Test
    void buildGreaterThanCriteriaOrThrowOrMoreCheck() {
        longFilter.setGreaterThan(1L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(longFilter, 5L));
        longFilter.setGreaterThan(5L);
        assertDoesNotThrow(() -> CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(longFilter, 5L));
        longFilter.setGreaterThan(8L);
        assertDoesNotThrow(() -> CriteriaUtil.buildGreaterThanCriteriaOrThrowOrMore(longFilter, 5L));
    }

    @Test
    void buildGreaterThanCriteria() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanCriteria(1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getGreaterThan());
        assertAllNullExceptGreaterThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanCriteria(1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getGreaterThan());
        assertAllNullExceptGreaterThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanCriteria(NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getGreaterThan());
        assertAllNullExceptGreaterThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanCriteria(NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getGreaterThan());
        assertAllNullExceptGreaterThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanCriteria(DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getGreaterThan());
        assertAllNullExceptGreaterThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanCriteria(FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getGreaterThan());
        assertAllNullExceptGreaterThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanCriteria(DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getGreaterThan());
        assertAllNullExceptGreaterThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanCriteria(SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getGreaterThan());
        assertAllNullExceptGreaterThan(filter10);
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(longFilter, 1L);
        assertEquals(1L, filter1.getGreaterThanOrEqual());
        assertEquals(longFilter, filter1);
        assertAllNullExceptGreaterThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(integerFilter, 1);
        assertEquals(1, filter2.getGreaterThanOrEqual());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptGreaterThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(instantFilter, NOW);
        assertEquals(NOW, filter5.getGreaterThanOrEqual());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptGreaterThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getGreaterThanOrEqual());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptGreaterThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getGreaterThanOrEqual());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptGreaterThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getGreaterThanOrEqual());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptGreaterThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getGreaterThanOrEqual());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptGreaterThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrow(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getGreaterThanOrEqual());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptGreaterThanOrEqual(filter10);
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrowOrMore() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(longFilter, 1L);
        assertEquals(1L, filter1.getGreaterThanOrEqual());
        assertEquals(longFilter, filter1);
        assertAllNullExceptGreaterThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(integerFilter, 1);
        assertEquals(1, filter2.getGreaterThanOrEqual());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptGreaterThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(instantFilter, NOW);
        assertEquals(NOW, filter5.getGreaterThanOrEqual());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptGreaterThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getGreaterThanOrEqual());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptGreaterThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getGreaterThanOrEqual());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptGreaterThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getGreaterThanOrEqual());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptGreaterThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getGreaterThanOrEqual());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptGreaterThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getGreaterThanOrEqual());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptGreaterThanOrEqual(filter10);
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrowOrMoreAcceptsHigher() {
        longFilter.setGreaterThanOrEqual(1L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(longFilter, 5L));
        longFilter.setGreaterThanOrEqual(5L);
        assertDoesNotThrow(() -> CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(longFilter, 5L));
        longFilter.setGreaterThanOrEqual(10L);
        assertDoesNotThrow(() -> CriteriaUtil.buildGreaterThanOrEqualCriteriaOrThrowOrMore(longFilter, 5L));
    }

    @Test
    void buildGreaterThanOrEqualCriteria() {
        final LongFilter filter1 = CriteriaUtil.buildGreaterThanOrEqualCriteria(1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildGreaterThanOrEqualCriteria(1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildGreaterThanOrEqualCriteria(NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildGreaterThanOrEqualCriteria(NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildGreaterThanOrEqualCriteria(DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildGreaterThanOrEqualCriteria(FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildGreaterThanOrEqualCriteria(DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildGreaterThanOrEqualCriteria(SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getGreaterThanOrEqual());
        assertAllNullExceptGreaterThanOrEqual(filter10);
    }

    @Test
    void buildLessThanCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanCriteriaOrThrow(longFilter, 1L);
        assertEquals(1L, filter1.getLessThan());
        assertEquals(longFilter, filter1);
        assertAllNullExceptLessThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanCriteriaOrThrow(integerFilter, 1);
        assertEquals(1, filter2.getLessThan());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptLessThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanCriteriaOrThrow(instantFilter, NOW);
        assertEquals(NOW, filter5.getLessThan());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptLessThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanCriteriaOrThrow(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getLessThan());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptLessThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanCriteriaOrThrow(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getLessThan());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptLessThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanCriteriaOrThrow(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getLessThan());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptLessThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanCriteriaOrThrow(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getLessThan());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptLessThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanCriteriaOrThrow(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getLessThan());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptLessThan(filter10);
    }

    @Test
    void buildLessThanCriteriaOrThrowThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanCriteriaOrThrow(longFilter, null));
    }

    @Test
    void buildLessThanCriteriaOrThrowOrLess() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(longFilter, 1L);
        assertEquals(1L, filter1.getLessThan());
        assertEquals(longFilter, filter1);
        assertAllNullExceptLessThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(integerFilter, 1);
        assertEquals(1, filter2.getLessThan());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptLessThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(instantFilter, NOW);
        assertEquals(NOW, filter5.getLessThan());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptLessThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getLessThan());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptLessThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getLessThan());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptLessThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getLessThan());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptLessThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getLessThan());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptLessThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getLessThan());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptLessThan(filter10);
    }

    @Test
    void buildLessThanCriteriaOrThrowOrLessThrowsOnNotCorrect() {
        longFilter.setLessThan(1L);
        assertDoesNotThrow(() -> CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(longFilter, 5L));
        longFilter.setLessThan(5L);
        assertDoesNotThrow(() -> CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(longFilter, 5L));
        longFilter.setLessThan(8L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(longFilter, 5L));
    }

    @Test
    void buildLessThanCriteriaOrThrowOrLessThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanCriteriaOrThrowOrLess(longFilter, null));
    }

    @Test
    void buildLessThanCriteria() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanCriteria(1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getLessThan());
        assertAllNullExceptLessThan(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanCriteria(1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getLessThan());
        assertAllNullExceptLessThan(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanCriteria(NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getLessThan());
        assertAllNullExceptLessThan(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanCriteria(NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getLessThan());
        assertAllNullExceptLessThan(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanCriteria(DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getLessThan());
        assertAllNullExceptLessThan(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanCriteria(FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getLessThan());
        assertAllNullExceptLessThan(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanCriteria(DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getLessThan());
        assertAllNullExceptLessThan(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanCriteria(SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getLessThan());
        assertAllNullExceptLessThan(filter10);
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrow() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(longFilter, 1L);
        assertEquals(1L, filter1.getLessThanOrEqual());
        assertEquals(longFilter, filter1);
        assertAllNullExceptLessThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(integerFilter, 1);
        assertEquals(1, filter2.getLessThanOrEqual());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptLessThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(instantFilter, NOW);
        assertEquals(NOW, filter5.getLessThanOrEqual());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptLessThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getLessThanOrEqual());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptLessThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getLessThanOrEqual());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptLessThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getLessThanOrEqual());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptLessThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getLessThanOrEqual());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptLessThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getLessThanOrEqual());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptLessThanOrEqual(filter10);
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOnNull() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanOrEqualCriteriaOrThrow(longFilter, null));
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOrLess() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(longFilter, 1L);
        assertEquals(1L, filter1.getLessThanOrEqual());
        assertEquals(longFilter, filter1);
        assertAllNullExceptLessThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(integerFilter, 1);
        assertEquals(1, filter2.getLessThanOrEqual());
        assertEquals(integerFilter, filter2);
        assertAllNullExceptLessThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(instantFilter, NOW);
        assertEquals(NOW, filter5.getLessThanOrEqual());
        assertEquals(instantFilter, filter5);
        assertAllNullExceptLessThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(localDateFilter, NOW_DATE);
        assertEquals(NOW_DATE, filter6.getLessThanOrEqual());
        assertEquals(localDateFilter, filter6);
        assertAllNullExceptLessThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(doubleFilter, DOUBLE);
        assertEquals(DOUBLE, filter7.getLessThanOrEqual());
        assertEquals(doubleFilter, filter7);
        assertAllNullExceptLessThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(floatFilter, FLOAT);
        assertEquals(FLOAT, filter8.getLessThanOrEqual());
        assertEquals(floatFilter, filter8);
        assertAllNullExceptLessThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(bigDecimalFilter, DECIMAL);
        assertEquals(DECIMAL, filter9.getLessThanOrEqual());
        assertEquals(bigDecimalFilter, filter9);
        assertAllNullExceptLessThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(shortFilter, SHORT);
        assertEquals(SHORT, filter10.getLessThanOrEqual());
        assertEquals(shortFilter, filter10);
        assertAllNullExceptLessThanOrEqual(filter10);
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOrLessThrowsOnNotCorrect() {
        longFilter.setLessThanOrEqual(11L);
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(longFilter, 5L));
        longFilter.setLessThanOrEqual(5L);
        assertDoesNotThrow(() -> CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(longFilter, 5L));
        longFilter.setLessThanOrEqual(1L);
        assertDoesNotThrow(() -> CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(longFilter, 5L));
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOrLessThrowsOnNull() {
        assertThrows(IllegalArgumentException.class, () -> CriteriaUtil.buildLessThanOrEqualCriteriaOrThrowOrLess(longFilter, null));
    }

    @Test
    void buildLessThanOrEqualCriteria() {
        final LongFilter filter1 = CriteriaUtil.buildLessThanOrEqualCriteria(1L);
        assertNotNull(filter1);
        assertEquals(1L, filter1.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter1);

        final IntegerFilter filter2 = CriteriaUtil.buildLessThanOrEqualCriteria(1);
        assertNotNull(filter2);
        assertEquals(1, filter2.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter2);

        final InstantFilter filter5 = CriteriaUtil.buildLessThanOrEqualCriteria(NOW);
        assertNotNull(filter5);
        assertEquals(NOW, filter5.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter5);

        final LocalDateFilter filter6 = CriteriaUtil.buildLessThanOrEqualCriteria(NOW_DATE);
        assertNotNull(filter6);
        assertEquals(NOW_DATE, filter6.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter6);

        final DoubleFilter filter7 = CriteriaUtil.buildLessThanOrEqualCriteria(DOUBLE);
        assertNotNull(filter7);
        assertEquals(DOUBLE, filter7.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter7);

        final FloatFilter filter8 = CriteriaUtil.buildLessThanOrEqualCriteria(FLOAT);
        assertNotNull(filter8);
        assertEquals(FLOAT, filter8.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter8);

        final BigDecimalFilter filter9 = CriteriaUtil.buildLessThanOrEqualCriteria(DECIMAL);
        assertNotNull(filter9);
        assertEquals(DECIMAL, filter9.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter9);

        final ShortFilter filter10 = CriteriaUtil.buildLessThanOrEqualCriteria(SHORT);
        assertNotNull(filter10);
        assertEquals(SHORT, filter10.getLessThanOrEqual());
        assertAllNullExceptLessThanOrEqual(filter10);
    }

}
