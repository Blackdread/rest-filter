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
import org.jooq.Param;
import org.jooq.SortField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

    // todo might in fact need to ignore case of alias and fields params name, could be another option set to true by default, most of the time id == ID or my_column == MY_COLUMN

    private final Map<String, Collection<Field<?>>> fieldByAliasMap;

    private final Map<String, Collection<Param<Integer>>> inlineByAliasMap;

    // Only one of defaultSort/defaultSortFields may be not null -> todo I forgot why, maybe both could be defined
    private final Sort defaultSort;

    private final List<? extends SortField<?>> defaultSortFields;

    private final boolean enableCaseInsensitiveSort;

    private final boolean enableNullHandling;

    private final boolean throwOnSortPropertyNotFound;

    private final boolean throwOnAliasNotFound;

    JooqSortImpl(Map<String, Collection<Field<?>>> fieldByAliasMap, Map<String, Collection<Param<Integer>>> inlineByAliasMap, @Nullable Sort defaultSort, @Nullable Collection<? extends SortField<?>> defaultSortFields, boolean enableCaseInsensitiveSort, boolean enableNullHandling, boolean throwOnSortPropertyNotFound, boolean throwOnAliasNotFound) {
        this.fieldByAliasMap = Collections.unmodifiableMap(new HashMap<>(fieldByAliasMap));
        this.inlineByAliasMap = Collections.unmodifiableMap(new HashMap<>(inlineByAliasMap));
        this.defaultSortFields = defaultSortFields == null ? null : new ArrayList<>(defaultSortFields);
        this.defaultSort = defaultSort;
        this.enableCaseInsensitiveSort = enableCaseInsensitiveSort;
        this.enableNullHandling = enableNullHandling;
        this.throwOnSortPropertyNotFound = throwOnSortPropertyNotFound;
        this.throwOnAliasNotFound = throwOnAliasNotFound;
    }

    @Override
    public List<? extends SortField<?>> buildOrderBy(final Sort sort) {
        if (fieldByAliasMap.isEmpty() && inlineByAliasMap.isEmpty()) {
            throw new IllegalStateException("No alias were defined, method with Sort only is not supported");
        }

        final List<? extends SortField<?>> result = getSortFields(sort);

        if (!result.isEmpty()) {
            return result;
        }

        // Below both default sort are supported at the same time
        List<? extends SortField<?>> resultWithDefault = Collections.emptyList();
        if (defaultSort != null) {
            resultWithDefault = getSortFields(defaultSort);
        }
        if (defaultSortFields != null && resultWithDefault.isEmpty()) {
            return defaultSortFields;
        }
        return resultWithDefault;
    }

    private List<? extends SortField<?>> getSortFields(final Sort sort) {
        return sort.stream()
            .map(order -> {
                final String property = order.getProperty();
                final Collection<Param<Integer>> aliasParams = inlineByAliasMap.get(property);
                final Collection<Field<?>> aliasFields = fieldByAliasMap.get(property);
                if (aliasParams != null) {
                    return aliasParams.stream()
                        .map(param -> convertToSortField(param, order))
                        .collect(Collectors.toList());
                } else if (aliasFields != null) {
                    return aliasFields.stream()
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
    public List<? extends SortField<?>> buildOrderBy(final Sort sort, final Field<?>... fields) {
        if (fields == null || fields.length == 0 || Arrays.stream(fields).allMatch(Objects::isNull)) {
            return buildOrderBy(sort, Collections.emptyList());
        }
        return buildOrderBy(sort, Arrays.asList(fields));
    }

    @Override
    public List<? extends SortField<?>> buildOrderBy(final Sort sort, final Collection<Field<?>> fields) {
        final List<? extends SortField<?>> result = getSortFields(sort, fields);

        if (!result.isEmpty()) {
            return result;
        }

        // Below both default sort are supported at the same time
        List<? extends SortField<?>> resultWithDefault = Collections.emptyList();
        if (defaultSort != null) {
            resultWithDefault = getSortFields(defaultSort, fields);
        }
        if (defaultSortFields != null && resultWithDefault.isEmpty()) {
            return defaultSortFields;
        }
        return resultWithDefault;
    }

    private List<? extends SortField<?>> getSortFields(final Sort sort, final Collection<Field<?>> fields) {
        final Map<String, ? extends Field<?>> nameFieldMap = fields.stream()
            .collect(Collectors.toMap(Field::getName, field -> field));

        return sort.stream()
            .map(order -> {
                final String property = order.getProperty();
                final Collection<Param<Integer>> aliasParams = inlineByAliasMap.get(property);
                final Collection<Field<?>> aliasFields = fieldByAliasMap.get(property);

                // aliases have priority over fields passed
                if (aliasParams != null) {
                    return aliasParams.stream()
                        .map(param -> convertToSortField(param, order))
                        .collect(Collectors.toList());
                } else if (aliasFields != null) {
                    return aliasFields.stream()
                        .map(field -> convertToSortField(field, order))
                        .collect(Collectors.toList());
                } else if (!fields.isEmpty()) {
                    final Field<?> field = nameFieldMap.get(order.getProperty());
                    if (field != null) {
                        final SortField<?> sortField = convertToSortField(field, order);
                        return Collections.singletonList(sortField);
                    }
                }

                if (throwOnSortPropertyNotFound) {
                    throw new IllegalArgumentException(String.format("Property '%s' not found", property));
                }
                log.info("Property '{}' not found but ignored", property);
                return null;
            })
            .filter(Objects::nonNull)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private <T> SortField<T> convertToSortField(final Field<T> field, final Sort.Order order) {
        final Sort.Direction direction = order.getDirection();
        final Sort.NullHandling nullHandling = order.getNullHandling();
        final boolean ignoreCase = order.isIgnoreCase();

        final Field<T> fieldCase;
        if (ignoreCase && enableCaseInsensitiveSort) {
            fieldCase = JooqSortUtil.tryConvertSortIgnoreCase(field);
        } else {
            fieldCase = field;
        }
        final SortField<T> sortField = JooqSortUtil.convertToSortField(fieldCase, direction);
        if (enableNullHandling) {
            JooqSortUtil.applyNullHandling(sortField, nullHandling);
        }

        return sortField;
    }

}
