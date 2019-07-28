package org.blackdread.lib.restfilter.criteria;

import javafx.util.Duration;
import org.blackdread.lib.restfilter.filter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/05/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaUtilTest {

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
    private static final Duration DURATION = Duration.seconds(50);
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

    private static void fillAll(final LongFilter filter) {
        filter.setEquals(1L);
        filter.setIn(Arrays.asList(1L, 2L));
        filter.setSpecified(true);
        filter.setGreaterThan(1L);
        filter.setGreaterThanOrEqual(1L);
        filter.setLessThan(1L);
        filter.setLessThanOrEqual(1L);
    }

    private static void fillAll(final StringFilter filter) {
        filter.setEquals("any");
        filter.setIn(Arrays.asList("any", "any2"));
        filter.setSpecified(true);
        filter.setContains("any");
    }

    private static void assertAllNullExceptEquals(final Filter<?> filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getIn());
        assertNull(filter.getSpecified());
    }

    private static void assertAllNullExceptEquals(final RangeFilter<?> filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptEquals(final StringFilter filter) {
        assertNotNull(filter.getEquals());
        assertNull(filter.getIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getContains());
    }

    private static void assertAllNullExceptIn(final Filter<?> filter) {
        assertNull(filter.getEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getSpecified());
    }

    private static void assertAllNullExceptIn(final RangeFilter<?> filter) {
        assertNull(filter.getEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getGreaterThan());
        assertNull(filter.getGreaterThanOrEqual());
        assertNull(filter.getLessThan());
        assertNull(filter.getLessThanOrEqual());
    }

    private static void assertAllNullExceptIn(final StringFilter filter) {
        assertNull(filter.getEquals());
        assertNotNull(filter.getIn());
        assertNull(filter.getSpecified());
        assertNull(filter.getContains());
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
    void buildEqualsCriteria1() {
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
    void buildInCriteriaFiltered() {
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
    void checkInNotEmpty() {
    }

    @Test
    void buildContainsCriteria() {
    }

    @Test
    void buildContainsCriteria1() {
    }

    @Test
    void buildContainsCriteria2() {
    }

    @Test
    void buildContainsCriteriaOrThrow() {
    }

    @Test
    void buildSpecifiedCriteria() {
    }

    @Test
    void buildGreaterThanCriteriaOrThrow() {
    }

    @Test
    void buildGreaterThanCriteriaOrThrowOrMore() {
    }

    @Test
    void buildGreaterThanCriteria() {
    }

    @Test
    void buildGreaterThanCriteriaOrThrow1() {
    }

    @Test
    void buildGreaterThanCriteriaOrThrowOrMore1() {
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrow() {
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrowOrMore() {
    }

    @Test
    void buildGreaterThanOrEqualCriteria() {
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrow1() {
    }

    @Test
    void buildGreaterThanOrEqualCriteriaOrThrowOrMore1() {
    }

    @Test
    void buildLessThanCriteriaOrThrow() {
    }

    @Test
    void buildLessThanCriteriaOrThrowOrLess() {
    }

    @Test
    void buildLessThanCriteria() {
    }

    @Test
    void buildLessThanCriteriaOrThrow1() {
    }

    @Test
    void buildLessThanCriteriaOrThrowOrLess1() {
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrow() {
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOrLess() {
    }

    @Test
    void buildLessThanOrEqualCriteria() {
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrow1() {
    }

    @Test
    void buildLessThanOrEqualCriteriaOrThrowOrLess1() {
    }
}
