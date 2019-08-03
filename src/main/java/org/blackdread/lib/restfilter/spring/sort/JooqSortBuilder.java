package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.Param;
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

    private final Map<String, Collection<Field<?>>> fieldByAliasMap = new HashMap<>();

    private final Map<String, Collection<Param<?>>> inlineByAliasMap = new HashMap<>();

    private Sort defaultSort;

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
        this.enableCaseInsensitiveSort = copy.enableCaseInsensitiveSort;
        this.enableNullHandling = copy.enableNullHandling;
        this.throwOnSortPropertyNotFound = copy.throwOnSortPropertyNotFound;
        this.throwOnAliasNotFound = copy.throwOnAliasNotFound;
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
        checkAliasNotAlreadyDefined(alias);
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.inlineByAliasMap.put(alias, Arrays.asList(indexes).stream().map(DSL::inline).collect(Collectors.toList()));
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
     * <br/>
     * When a property/alias is not found, it is possible to default to this ordering, it is applied only if all sort in {@link Sort} passed are not found and appropriate "throws on not found" set to false.
     *
     * @param defaultSort default sort to use
     * @return new {@code JooqSortBuilder} instance (for chaining)
     */
    public JooqSortBuilder withDefaultOrdering(final Sort defaultSort) {
        final JooqSortBuilder copy = new JooqSortBuilder(this);
        copy.defaultSort = defaultSort;
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

    private void checkAliasNotAlreadyDefined(final String alias) {
        if (fieldByAliasMap.containsKey(alias))
            throw new IllegalArgumentException(String.format("Alias \"%s\" already defined", alias));
    }

    // fallbackOnSortPropertyNotFound
    // fallbackOnAliasNotFound
}
