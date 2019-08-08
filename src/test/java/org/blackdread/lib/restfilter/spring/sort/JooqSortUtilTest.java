package org.blackdread.lib.restfilter.spring.sort;

import org.apache.commons.text.CaseUtils;
import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.blackdread.lib.restfilter.demo.jooq.tables.ChildEntity.CHILD_ENTITY;
import static org.blackdread.lib.restfilter.demo.jooq.tables.ParentEntity.PARENT_ENTITY;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/08/05.</p>
 *
 * @author Yoann CAPLAIN
 */
class JooqSortUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buildOrderByFromSelect() {
        final SelectSelectStep<Record4<Long, String, BigDecimal, Boolean>> select = DSL.select(CHILD_ENTITY.ID, CHILD_ENTITY.NAME, CHILD_ENTITY.TOTAL, CHILD_ENTITY.ACTIVE);
        // todo right now, the search for alias/property is case sensitive, might need to ignore case
        final List<? extends SortField<?>> sortFields = JooqSortUtil.buildOrderBy(Sort.by("ID", CHILD_ENTITY.NAME.getName(), CHILD_ENTITY.TOTAL.getQualifiedName().last(), CHILD_ENTITY.ACTIVE.getName()), select);
        assertEquals(Arrays.asList(
            CHILD_ENTITY.ID.asc(),
            CHILD_ENTITY.NAME.asc(),
            CHILD_ENTITY.TOTAL.asc(),
            CHILD_ENTITY.ACTIVE.asc()
        ), sortFields);

        final IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> JooqSortUtil.buildOrderBy(Sort.by("id", CHILD_ENTITY.NAME.getName(), CHILD_ENTITY.TOTAL.getQualifiedName().toString(), CHILD_ENTITY.ACTIVE.getName()), select));
        assertEquals("Property 'id' not found", ex1.getMessage());


        final IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> JooqSortUtil.buildOrderBy(Sort.by("ID", CHILD_ENTITY.getName(), CHILD_ENTITY.TOTAL.getQualifiedName().toString(), CHILD_ENTITY.ACTIVE.getName()), select));
        assertEquals("Property 'CHILD_ENTITY' not found", ex2.getMessage());


        final IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> JooqSortUtil.buildOrderBy(Sort.by("ID", CHILD_ENTITY.NAME.getName(), CHILD_ENTITY.TOTAL.getQualifiedName().toString(), CHILD_ENTITY.ACTIVE.getName()), select));
        assertEquals("Property '\"CHILD_ENTITY\".\"TOTAL\"' not found", ex3.getMessage());

    }

//    @Test
//    void buildOrderBy() {
//    }


    @Test
    void convertToSortField() {
        final Field<String> field = DSL.field("any", String.class);
        final SortField<String> sortField = JooqSortUtil.convertToSortField(field, Sort.Order.asc("any").nullsFirst().ignoreCase());
        assertEquals(DSL.upper(DSL.field("any", String.class)).asc().nullsFirst(), sortField);

        final SortField<String> sortField2 = JooqSortUtil.convertToSortField(field, Sort.Order.asc("any").nullsLast());
        assertEquals(DSL.field("any", String.class).asc().nullsLast(), sortField2);
    }

    @Test
    void tryConvertSortIgnoreCase() {
        final Field<Integer> field = DSL.field("any", String.class).cast(Integer.class);
        final Field<Integer> failConvert = JooqSortUtil.tryConvertSortIgnoreCase(field);
        assertSame(field, failConvert);

        final Field<String> field2 = DSL.field("any", Integer.class).cast(String.class);
        final Field<String> converted = JooqSortUtil.tryConvertSortIgnoreCase(field2);
        assertNotSame(field, converted);
        assertEquals(DSL.upper(DSL.field("any", Integer.class).cast(String.class)), converted);
    }

    // just to verify some logic
    @Test
    void testCamelCase() {
        assertEquals("id", CaseUtils.toCamelCase("ID", false));
        assertEquals("my_column", CaseUtils.toCamelCase("MY_COLUMN", false));
        assertEquals("my-column", CaseUtils.toCamelCase("MY-COLUMN", false));
        assertEquals("myColumn", CaseUtils.toCamelCase("MY_COLUMN", false, '_'));
        assertEquals("myColumn", CaseUtils.toCamelCase("MY-COLUMN", false, '-'));
        assertEquals("myColumnTestAt", CaseUtils.toCamelCase("MY_COLUMN-TEST_AT", false, '_', '-'));
    }

    @Test
    void testJooqFieldName() {
        assertEquals("ID", CHILD_ENTITY.ID.getName());
        assertEquals("ACTIVE", CHILD_ENTITY.ACTIVE.getName());
        assertEquals("PARENT_ID", CHILD_ENTITY.PARENT_ID.getName());
        assertEquals("childId", CHILD_ENTITY.ID.as("childId").getName());
        assertEquals("active", CHILD_ENTITY.ACTIVE.as("active").getName());
        assertEquals("parentId", PARENT_ENTITY.ID.as("parentId").getName());
    }

//    @Test
//    void applyNullHandling() {
//    }

//    @Test
//    void convertToSortField() {
//    }
}
