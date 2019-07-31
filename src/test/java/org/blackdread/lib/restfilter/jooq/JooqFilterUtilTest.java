package org.blackdread.lib.restfilter.jooq;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.trueCondition;

/**
 * <p>Created on 2019/07/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class JooqFilterUtilTest {

    private LongFilter longFilter;

    private StringFilter stringFilter;

    private Field<Long> longField;

    private Field<String> stringField;

    @BeforeEach
    void setUp() {
        longFilter = new LongFilter();
        longFilter.setEquals(1L);
        longFilter.setIn(Arrays.asList(1L, 2L));
        longFilter.setSpecified(true);
        longFilter.setGreaterThan(1L);
        longFilter.setGreaterThanOrEqual(1L);
        longFilter.setLessThan(1L);
        longFilter.setLessThanOrEqual(1L);

        stringFilter = new StringFilter();
        stringFilter.setEquals("any");
        stringFilter.setIn(Arrays.asList("any", "any2"));
        stringFilter.setSpecified(true);
        stringFilter.setContains("any");

        longField = DSL.field("myLong", Long.class);
        stringField = DSL.field("myString", String.class);
    }

    @Test
    void buildConditionNone() {
        final Condition condition = JooqFilterUtil.buildCondition((Filter<Long>) new LongFilter(), longField);
        Assertions.assertEquals(trueCondition(), condition);
    }

    @Test
    void buildConditionEquals() {
        final Condition condition = JooqFilterUtil.buildCondition((Filter<Long>) longFilter, longField);
        Assertions.assertEquals(longField.equal(1L), condition);
    }

    @Test
    void buildConditionIn() {
        longFilter.setEquals(null);
        final Condition condition = JooqFilterUtil.buildCondition((Filter<Long>) longFilter, longField);
        Assertions.assertEquals(longField.in(Arrays.asList(1L, 2L)), condition);
    }

    @Test
    void buildConditionSpecified() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        final Condition condition = JooqFilterUtil.buildCondition((Filter<Long>) longFilter, longField);
        Assertions.assertEquals(longField.isNotNull(), condition);

        longFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition((Filter<Long>) longFilter, longField);
        Assertions.assertEquals(longField.isNull(), condition2);
    }

    @Test
    void buildRangeConditionNone() {
        final Condition condition = JooqFilterUtil.buildCondition(new LongFilter(), longField);
        Assertions.assertEquals(trueCondition(), condition);
    }

    @Test
    void buildRangeConditionEquals() {
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(longField.equal(1L), condition);
    }

    @Test
    void buildRangeConditionIn() {
        longFilter.setEquals(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(longField.in(Arrays.asList(1L, 2L)), condition);
    }

    @Test
    void buildRangeConditionSpecified() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        longFilter.setGreaterThan(null);
        longFilter.setGreaterThanOrEqual(null);
        longFilter.setLessThan(null);
        longFilter.setLessThanOrEqual(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(trueCondition().and(longField.isNotNull()), condition);

        longFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(trueCondition().and(longField.isNull()), condition2);
    }

    @Test
    void buildRangeConditionOthers() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(trueCondition(),longField.isNotNull(), longField.greaterThan(1L), longField.greaterOrEqual(1L), longField.lessThan(1L), longField.lessOrEqual(1L)), condition);

        longFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(trueCondition(),longField.isNull(), longField.greaterThan(1L), longField.greaterOrEqual(1L), longField.lessThan(1L), longField.lessOrEqual(1L)), condition2);
    }

    @Test
    void buildRangeConditionFew() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        longFilter.setSpecified(null);
        longFilter.setGreaterThan(null);
        longFilter.setLessThan(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(trueCondition(),longField.greaterOrEqual(1L), longField.lessOrEqual(1L)), condition);
    }

    @Test
    void buildStringConditionNone() {
        final Condition condition = JooqFilterUtil.buildCondition(new StringFilter(), stringField);
        Assertions.assertEquals(trueCondition(), condition);
    }

    @Test
    void buildStringConditionEquals() {
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.equal("any"), condition);
    }

    @Test
    void buildStringConditionIn() {
        stringFilter.setEquals(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.in(Arrays.asList("any", "any2")), condition);
    }

    @Test
    void buildStringConditionContains() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.containsIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionSpecified() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        stringFilter.setContains(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNotNull(), condition);

        stringFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNull(), condition2);
    }

    @Test
    void buildStringConditionIgnoreCaseNone() {
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(new StringFilter(), stringField);
        Assertions.assertEquals(trueCondition(), condition);
    }

    @Test
    void buildStringConditionIgnoreCaseEquals() {
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.equalIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionIgnoreCaseIn() {
        stringFilter.setEquals(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(DSL.upper(stringField).in(Stream.of("any", "any2").map(String::toUpperCase).collect(Collectors.toSet())), condition);
    }

    @Test
    void buildStringConditionIgnoreCaseContains() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.containsIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionIgnoreCaseSpecified() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        stringFilter.setContains(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNotNull(), condition);

        stringFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNull(), condition2);
    }

}
