package org.blackdread.lib.restfilter.spring.sort;

import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * <p>Created on 2019/08/03.</p>
 *
 * @author Yoann CAPLAIN
 */
class JooqSortBuilderTest {

    private JooqSortBuilder builder;

    private Field<Long> fieldLong;
    private Field<String> fieldString;

    private Field<Integer> fieldInt;
    private Field<Integer> fieldInt2;

    private static final String ALIAS_1 = "any";
    private static final String ALIAS_2 = "any2";
    private static final String ALIAS_3 = "any3";

    private static final Sort UNSORTED = Sort.unsorted();
    private static final Sort SORT_1_2 = Sort.by(ALIAS_1, ALIAS_2);

    private Collection<SortField<?>> sort_1_2_fields;

    @BeforeEach
    void setUp() {
        builder = JooqSortBuilder.newBuilder();

        fieldLong = DSL.field("my_long", Long.class);
        fieldString = DSL.field("my_string", String.class);

        fieldInt = DSL.field(ALIAS_1, Integer.class);
        fieldInt = DSL.field(ALIAS_2, Integer.class);

        sort_1_2_fields = Arrays.asList(fieldLong.asc(), fieldString.desc());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void newBuilderIsNewInstance() {
        final JooqSortBuilder builder1 = JooqSortBuilder.newBuilder();
        final JooqSortBuilder builder2 = JooqSortBuilder.newBuilder();
        Assertions.assertNotSame(builder1, builder2);
    }

    @Test
    void newBuilderHasDefaults() {
    }

    @Test
    void allMethodsReturnsNewInstance() {
        final JooqSortBuilder b1 = builder.addAlias(ALIAS_1, fieldLong);
        final JooqSortBuilder b2 = builder.addAlias(ALIAS_2, fieldLong, fieldString);
        final JooqSortBuilder b3 = builder.addAlias(ALIAS_2, Collections.singleton(fieldLong));
        final JooqSortBuilder b4 = builder.addAliasInline(ALIAS_1, 1);
        final JooqSortBuilder b5 = builder.addAliasInline(ALIAS_2, 1, 2);
        final JooqSortBuilder b6 = builder.addAliasInline(ALIAS_2, Collections.singleton(1));
        final JooqSortBuilder b7 = builder.withDefaultOrdering(Sort.by(ALIAS_1, ALIAS_2));
        final JooqSortBuilder b8 = builder.withDefaultOrdering(Arrays.asList(fieldLong.asc(), fieldString.desc()));
        final JooqSortBuilder b9 = builder.throwOnSortPropertyNotFound(true);
        final JooqSortBuilder b10 = builder.throwOnAliasNotFound(true);
        final ArrayList<JooqSortBuilder> list = new ArrayList<>();
        Collections.addAll(list, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size() - 1; j++) {
                Assertions.assertNotSame(list.get(i), list.get(j), String.format("i=%d and j=%d", i, j));
            }
        }
    }

    @Test
    void buildIsNewInstance() {
        final JooqSort jooqSort1 = builder.build();
        final JooqSort jooqSort2 = builder.build();
        Assertions.assertNotSame(jooqSort1, jooqSort2);
    }

    @Test
    void allCanBeDefinedResetManyTimes() {
        builder
            .addAlias(ALIAS_1, fieldLong)
            .addAlias(ALIAS_2, fieldLong, fieldString)
            .addAlias(ALIAS_3, Collections.singleton(fieldLong))
            .addAliasInline("adsd", 1)
            .addAliasInline("sdd", 1, 2)
            .addAliasInline("sadad", Collections.singleton(1))
            .withDefaultOrdering(Sort.by(ALIAS_1, ALIAS_2))
            .withDefaultOrdering(Sort.by(ALIAS_1))
            .withDefaultOrdering((Sort) null)
            .withDefaultOrdering(Arrays.asList(fieldLong.asc(), fieldString.desc()))
            .withDefaultOrdering(Collections.singletonList(fieldLong.asc()))
            .withDefaultOrdering((Collection<? extends SortField<?>>) null)
            .withDefaultOrdering(Sort.by(ALIAS_1, ALIAS_2))
            .throwOnSortPropertyNotFound(true)
            .throwOnSortPropertyNotFound(false)
            .throwOnAliasNotFound(true)
            .throwOnAliasNotFound(false);
    }

    @Test
    void addDuplicateAliasThrows() {
        final JooqSortBuilder aa = builder.addAlias("aa", fieldLong);

        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", fieldLong));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", fieldLong, fieldString));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", Collections.singleton(fieldInt)));

        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", 1));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", 1, 2));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", Collections.singleton(3)));
    }

    @Test
    void addDuplicateAliasInlineThrows() {
        final JooqSortBuilder aa = builder.addAliasInline("aa", 5);

        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", fieldLong));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", fieldLong, fieldString));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAlias("aa", Collections.singleton(fieldInt)));

        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", 1));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", 1, 2));
        Assertions.assertThrows(IllegalStateException.class, () -> aa.addAliasInline("aa", Collections.singleton(3)));
    }

    @Test
    void addAlias() {
    }

    @Test
    void addAliasInline() {
    }

    @Test
    void enableCaseInsensitiveSort() {
    }

    @Test
    void enableNullHandling() {
    }

    @Test
    void withDefaultOrderingThrowsIfEitherAlreadyDefined() {
        final JooqSortBuilder withSort = builder.withDefaultOrdering(SORT_1_2);
        Assertions.assertThrows(IllegalStateException.class, () -> withSort.withDefaultOrdering(sort_1_2_fields));

        final JooqSortBuilder withSortFields = builder.withDefaultOrdering(sort_1_2_fields);
        Assertions.assertThrows(IllegalStateException.class, () -> withSortFields.withDefaultOrdering(SORT_1_2));
    }

    @Test
    void withDefaultOrderingSort() {
    }

    @Test
    void WithDefaultOrderingFields() {
    }

    @Test
    void throwOnSortPropertyNotFound() {
    }

    @Test
    void throwOnAliasNotFound() {
    }
}
