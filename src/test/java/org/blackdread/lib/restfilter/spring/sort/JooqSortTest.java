package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/08/04.</p>
 *
 * @author Yoann CAPLAIN
 */
class JooqSortTest {

    private JooqSortBuilder builder;

    private static final String ALIAS_1 = "any";
    private static final String ALIAS_2 = "any2";
    private static final String ALIAS_3 = "any3";

    private static final Field<Long> fieldLong = DSL.field("my_long", Long.class);
    private static final Field<String> fieldString = DSL.field("my_string", String.class);

    private static final Field<Integer> fieldAlias1 = DSL.field(ALIAS_1, Integer.class);
    private static final Field<String> fieldAlias2 = DSL.field(ALIAS_2, String.class);
    private static final Field<String> fieldAlias3 = DSL.field(ALIAS_3, String.class);

    private static final Sort UNSORTED = Sort.unsorted();
    private static final Sort SORT_1 = Sort.by(Sort.Order.asc(ALIAS_1));
    private static final Sort SORT_1_2 = Sort.by(Sort.Order.asc(ALIAS_1), Sort.Order.desc(ALIAS_2));
    private static final Sort SORT_1_2_3 = Sort.by(Sort.Order.asc(ALIAS_1), Sort.Order.desc(ALIAS_2), Sort.Order.asc(ALIAS_3));
    private static final Sort SORT_NULL_HANDLE = Sort.by(Sort.Order.asc(ALIAS_1).nullsLast(), Sort.Order.desc(ALIAS_2).nullsFirst(), Sort.Order.asc(ALIAS_3));
    private static final Sort SORT_IGNORE_CASE = Sort.by(Sort.Order.asc(ALIAS_1).ignoreCase(), Sort.Order.desc(ALIAS_2), Sort.Order.asc(ALIAS_3).ignoreCase());
    private static final Sort SORT_ALL = Sort.by(Sort.Order.asc(ALIAS_1).nullsLast().ignoreCase(), Sort.Order.desc(ALIAS_2).nullsFirst(), Sort.Order.asc(ALIAS_3).nullsLast().ignoreCase());

    private static final Collection<Field<?>> FIELDS_1_2 = Arrays.asList(fieldLong, fieldString);

    private static final Collection<SortField<?>> SORT_1_2_FIELDS = Arrays.asList(fieldLong.asc(), fieldString.desc());
    private static final Collection<SortField<?>> SORT_1_2_FIELDS_ALIAS = Arrays.asList(fieldLong.asc().nullsFirst(), fieldString.desc(), fieldAlias1.asc(), fieldAlias2.desc().nullsLast());

