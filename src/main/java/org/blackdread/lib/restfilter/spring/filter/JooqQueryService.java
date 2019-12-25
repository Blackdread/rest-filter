/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
import org.blackdread.lib.restfilter.jooq.JooqFilterUtil;
import org.jooq.Condition;
import org.jooq.Field;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service contract for constructing and executing complex queries with jOOQ.
 * Build condition based on filter passed, for more complex conditions, might require to manually write it instead of using this class.
 * <p>Created on 2019/07/23</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated Will not delete, just API not sure yet, better to use directly the util class with the static methods
 */
@Transactional(readOnly = true)
public interface JooqQueryService {

    default <X, T extends Field<X>> Condition buildCondition(final Filter<X> filter, final T field) {
        return JooqFilterUtil.buildCondition(filter, field);
    }

    default <X extends Comparable<? super X>, T extends Field<X>> Condition buildCondition(final RangeFilter<X> filter, final T field) {
        return JooqFilterUtil.buildCondition(filter, field);
    }

    default <T extends Field<String>> Condition buildCondition(final StringFilter filter, final T field) {
        return JooqFilterUtil.buildCondition(filter, field);
    }

    /**
     *
     * @param filter filter
     * @param field field
     * @param <T> field type
     * @return condition
     * @deprecated not sure of API, could add a boolean in StringFilter so user of API can specify ignore case in url, would not be another field in a criteria class or other to make the distinction
     */
    default <T extends Field<String>> Condition buildConditionIgnoreCase(final StringFilter filter, final T field) {
        return JooqFilterUtil.buildConditionIgnoreCase(filter, field);
    }

}
