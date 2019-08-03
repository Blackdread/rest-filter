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

    Collection<? extends SortField<?>> buildOrderBy(final Sort sort);

    /**
     * If aliases were defined at creation of this {@link JooqSort}, those will match first instead of fields passed.
     *
     * @param sort   the sort to use (from REST usually), property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields);

    /**
     * If aliases were defined at creation of this {@link JooqSort}, those will match first instead of fields passed.
     *
     * @param sort   the sort to use (from REST usually), property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields);

    // todo add support for sort by index (inline) -> kind of already part of buildOrderBy/etc
//    Collection<? extends SortField<?>> buildOrderByInline(final Sort sort, ...);
}
