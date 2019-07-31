package org.blackdread.lib.restfilter.spring.filter;

import org.blackdread.lib.restfilter.demo.jooq.tables.ChildEntity;
import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * <p>Created on 2019/07/31.</p>
 *
 * @author Yoann CAPLAIN
 */
class QueryServiceTest {

    private LongFilter longFilter;

    private StringFilter stringFilter;

    private Field<Long> longField;

    private Field<String> stringField;

    private QueryService<ChildEntity> queryService;

    @BeforeEach
    void setUp() {
        longFilter = new LongFilter();
        longFilter.setEquals(1L);
        longFilter.setIn(Arrays.asList(1L, 2L));
        longFilter.setSpecified(true);
        longFilter.setGreaterThan(1L);
        longFilter.setGreaterThanOrEqual(1L);
        longFilter.setLessThan(1L);
        longFilter.setLessThanOrEqual(1L);

        stringFilter = new StringFilter();
        stringFilter.setEquals("any");
        stringFilter.setIn(Arrays.asList("any", "any2"));
        stringFilter.setSpecified(true);
        stringFilter.setContains("any");

        longField = DSL.field("myLong", Long.class);
        stringField = DSL.field("myString", String.class);

        queryService = new QueryServiceImpl<>();
    }

    @Test
    void buildSpecificationNone() {
        final Specification<ChildEntity> specification = queryService.buildSpecification((Filter<Long>) new LongFilter(), root -> root.get(""));
        assertNull(specification);
    }

    @Test
    void buildSpecification() {
        queryService.buildSpecification((Filter<Long>) longFilter, root -> root.get(""));
    }

    @Test
    void buildSpecificationIn() {
        longFilter.setEquals(null);
        queryService.buildSpecification((Filter<Long>) longFilter, root -> root.get(""));
    }

    @Test
    void buildSpecificationSpecified() {
        longFilter.setEquals(null);
        longFilter.setIn(null);
        queryService.buildSpecification((Filter<Long>) longFilter, root -> root.get(""));
    }

    @Test
    void testBuildSpecification() {
    }

    @Test
    void buildStringSpecification() {
    }

    @Test
    void testBuildSpecification1() {
    }

    @Test
    void buildRangeSpecification() {
    }

    @Test
    void testBuildSpecification2() {
    }

    @Test
    void buildReferringEntitySpecification() {
    }

    @Test
    void testBuildReferringEntitySpecification() {
    }

    @Test
    void testBuildReferringEntitySpecification1() {
    }

    @Test
    void testBuildReferringEntitySpecification2() {
    }

    @Test
    void testBuildReferringEntitySpecification3() {
    }

    @Test
    void equalsSpecification() {
    }

    @Test
    void likeUpperSpecification() {
    }

    @Test
    void byFieldSpecified() {
    }

    @Test
    void byFieldEmptiness() {
    }

    @Test
    void valueIn() {
    }

    @Test
    void greaterThanOrEqualTo() {
    }

    @Test
    void greaterThan() {
    }

    @Test
    void lessThanOrEqualTo() {
    }

    @Test
    void lessThan() {
    }

    @Test
    void wrapLikeQuery() {
    }
}
