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
package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.SortField;
import org.springframework.data.domain.Sort;

import java.util.Collection;

/**
 * <p>Created on 2019/08/02.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface JooqSort {

    /**
     * @param sort the sort to use (from REST usually), property names must match aliases
     * @return Sort fields to be used in the orderBy query of jooq
     * @throws IllegalStateException if this was built without any alias (no alias would always return an empty collection or the default sort if was provided)
     */
    Collection<? extends SortField<?>> buildOrderBy(final Sort sort);

    /**
     * If aliases were defined at creation of this {@link JooqSort}, those will match first instead of fields passed (may change on implementation).
     *
     * @param sort   the sort to use (from REST usually), property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields);

    /**
     * If aliases were defined at creation of this {@link JooqSort}, those will match first instead of fields passed (may change on implementation).
     *
     * @param sort   the sort to use (from REST usually), property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields);

    // todo add support for sort by index (inline) -> kind of already part of buildOrderBy/etc
//    Collection<? extends SortField<?>> buildOrderByInline(final Sort sort, ...);
}