    @BeforeEach
    void setUp() {
        builder = JooqSortBuilder.newBuilder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buildOrderByThrowsIfNoAlias() {
        final JooqSort jooqSort = builder.build();
        assertThrows(IllegalStateException.class, () -> jooqSort.buildOrderBy(UNSORTED));
        assertThrows(IllegalStateException.class, () -> jooqSort.buildOrderBy(SORT_1_2));
    }

    @Test
    void buildOrderByDoesNotThrowsIfNoAliasForFieldsPassed() {
        final JooqSort jooqSort = builder.build();
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(UNSORTED, fieldLong));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(SORT_1_2, FIELDS_1_2));
    }

    @Test
    void buildOrderByThrowsOnNullSort() {
        final JooqSort jooqSort = builder
            .addAlias("anything", DSL.inline(1)) // yes it is possible to do
            .addAlias("anything2", fieldLong)
            .build();
        assertThrows(NullPointerException.class, () -> jooqSort.buildOrderBy(null));
        assertThrows(NullPointerException.class, () -> jooqSort.buildOrderBy(null, fieldLong, fieldString));
        assertThrows(NullPointerException.class, () -> jooqSort.buildOrderBy(null, FIELDS_1_2));
    }

    @Test
    void buildOrderByCanTakeNull() {
        final JooqSort jooqSort = builder.build();
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(UNSORTED, (Field<?>) null));
        assertThrows(NullPointerException.class, () -> jooqSort.buildOrderBy(UNSORTED, Collections.singleton(null)));
    }

    @Test
    void buildOrderByWithSort() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_1);

        assertEquals(1, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
    }

    @Test
    void buildOrderByWithSortInline() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias("anything", fieldLong)
            .addAliasInline(ALIAS_2, 5)
            .addAliasInline(ALIAS_3, 2)
            .withDefaultOrdering(SORT_1)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_1_2_3);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
        assertEquals(DSL.inline(5).desc(), result.get(1));
        assertEquals(DSL.inline(2).asc(), result.get(2));
    }

    @Test
    void buildOrderByWithDefaultSort() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias("anything", fieldLong)
            .withDefaultOrdering(SORT_1)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(UNSORTED);

        assertEquals(1, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
    }

    @Test
    void buildOrderByWithDefaultSortFields() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias("anything", fieldLong)
            .withDefaultOrdering(SORT_1_2_FIELDS)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(UNSORTED);

        assertEquals(2, result.size());
        assertEquals(fieldLong.asc(), result.get(0));
        assertEquals(fieldString.desc(), result.get(1));
        assertNotSame(SORT_1_2_FIELDS, result);
    }

    @Test
    void buildOrderByCaseOk() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias(ALIAS_2, fieldAlias2)
            .addAlias(ALIAS_3, fieldAlias3)
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_IGNORE_CASE);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
        assertEquals(fieldAlias2.desc(), result.get(1));
        assertEquals(DSL.upper(fieldAlias3).asc(), result.get(2));
    }

    @Test
    void buildOrderByCaseNoAliasOk() {
        final JooqSort jooqSort = builder
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_IGNORE_CASE, fieldAlias1, fieldAlias2, fieldAlias3);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
        assertEquals(fieldAlias2.desc(), result.get(1));
        assertEquals(DSL.upper(fieldAlias3).asc(), result.get(2));
    }

    @Test
    void buildOrderByNullHandlingOk() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias(ALIAS_2, fieldAlias2)
            .addAlias(ALIAS_3, fieldAlias3)
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_NULL_HANDLE);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc().nullsLast(), result.get(0));
        assertEquals(fieldAlias2.desc().nullsFirst(), result.get(1));
        assertEquals(fieldAlias3.asc(), result.get(2));
    }

    @Test
    void buildOrderByNullHandlingNoAliasOk() {
        final JooqSort jooqSort = builder
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_NULL_HANDLE, fieldAlias1, fieldAlias2, fieldAlias3);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc().nullsLast(), result.get(0));
        assertEquals(fieldAlias2.desc().nullsFirst(), result.get(1));
        assertEquals(fieldAlias3.asc(), result.get(2));
    }

    @Test
    void buildOrderByCaseAndNullHandlingOk() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias(ALIAS_2, fieldAlias2)
            .addAlias(ALIAS_3, fieldAlias3)
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_ALL);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc().nullsLast().nullsLast(), result.get(0));
        assertEquals(fieldAlias2.desc().nullsFirst().nullsFirst(), result.get(1));
        assertEquals(DSL.upper(fieldAlias3).asc().nullsLast(), result.get(2));
    }

    @Test
    void buildOrderByCaseAndNullHandlingNoAliasOk() {
        final JooqSort jooqSort = builder
            .build();

        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_ALL, fieldAlias1, fieldAlias2, fieldAlias3);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc().nullsLast().nullsLast(), result.get(0));
        assertEquals(fieldAlias2.desc().nullsFirst().nullsFirst(), result.get(1));
        assertEquals(DSL.upper(fieldAlias3).asc().nullsLast(), result.get(2));
    }

    @Test
    void buildOrderByThrowsOnAliasNotFound() {
        final JooqSort jooqSort = builder
            .addAlias("missing", fieldLong)
            .throwOnSortPropertyNotFound(false)
            .build();
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(SORT_1_2));

        final JooqSort jooqSort2 = builder
            .throwOnAliasNotFound(false)
            .build();
        assertThrows(IllegalArgumentException.class, () -> jooqSort2.buildOrderBy(SORT_1_2, fieldLong));
    }

    @Test
    void buildOrderByDoesNotThrowsOnAliasNotFoundIfSet() {
        final JooqSort jooqSort = builder
            .addAlias("missing", fieldLong)
            .throwOnAliasNotFound(false)
            .build();
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(SORT_1_2));

        final JooqSort jooqSort2 = builder
            .throwOnSortPropertyNotFound(false)
            .build();
        assertDoesNotThrow(() -> jooqSort2.buildOrderBy(SORT_1_2, fieldLong));
    }

//    @Test
//    void buildOrderBy() {
//    }
}
