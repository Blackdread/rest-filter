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
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Immutable builder, methods that would modify the builder creates a new builder copied and alters the copy before returning it.
 * <p>Created on 2019/08/02.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
public class JooqSortBuilder {

    private static final Logger log = LoggerFactory.getLogger(JooqSortBuilder.class);

    // todo might in fact need to ignore case of alias and fields params name, could be another option set to true by default, most of the time id == ID or my_column == MY_COLUMN

    // fieldByAliasMap and inlineByAliasMap are differentiated but in fact could be one single Map
    private final Map<String, Collection<Field<?>>> fieldByAliasMap = new HashMap<>();

    private final Map<String, Collection<Param<Integer>>> inlineByAliasMap = new HashMap<>();

    private Sort defaultSort;

    private Collection<? extends SortField<?>> defaultSortFields;

    private boolean enableCaseInsensitiveSort = true;

    private boolean enableNullHandling = true;

    private boolean throwOnSortPropertyNotFound = true;

    private boolean throwOnAliasNotFound = true;

    private JooqSortBuilder() {
    }

    private JooqSortBuilder(final JooqSortBuilder copy) {
        this.fieldByAliasMap.putAll(copy.fieldByAliasMap);
        this.inlineByAliasMap.putAll(copy.inlineByAliasMap);
        this.defaultSort = copy.defaultSort;
        this.defaultSortFields = copy.defaultSortFields != null ? new ArrayList<>(copy.defaultSortFields) : null;
        this.enableCaseInsensitiveSort = copy.enableCaseInsensitiveSort;
        this.enableNullHandling = copy.enableNullHandling;
        this.throwOnSortPropertyNotFound = copy.throwOnSortPropertyNotFound;
        this.throwOnAliasNotFound = copy.throwOnAliasNotFound;
    }

    /**
     * Constructs a new {@code JooqSortBuilder} instance with default settings.
     *
     * @return builder
     */
    public static JooqSortBuilder newBuilder() {
        return new JooqSortBuilder();
    }

    /**
     * The alias will activate one field for sorting purpose
     *
     * @param alias property name to use
     * @param field field that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAlias(final String alias, final Field<?> field) {
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.fieldByAliasMap.put(alias, Collections.singleton(field));
        return copy;
    }

    /**
     * The alias will activate many fields for sorting purpose
     *
     * @param alias  property name to use
     * @param fields fields that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAlias(final String alias, final Field<?>... fields) {
        checkNotEmpty(fields);
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.fieldByAliasMap.put(alias, Arrays.asList(fields));
        return copy;
    }

    /**
     * The alias will activate many fields for sorting purpose
     *
     * @param alias  property name to use
     * @param fields fields that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAlias(final String alias, final Collection<Field<?>> fields) {
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.fieldByAliasMap.put(alias, new ArrayList<>(fields));
        return copy;
    }

    /**
     * The alias will activate one field index for sorting purpose
     *
     * @param alias property name to use
     * @param index index that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAliasInline(final String alias, final int index) {
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.inlineByAliasMap.put(alias, Collections.singleton(DSL.inline(index)));
        return copy;
    }

    /**
     * The alias will activate many field indexes for sorting purpose
     *
     * @param alias   property name to use
     * @param indexes indexes that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAliasInline(final String alias, final int... indexes) {
        checkNotEmpty(indexes);
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.inlineByAliasMap.put(alias, Arrays.stream(indexes).mapToObj(DSL::inline).collect(Collectors.toList()));
        return copy;
    }

    /**
     * The alias will activate many field indexes for sorting purpose
     *
     * @param alias   property name to use
     * @param indexes indexes that will be sorted if alias is encountered
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder addAliasInline(final String alias, final Collection<Integer> indexes) {
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.inlineByAliasMap.put(alias, indexes.stream().map(DSL::inline).collect(Collectors.toList()));
        return copy;
    }

    /**
     * @param enableCaseInsensitiveSort enable or not case insensitive sorting if specified in Sort
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder enableCaseInsensitiveSort(final boolean enableCaseInsensitiveSort) {
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.enableCaseInsensitiveSort = enableCaseInsensitiveSort;
        return copy;
    }

    /**
     * @param enableNullHandling enable or not null handling sorting if specified in Sort
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder enableNullHandling(final boolean enableNullHandling) {
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.enableNullHandling = enableNullHandling;
        return copy;
    }

    /**
     * Is used when methods from {@link JooqSort} receive parameters that resolve to no sorting.
     * <br>
     * The sorting is hard-coded but may be dynamic, in opposite to {@link #withDefaultOrdering(Collection)}.
     * <br>
     * When a property/alias is not found, it is possible to default to this ordering, it is applied only if all sort in {@link Sort} passed are not found and appropriate "throws on not found" set to false.
     * <br>
     * It is exclusive with {@link #withDefaultOrdering(Collection)}, will throw if other one was defined.
     *
     * @param defaultSort default sort to use
     * @return new {@code JooqSortBuilder} instance (for chaining)
     * @throws IllegalStateException if {@link #withDefaultOrdering(Collection)} is defined (non null)
     */
    public JooqSortBuilder withDefaultOrdering(final Sort defaultSort) {
        if (this.defaultSortFields != null) {
            throw new IllegalStateException("Default sort with fields is already defined");
        }
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.defaultSort = defaultSort;
        return copy;
    }

