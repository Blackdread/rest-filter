package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.Param;
import org.jooq.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Created on 2019/08/03.</p>
 *
 * @author Yoann CAPLAIN
 */
public class JooqSortImpl implements JooqSort {

    private static final Logger log = LoggerFactory.getLogger(JooqSortImpl.class);

    private final Map<String, Collection<Field<?>>> fieldByAliasMap;

    private final Map<String, Collection<Param<Integer>>> inlineByAliasMap;

    // Only one of defaultSort/defaultSortFields may be not null -> todo I forgot why, maybe both could be defined
    private final Sort defaultSort;

    private final Collection<? extends SortField<?>> defaultSortFields;

    private final boolean enableCaseInsensitiveSort;

    private final boolean enableNullHandling;

    private final boolean throwOnSortPropertyNotFound;

    private final boolean throwOnAliasNotFound;

    JooqSortImpl(Map<String, Collection<Field<?>>> fieldByAliasMap, Map<String, Collection<Param<Integer>>> inlineByAliasMap, @Nullable Sort defaultSort, @Nullable Collection<? extends SortField<?>> defaultSortFields, boolean enableCaseInsensitiveSort, boolean enableNullHandling, boolean throwOnSortPropertyNotFound, boolean throwOnAliasNotFound) {
        this.fieldByAliasMap = Collections.unmodifiableMap(new HashMap<>(fieldByAliasMap));
        this.inlineByAliasMap = Collections.unmodifiableMap(new HashMap<>(inlineByAliasMap));
        this.defaultSortFields = defaultSortFields;
        this.defaultSort = defaultSort;
        this.enableCaseInsensitiveSort = enableCaseInsensitiveSort;
        this.enableNullHandling = enableNullHandling;
        this.throwOnSortPropertyNotFound = throwOnSortPropertyNotFound;
        this.throwOnAliasNotFound = throwOnAliasNotFound;
    }

    @Override
    public Collection<? extends SortField<?>> buildOrderBy(final Sort sort) {
        if (fieldByAliasMap.isEmpty() && inlineByAliasMap.isEmpty()) {
            throw new IllegalStateException("No alias were defined, method with Sort only is not supported");
        }

        final List<? extends SortField<?>> result = getSortFields(sort);

        if (!result.isEmpty()) {
            return result;
        }

        List<? extends SortField<?>> resultWithDefault = Collections.emptyList();
        if (defaultSort != null) {
            resultWithDefault = getSortFields(defaultSort);
        }
        if (defaultSortFields != null && resultWithDefault.isEmpty()) {
            return defaultSortFields;
        }
        return defaultSortFields;
    }

    private List<? extends SortField<?>> getSortFields(final Sort sort) {
        return sort.stream()
            .map(order -> {
                final String property = order.getProperty();
                final Collection<Param<Integer>> params = inlineByAliasMap.get(property);
                final Collection<Field<?>> fields = fieldByAliasMap.get(property);
                if (params != null) {
                    return params.stream()
                        .map(param -> convertToSortField(param, order))
                        .collect(Collectors.toList());
                } else if (fields != null) {
                    return fields.stream()
                        .map(field -> convertToSortField(field, order))
                        .collect(Collectors.toList());
                } else {
                    if (throwOnAliasNotFound) {
                        throw new IllegalArgumentException(String.format("Property '%s' not found", property));
                    }
                    log.info("Property '{}' not found but ignored", property);
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields) {
        if (fields == null || fields.length == 0) {
            log.info("Fields null or empty");
            return getDefaultOrEmpty();
        }
        return buildOrderBy(sort, Arrays.asList(fields));
    }

    @Override
    public Collection<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields) {
        return null;
    }

    private Collection<? extends SortField<?>> getDefaultOrEmpty() {
        // todo default sort
        return Collections.emptyList();
    }

    private <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Order order) {
        final Sort.Direction direction = order.getDirection();
        final Sort.NullHandling nullHandling = order.getNullHandling();
        final boolean ignoreCase = order.isIgnoreCase();

        final Field<T> fieldCase;
        if (ignoreCase) {
            fieldCase = JooqSortUtil.tryConvertSortIgnoreCase(field);
        } else {
            fieldCase = field;
        }
        final SortField<T> sortField = JooqSortUtil.convertToSortField(fieldCase, direction);
        JooqSortUtil.applyNullHandling(sortField, nullHandling);

        return sortField;
    }

}
