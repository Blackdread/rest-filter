package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    private DurationFilter durationFilter;
    private InstantFilter instantFilter;
    private LocalDateFilter localDateFilter;
    private ZonedDateTimeFilter zonedDateTimeFilter;

    private StringFilter stringFilter;
    private UUIDFilter uuidFilter;

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

        durationFilter = new DurationFilter();
        instantFilter = new InstantFilter();
        localDateFilter = new LocalDateFilter();
        zonedDateTimeFilter = new ZonedDateTimeFilter();

        stringFilter = new StringFilter();
        uuidFilter = new UUIDFilter();
    }

    @Test
    void createCriteria() {
    }

    @Test
    void isCriteriaOverlap() {
    }

    @Test
    void isCriteriaOverlap1() {
    }

    @Test
    void isCriteriaOverlap2() {
    }

    @Test
    void isEqualsDefined() {
    }

    @Test
    void isInDefined() {
    }

    @Test
    void isContainsDefined() {
    }

    @Test
    void isSpecifiedDefined() {
    }

    @Test
    void isGreaterThanDefined() {
    }

    @Test
    void isGreaterThanOrEqualDefined() {
    }

    @Test
    void isLessThanDefined() {
    }

    @Test
    void isLessThanOrEqualDefined() {
    }

    @Test
    void isAnyRangeDefined() {
    }

    @Test
    void buildEqualsCriteria() {
    }

    @Test
    void buildEqualsCriteria1() {
    }

    @Test
    void buildEqualsCriteriaOrThrow() {
    }

    @Test
    void buildEqualsCriteria2() {
    }

    @Test
    void buildEqualsCriteria3() {
    }

    @Test
    void buildEqualsCriteriaOrThrow1() {
    }

    @Test
    void buildEqualsCriteria4() {
    }

    @Test
    void buildEqualsCriteria5() {
    }

    @Test
    void buildEqualsCriteriaOrThrow2() {
    }

    @Test
    void buildEqualsCriteria6() {
    }

    @Test
    void buildEqualsCriteria7() {
    }

    @Test
    void buildEqualsCriteriaOrThrow3() {
    }

    @Test
    void buildEqualsCriteria8() {
    }

    @Test
    void buildEqualsCriteria9() {
    }

    @Test
    void buildEqualsCriteriaOrThrow4() {
    }

    @Test
    void buildEqualsCriteria10() {
    }

    @Test
    void buildEqualsCriteria11() {
    }

    @Test
    void buildEqualsCriteriaOrThrow5() {
    }

    @Test
    void buildEqualsCriteria12() {
    }

    @Test
    void buildEqualsCriteria13() {
    }

    @Test
    void buildEqualsCriteriaOrThrow6() {
    }

    @Test
    void buildEqualsCriteria14() {
    }

    @Test
    void buildEqualsCriteria15() {
    }

    @Test
    void buildEqualsCriteriaOrThrow7() {
    }

    @Test
    void buildEqualsCriteria16() {
    }

    @Test
    void buildEqualsCriteria17() {
    }

    @Test
    void buildEqualsCriteriaOrThrow8() {
    }

    @Test
    void buildEqualsCriteria18() {
    }

    @Test
    void buildEqualsCriteria19() {
    }

    @Test
    void buildEqualsCriteriaOrThrow9() {
    }

    @Test
    void buildInCriteria() {
    }

    @Test
    void buildInCriteria1() {
    }

    @Test
    void buildInCriteria2() {
    }

    @Test
    void buildInCriteriaFiltered() {
    }

    @Test
    void buildInCriteriaFiltered1() {
    }

    @Test
    void buildInCriteriaOrThrow() {
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