    /**
     * Is used when methods from {@link JooqSort} receive parameters that resolve to no sorting.
     * <br>
     * The sorting is hard-coded as all SortFields are defined in advance, in opposite to {@link #withDefaultOrdering(Sort)}.
     * <br>
     * When a property/alias is not found, it is possible to default to this ordering, it is applied only if all sort in {@link Sort} passed are not found and appropriate "throws on not found" set to false.
     * <br>
     * It is exclusive with {@link #withDefaultOrdering(Sort)}, will throw if other one was defined.
     *
     * @param defaultSortFields default sort fields to use
     * @return new {@code JooqSortBuilder} instance (for chaining)
     * @throws IllegalStateException if {@link #withDefaultOrdering(Sort)} is defined (non null)
     */
    public JooqSortBuilder withDefaultOrdering(final Collection<? extends SortField<?>> defaultSortFields) {
        if (this.defaultSort != null) {
            throw new IllegalStateException("Default sort is already defined");
        }
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.defaultSortFields = defaultSortFields == null ? null : new ArrayList<>(defaultSortFields);
        return copy;
    }

    /**
     * @param sortPropertyNotFoundThrows when using {@link JooqSort#buildOrderBy(Sort, Field[])} or {@link JooqSort#buildOrderBy(Sort, Collection)}, will throw if property is not found in fields passed
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder throwOnSortPropertyNotFound(final boolean sortPropertyNotFoundThrows) {
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.throwOnSortPropertyNotFound = sortPropertyNotFoundThrows;
        return copy;
    }

    /**
     * @param aliasNotFoundThrows when using {@link JooqSort#buildOrderBy(Sort)}, will throw if alias is not found in aliases given when this builder was built
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder throwOnAliasNotFound(final boolean aliasNotFoundThrows) {
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.throwOnAliasNotFound = aliasNotFoundThrows;
        return copy;
    }

    // todo can pass a custom sort property/field/alias resolver

    /**
     * This method does not alter the state of this {@code JooqSortBuilder} instance, so it can be
     * invoked again to create multiple independent JooqSort.
     *
     * @return a JooqSort with features specified in this builder
     */
    public JooqSort build() {
        return new JooqSortImpl(
            fieldByAliasMap,
            inlineByAliasMap,
            defaultSort,
            defaultSortFields,
            enableCaseInsensitiveSort,
            enableNullHandling,
            throwOnSortPropertyNotFound,
            throwOnAliasNotFound
        );
    }

    private void checkNotEmpty(final Field[] fields) {
        if (fields == null || fields.length == 0)
            throw new IllegalArgumentException("Fields cannot be null/empty");
    }

    private void checkNotEmpty(int[] indexes) {
        if (indexes == null || indexes.length == 0)
            throw new IllegalArgumentException("Fields cannot be null/empty");
    }

    private void checkAliasNotAlreadyDefined(final String alias) {
        if (fieldByAliasMap.containsKey(alias))
            throw new IllegalStateException(String.format("Field alias \"%s\" already defined", alias));
        if (inlineByAliasMap.containsKey(alias))
            throw new IllegalStateException(String.format("Inline alias \"%s\" already defined", alias));
    }

    // fallbackOnSortPropertyNotFound
    // fallbackOnAliasNotFound
}
