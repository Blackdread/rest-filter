/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.stream.Collectors;

/**
 * Build condition based on filter passed, for more complex conditions, might require to manually write it instead of using this class.
 * Class is not final to let user shadow or add static methods while keeping.
 * See <a>https://www.jooq.org/doc/3.13/manual/sql-building/column-expressions/case-sensitivity/</a> for filtering without case sensitivity.
 * <p>Created on 2019/07/23</p>
 *
 * @author Yoann CAPLAIN
 */
public class JooqFilterUtil {

    public static <X, T extends Field<X>> Condition buildCondition(final Filter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        }
        Condition condition = DSL.noCondition();
        if (filter.getNotEquals() != null) {
            condition = condition.and(field.notEqual(filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            condition = condition.and(field.notIn(filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            condition = condition.and(filter.getSpecified() ? field.isNotNull() : field.isNull());
        }
        return condition;
    }

    public static <X extends Comparable<? super X>, T extends Field<X>> Condition buildCondition(final RangeFilter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        }

        Condition condition = DSL.noCondition();
        if (filter.getNotEquals() != null) {
            condition = condition.and(field.notEqual(filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            condition = condition.and(field.notIn(filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            condition = condition.and(filter.getSpecified() ? field.isNotNull() : field.isNull());
        }
        if (filter.getGreaterThan() != null) {
            condition = condition.and(field.greaterThan(filter.getGreaterThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            condition = condition.and(field.greaterOrEqual(filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThan() != null) {
            condition = condition.and(field.lessThan(filter.getLessThan()));
        }
        if (filter.getLessThanOrEqual() != null) {
            condition = condition.and(field.lessOrEqual(filter.getLessThanOrEqual()));
        }
        return condition;
    }

    public static <T extends Field<String>> Condition buildCondition(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return filter.isIgnoreCase() ?
                field.equalIgnoreCase(filter.getEquals()) :
                field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return filter.isIgnoreCase() ?
                DSL.upper(field).in(filter.getIn().stream().map(String::toUpperCase).collect(Collectors.toSet())) :
                field.in(filter.getIn());
        }

        Condition condition = DSL.noCondition();
        if (filter.getNotEquals() != null) {
            condition = filter.isIgnoreCase() ?
                condition.and(field.notEqualIgnoreCase(filter.getNotEquals())) :
                condition.and(field.notEqual(filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            condition = condition.and(filter.isIgnoreCase() ?
                DSL.upper(field).notIn(filter.getNotIn().stream().map(String::toUpperCase).collect(Collectors.toSet())) :
                field.notIn(filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            condition = condition.and(filter.getSpecified() ? field.isNotNull() : field.isNull());
        }
        if (filter.getContains() != null) {
            condition = condition.and(filter.isIgnoreCase() ?
                field.containsIgnoreCase(filter.getContains()) :
                field.contains(filter.getContains()));
        }
        if (filter.getNotContains() != null) {
            condition = condition.and(filter.isIgnoreCase() ?
                field.notContainsIgnoreCase(filter.getNotContains()) :
                field.notContains(filter.getNotContains()));
        }

        return condition;
    }

    /**
     * @param filter filter
     * @param field  field
     * @param <T>    field type
     * @return condition
     * @deprecated prefer {@link #buildCondition(StringFilter, Field)} as it supports conditional ignore case
     */
    @Deprecated
    public static <T extends Field<String>> Condition buildConditionIgnoreCase(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equalIgnoreCase(filter.getEquals());
        } else if (filter.getIn() != null) {
            return DSL.upper(field).in(filter.getIn().stream().map(String::toUpperCase).collect(Collectors.toSet()));
        }

        Condition condition = DSL.noCondition();
        if (filter.getNotEquals() != null) {
            condition = condition.and(field.notEqualIgnoreCase(filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            condition = condition.and(DSL.upper(field).notIn(filter.getNotIn().stream().map(String::toUpperCase).collect(Collectors.toSet())));
        }
        if (filter.getSpecified() != null) {
            condition = condition.and(filter.getSpecified() ? field.isNotNull() : field.isNull());
        }
        if (filter.getContains() != null) {
            condition = condition.and(field.containsIgnoreCase(filter.getContains()));
        }
        if (filter.getNotContains() != null) {
            condition = condition.and(field.notContainsIgnoreCase(filter.getNotContains()));
        }

        return condition;
    }

    private JooqFilterUtil() {
    }
}
