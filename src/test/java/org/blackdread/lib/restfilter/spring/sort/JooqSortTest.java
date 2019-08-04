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

    private static final Field<Integer> fieldInt = DSL.field(ALIAS_1, Integer.class);
    private static final Field<Integer> fieldInt2 = DSL.field(ALIAS_2, Integer.class);

    private static final Sort UNSORTED = Sort.unsorted();
    private static final Sort SORT_1_2 = Sort.by(Sort.Order.asc(ALIAS_1), Sort.Order.desc(ALIAS_2));
    private static final Sort SORT_1_2_3 = Sort.by(Sort.Order.asc(ALIAS_1), Sort.Order.desc(ALIAS_2), Sort.Order.asc(ALIAS_3));
    private static final Sort SORT_IGNORE_CASE = Sort.by(Sort.Order.asc(ALIAS_1).nullsLast(), Sort.Order.desc(ALIAS_2).nullsFirst(), Sort.Order.asc(ALIAS_3));
    private static final Sort SORT_NULL_HANDLE = Sort.by(Sort.Order.asc(ALIAS_1).ignoreCase(), Sort.Order.desc(ALIAS_2), Sort.Order.asc(ALIAS_3).ignoreCase());
    private static final Sort SORT_ALL = Sort.by(Sort.Order.asc(ALIAS_1).nullsLast().ignoreCase(), Sort.Order.desc(ALIAS_2).nullsFirst(), Sort.Order.asc(ALIAS_3).ignoreCase());

    private static final Collection<SortField<?>> SORT_1_2_FIELDS = Arrays.asList(fieldLong.asc(), fieldString.desc());
    private static final Collection<SortField<?>> SORT_1_2_FIELDS_ALIAS = Arrays.asList(fieldLong.asc().nullsFirst(), fieldString.desc(), fieldInt.asc(), fieldInt2.desc().nullsLast());

    @BeforeEach
    void setUp() {
        builder = JooqSortBuilder.newBuilder();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buildOrderBy() {
    }

    @Test
    void testBuildOrderBy() {
    }

    @Test
    void testBuildOrderBy1() {
    }
}