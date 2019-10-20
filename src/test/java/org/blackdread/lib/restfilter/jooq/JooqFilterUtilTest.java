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

import static org.jooq.impl.DSL.noCondition;
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
        stringFilter.setNotEquals("any2");
        stringFilter.setIn(Arrays.asList("any", "any2"));
        stringFilter.setNotIn(Arrays.asList("any3", "any4"));
        stringFilter.setSpecified(true);
        stringFilter.setContains("any");
        stringFilter.setNotContains("any2");

        longField = DSL.field("myLong", Long.class);
        stringField = DSL.field("myString", String.class);
    }

    @Test
    void buildConditionNone() {
        final Condition condition = JooqFilterUtil.buildCondition((Filter<Long>) new LongFilter(), longField);
        Assertions.assertEquals(noCondition(), condition);
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
        Assertions.assertEquals(noCondition(), condition);
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
        Assertions.assertEquals(noCondition().and(longField.isNotNull()), condition);

        longFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(noCondition().and(longField.isNull()), condition2);
    }

    @Test
    void buildRangeConditionOthers() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(noCondition(), longField.isNotNull(), longField.greaterThan(1L), longField.greaterOrEqual(1L), longField.lessThan(1L), longField.lessOrEqual(1L)), condition);

        longFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(noCondition(), longField.isNull(), longField.greaterThan(1L), longField.greaterOrEqual(1L), longField.lessThan(1L), longField.lessOrEqual(1L)), condition2);
    }

    @Test
    void buildRangeConditionFew() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        longFilter.setSpecified(null);
        longFilter.setGreaterThan(null);
        longFilter.setLessThan(null);
        final Condition condition = JooqFilterUtil.buildCondition(longFilter, longField);
        Assertions.assertEquals(DSL.and(noCondition(), longField.greaterOrEqual(1L), longField.lessOrEqual(1L)), condition);
    }

    @Test
    void buildStringConditionNone() {
        final Condition condition = JooqFilterUtil.buildCondition(new StringFilter(), stringField);
        Assertions.assertEquals(noCondition(), condition);
    }

    @Test
    void buildStringConditionEquals() {
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.equalIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionEqualsCase() {
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.equal("any"), condition);
    }

    @Test
    void buildStringConditionNotEquals() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.notEqualIgnoreCase("any2"), condition);
    }

    @Test
    void buildStringConditionNotEqualsCase() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.notEqual("any2"), condition);
    }

    @Test
    void buildStringConditionIn() {
        stringFilter.setEquals(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(DSL.upper(stringField).in(Stream.of("any", "any2").map(String::toUpperCase).collect(Collectors.toSet())), condition);
    }

    @Test
    void buildStringConditionInCase() {
        stringFilter.setEquals(null);
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.in(Arrays.asList("any", "any2")), condition);
    }

    @Test
    void buildStringConditionNotIn() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(DSL.upper(stringField).notIn(Stream.of("any3", "any4").map(String::toUpperCase).collect(Collectors.toSet())), condition);
    }

    @Test
    void buildStringConditionNotInCase() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.notIn(Arrays.asList("any3", "any4")), condition);
    }

    @Test
    void buildStringConditionContains() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.containsIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionContainsCase() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setNotContains(null);
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.contains("any"), condition);
    }

    @Test
    void buildStringConditionNotContains() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.notContainsIgnoreCase("any2"), condition);
    }

    @Test
    void buildStringConditionNotContainsCase() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setIgnoreCase(false);
        final Condition condition = JooqFilterUtil.buildCondition(stringFilter, stringField);
        Assertions.assertEquals(stringField.notContains("any2"), condition);
    }

    @Test
    void buildStringConditionSpecified() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
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
    void buildStringConditionIgnoreCaseNotEquals() {
        stringFilter.setEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.notEqualIgnoreCase("any2"), condition);
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
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setSpecified(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.containsIgnoreCase("any"), condition);
    }

    @Test
    void buildStringConditionIgnoreCaseSpecified() {
        stringFilter.setEquals(null);
        stringFilter.setNotEquals(null);
        stringFilter.setIn(null);
        stringFilter.setNotIn(null);
        stringFilter.setContains(null);
        stringFilter.setNotContains(null);
        final Condition condition = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNotNull(), condition);

        stringFilter.setSpecified(false);
        final Condition condition2 = JooqFilterUtil.buildConditionIgnoreCase(stringFilter, stringField);
        Assertions.assertEquals(stringField.isNull(), condition2);
    }

}
