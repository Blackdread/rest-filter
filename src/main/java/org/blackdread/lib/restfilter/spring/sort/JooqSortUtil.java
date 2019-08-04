package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;

/**
 * <p>Created on 2019/07/24.</p>
 *
 * @author Yoann CAPLAIN
 */
public class JooqSortUtil {

    private static final Logger log = LoggerFactory.getLogger(JooqSortUtil.class);

    private static final JooqSort DEFAULT_JOOQ_SORT = JooqSortBuilder.newBuilder()
        .build();

    // todo provide a static method to provide alias for fields so Sort can use different property name than back-end db name, etc

    /**
     * @param sort   the sort to build, property names must match field name (so careful with field alias etc)
     * @param select select from which fields can be extracted and used for ordering
     * @return Sort fields to be used in the orderBy query of jooq
     * @deprecated not sure of that yet
     */
    @Deprecated
    static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Select<?> select) {
        final List<Field<?>> fields = select.getSelect();
        return DEFAULT_JOOQ_SORT.buildOrderBy(sort, fields);
    }

    /**
     * @param sort   the sort to build, property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    public static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields) {
        return DEFAULT_JOOQ_SORT.buildOrderBy(sort, fields);
    }

    /**
     * @param sort   the sort to build, property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    public static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields) {
        return DEFAULT_JOOQ_SORT.buildOrderBy(sort, fields);
    }

    // todo add static support for sort by index (inline)

    public static <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Order order) {
        final Sort.Direction direction = order.getDirection();
        final Sort.NullHandling nullHandling = order.getNullHandling();
        final boolean ignoreCase = order.isIgnoreCase();

        final Field<T> fieldCase;
        if (ignoreCase) {
            fieldCase = tryConvertSortIgnoreCase(field);
        } else {
            fieldCase = field;
        }
        final SortField<T> sortField = convertToSortField(fieldCase, direction);
        applyNullHandling(sortField, nullHandling);

        return sortField;
    }

    /**
     * Try to apply a sort that ignore case on the field, if fails then return the field passed
     *
     * @param field field to sort on
     * @param <T>   field type
     * @return new field with sort ignore case or passed field
     */
    static <T> Field<T> tryConvertSortIgnoreCase(final Field<T> field) {
        if (!String.class.equals(field.getType())) {
            return field;
        }

        // See https://www.jooq.org/doc/3.11/manual/sql-building/column-expressions/case-sensitivity/
        try {
            return (Field<T>) DSL.upper((Field<String>) field);
        } catch (ClassCastException e) {
            log.info("Failed to cast {} to Field<String>", field);
            return field;
        }
    }

    /**
     * jOOQ modifies the field data so no object returned
     *
     * @param sortField    sort field to apply null handling
     * @param nullHandling type to apply
     * @param <T>          sortField type
     */
    static <T> void applyNullHandling(SortField<T> sortField, Sort.NullHandling nullHandling) {
        switch (nullHandling) {
            case NATIVE:
                // do nothing
                break;
            case NULLS_FIRST:
                sortField.nullsFirst();
                break;
            case NULLS_LAST:
                sortField.nullsLast();
                break;
        }
    }

    static <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Direction direction) {
        switch (direction) {
            case ASC:
                return field.asc();
            case DESC:
                return field.desc();
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
    }

    private JooqSortUtil() {
    }
}
