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
package org.blackdread.lib.restfilter.spring.filter;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base service for constructing and executing complex queries with jOOQ.
 * Build condition based on filter passed, for more complex conditions, might require to manually write it instead of using this class.
 * <p>Created on 2019/07/23</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated Will not delete, just API not sure yet, it could be defined as static methods in fact
 */
@Transactional(readOnly = true)
public interface JooqQueryService {

    default <X, T extends Field<X>> Condition buildCondition(final Filter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

    default <X extends Comparable<? super X>, T extends Field<X>> Condition buildCondition(final RangeFilter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        }

        Condition condition = DSL.trueCondition();
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

    default <T extends Field<String>> Condition buildCondition(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        } else if (filter.getContains() != null) {
            return field.contains(filter.getContains());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

    /**
     * @param filter filter
     * @param field  field
     * @param <T>    type
     * @return condition
     * @deprecated not sure of API, could add a boolean in StringFilter so user of API can specify ignore case in url, would not be another field in a criteria class or other to make the distinction
     */
    default <T extends Field<String>> Condition buildConditionIgnoreCase(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equalIgnoreCase(filter.getEquals());
        } else if (filter.getIn() != null) {
            // TODO ignore case? what about in values, iterate and make it upper ?
//            return DSL.upper(field).in(filter.getIn());
            return field.in(filter.getIn());
        } else if (filter.getContains() != null) {
            return field.containsIgnoreCase(filter.getContains());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

}
