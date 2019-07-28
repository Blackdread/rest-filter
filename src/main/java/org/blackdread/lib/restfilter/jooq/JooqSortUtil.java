package org.blackdread.lib.restfilter.jooq;

import org.jooq.Field;
import org.jooq.Select;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Created on 2019/07/24.</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not sure of API
 */
public class JooqSortUtil {

    private static final Logger log = LoggerFactory.getLogger(JooqSortUtil.class);

    // todo maybe do a immutable builder so can pass default sorting (when no sorting or etc), etc, build once, can be build in each repo/service/etc. And options can be put in the builder so different logic while providing the abstraction of creating dynamic sort
    //   so this util class will create a default builder and use it

    /**
     * @param sort   sort
     * @param select select
     * @return sort fields
     * @deprecated not sure of that yet
     */
    @Deprecated
    static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Select<?> select) {
        final List<Field<?>> fields = select.getSelect();
        return buildOrderBy(sort, fields);
    }

    public static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields) {
        if (fields == null || fields.length == 0)
            return Collections.emptyList();
        return buildOrderBy(sort, Arrays.asList(fields));
    }

    /**
     * @param sort   the sort to build, property names must match field name (so careful with field alias etc)
     * @param fields fields that are part of the select so can be ordered
     * @return Sort fields to be used in the orderBy query of jooq
     */
    public static Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields) {
        if (sort == null) {
            return Collections.emptyList();
        }

        final Map<String, ? extends Field<?>> nameFieldMap = fields.stream()
            .collect(Collectors.toMap(Field::getName, field -> field));

        return sort.stream()
            .filter(Objects::nonNull)
            .map(order -> {
                final Field<?> field = nameFieldMap.get(order.getProperty());
                if (field == null) {
                    // todo ignore not found. See if we throw instead
                    log.warn("Did not find field with name {} in {}", order.getProperty(), fields);
                    return null;
                }
                return convertToSortField(field, order);
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public static <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Order order) {
        final Sort.Direction direction = order.getDirection();
        final Sort.NullHandling nullHandling = order.getNullHandling();
        final boolean ignoreCase = order.isIgnoreCase();

        final Field<T> fieldCase;
        if (ignoreCase && String.class.equals(field.getType())) {
            Field<T> tmp;
            // See https://www.jooq.org/doc/3.11/manual/sql-building/column-expressions/case-sensitivity/
            try {
                tmp = (Field<T>) DSL.upper((Field<String>) field);
            } catch (ClassCastException e) {
                tmp = field;
            }
            fieldCase = tmp;
        } else {
            fieldCase = field;
        }
        final SortField<T> result;
        switch (direction) {
            case ASC:
                result = fieldCase.asc();
                break;
            case DESC:
                result = fieldCase.desc();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + direction);
        }
        switch (nullHandling) {
            case NATIVE:
                // do nothing
                break;
            case NULLS_FIRST:
                result.nullsFirst();
                break;
            case NULLS_LAST:
                result.nullsLast();
                break;
        }

        return result;
    }

    private static <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Direction direction) {
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
