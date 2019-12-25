/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
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
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    void buildOrderByFieldsWithSortInline() {
        final JooqSort jooqSort = builder
            .addAlias(ALIAS_1, fieldAlias1)
            .addAlias("anything", fieldLong)
            .addAliasInline(ALIAS_3, 2)
            .withDefaultOrdering(SORT_1)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(SORT_1_2_3, fieldAlias2);

        assertEquals(3, result.size());
        assertEquals(fieldAlias1.asc(), result.get(0));
        assertEquals(fieldAlias2.desc(), result.get(1));
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
    void buildOrderByFieldsWithDefaultSort() {
        final JooqSort jooqSort = builder
            .addAlias("anything", fieldLong)
            .withDefaultOrdering(SORT_1)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(UNSORTED, fieldLong, fieldAlias1);

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
    void buildOrderByFieldsWithDefaultSortFields() {
        final JooqSort jooqSort = builder
            .addAlias("anything", fieldLong)
            .withDefaultOrdering(SORT_1_2_FIELDS)
            .build();
        final List<? extends SortField<?>> result = jooqSort.buildOrderBy(UNSORTED, fieldLong, fieldAlias1);

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
        final List<? extends SortField<?>> sortFields = assertDoesNotThrow(() -> jooqSort.buildOrderBy(SORT_1_2));
        assertTrue(sortFields.isEmpty());

        final JooqSort jooqSort2 = builder
            .throwOnSortPropertyNotFound(false)
            .build();
        final List<? extends SortField<?>> sortFields2 = assertDoesNotThrow(() -> jooqSort2.buildOrderBy(SORT_1_2, fieldLong));
        assertTrue(sortFields2.isEmpty());
    }

    @Test
    void fieldsCanHideOthers() {
        final JooqSort jooqSort = builder
            .build();

        final List<? extends SortField<?>> sortFields = assertDoesNotThrow(() -> jooqSort.buildOrderBy(SORT_1_2, fieldAlias1, fieldAlias2, DSL.field(ALIAS_1, Integer.class)));
        assertEquals(Arrays.asList(fieldAlias1.asc(), fieldAlias2.desc()), sortFields);
    }

    @Test
    void fieldsCanBeMatchedIgnoringCase() {
        final JooqSort jooqSort = builder
            .enableJooqFieldExtraLookUp(false)
            .build();

        final Sort sort = Sort.by("myField", "field-2", "parent_Id", "createTime", "extra");
        final List<Field<?>> fields = Arrays.asList(
            DSL.field("MYFIELD"),
            DSL.field("FIELD-2"),
            DSL.field("pAREnt_Id"),
            DSL.field("CREATETIME"),
            DSL.field("EXtRA")
        );

        final List<? extends SortField<?>> sortFields = assertDoesNotThrow(() -> jooqSort.buildOrderBy(sort, fields));
        assertEquals(fields.stream().map(Field::asc).collect(Collectors.toList()), sortFields);
    }

    @Test
    void fieldsAreMatchedIgnoringCaseDisabled() {
        final JooqSort jooqSort = builder
            .ignoreJooqPropertyCase(false)
            .build();

        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("myField"), DSL.field("MY_FIELD")));
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("field2"), DSL.field("FIELD_2")));
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("parentId"), DSL.field("parent-Id")));
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("createTime"), DSL.field("CREATE-TIME")));
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("extra"), DSL.field("EXTRA")));
    }

    @Test
    void fieldsAreNotMatchedIgnoringCaseDisabledAndLookUp() {
        final JooqSort jooqSort = builder
            .ignoreJooqPropertyCase(false)
            .enableJooqFieldExtraLookUp(false)
            .build();

        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("myField"), DSL.field("MY_FIELD")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("field2"), DSL.field("FIELD_2")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("parentId"), DSL.field("parent-Id")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("createTime"), DSL.field("CREATE-TIME")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("extra"), DSL.field("EXTRA")));
    }

    @Test
    void fieldsCanBeMatchedWithExtraLookUp() {
        final JooqSort jooqSort = builder
            .ignoreJooqPropertyCase(false)
            .build();

        final Sort sort = Sort.by("myField", "field2", "parentId", "createTime", "extra");
        final List<Field<?>> fields = Arrays.asList(
            DSL.field("MY_FIELD"),
            DSL.field("FIELD_2"),
            DSL.field("parent-Id"),
            DSL.field("CREATE-TIME"),
            DSL.field("EXTRA")
        );

        final List<? extends SortField<?>> sortFields = assertDoesNotThrow(() -> jooqSort.buildOrderBy(sort, fields));
        assertEquals(fields.stream().map(Field::asc).collect(Collectors.toList()), sortFields);
    }

    @Test
    void fieldsAreNotMatchedWithExtraLookUp() {
        final JooqSort jooqSort = builder
            .enableJooqFieldExtraLookUp(false)
            .build();

        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("myField"), DSL.field("MY_FIELD")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("field2"), DSL.field("FIELD_2")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("parentId"), DSL.field("parent-Id")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("createTime"), DSL.field("CREATE-TIME")));
        assertDoesNotThrow(() -> jooqSort.buildOrderBy(Sort.by("extra"), DSL.field("EXTRA")));
    }

    @Test
    void fieldsAreNotMatchedWithExtraLookUpAndCase() {
        final JooqSort jooqSort = builder
            .ignoreJooqPropertyCase(false)
            .enableJooqFieldExtraLookUp(false)
            .build();

        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("myField"), DSL.field("MY_FIELD")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("field2"), DSL.field("FIELD_2")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("parentId"), DSL.field("parent-Id")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("createTime"), DSL.field("CREATE-TIME")));
        assertThrows(IllegalArgumentException.class, () -> jooqSort.buildOrderBy(Sort.by("extra"), DSL.field("EXTRA")));
    }

//    @Test
//    void buildOrderBy() {
//    }
}
