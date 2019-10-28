package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/10/24.</p>
 *
 * @author Yoann CAPLAIN
 */
class FilterQueryParamTest {

    private CriteriaQueryParamBuilder builder;

    private CriteriaQueryParam criteriaQueryParam;

    private LongFilter longFilter;

    static class MyCriteria implements Criteria {

        LongFilter id2;

        @Override
        public Criteria copy() {
            throw new IllegalStateException("won't impl");
        }
    }

    @BeforeEach
    void setUp() {
        builder = new CriteriaQueryParamBuilder();

        criteriaQueryParam = builder.build();

        longFilter = new LongFilter();
        longFilter.setEquals(1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void equalsIsOverridden() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);
        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);
        final FilterQueryParam filterQueryParam3 = criteriaQueryParam.getFilterQueryParams("aa", longFilter).get(0);
        final FilterQueryParam filterQueryParam4 = criteriaQueryParam.getFilterQueryParams("a", longFilter.setEquals(2L)).get(0);

        assertEquals(filterQueryParam, filterQueryParam2);
        assertNotSame(filterQueryParam, filterQueryParam2);
        assertNotEquals(filterQueryParam, filterQueryParam3);
        assertNotEquals(filterQueryParam, filterQueryParam4);
        assertNotEquals(filterQueryParam3, filterQueryParam4);
    }

    @Test
    void hashcodeIsOverridden() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);
        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);
        final FilterQueryParam filterQueryParam3 = criteriaQueryParam.getFilterQueryParams("aa", longFilter).get(0);
        final FilterQueryParam filterQueryParam4 = criteriaQueryParam.getFilterQueryParams("a", longFilter.setEquals(2L)).get(0);

        assertEquals(filterQueryParam.hashCode(), filterQueryParam2.hashCode());
        assertNotSame(filterQueryParam.hashCode(), filterQueryParam2.hashCode());
        assertNotEquals(filterQueryParam.hashCode(), filterQueryParam3.hashCode());
        assertNotEquals(filterQueryParam.hashCode(), filterQueryParam4.hashCode());
        assertNotEquals(filterQueryParam3.hashCode(), filterQueryParam4.hashCode());
    }

    @Test
    void returnNothingIfFilterEmpty() {
        final List<FilterQueryParam> filterQueryParams = criteriaQueryParam.getFilterQueryParams("a", new LongFilter());

        assertEquals(new ArrayList<>(), filterQueryParams);
    }

    @Test
    void getCriteriaName() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertEquals("a", filterQueryParam.getCriteriaName());
    }

    @Test
    void getFilterPropertyName() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertEquals("equals", filterQueryParam.getFilterPropertyName());
    }

    @Test
    void getParamName() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertEquals("a.equals", filterQueryParam.getParamName());
    }

    @Test
    void hasParamValue() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertTrue(filterQueryParam.hasParamValue());
    }

    @Test
    void hasMultipleParamValue() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertFalse(filterQueryParam.hasMultipleParamValue());

        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setIn(List2.of(1L, 2L))).get(0);

        assertTrue(filterQueryParam2.hasMultipleParamValue());
    }

    @Test
    void isOnlyOneParamValue() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertTrue(filterQueryParam.isOnlyOneParamValue());

        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setIn(List2.of(1L, 2L))).get(0);

        assertFalse(filterQueryParam2.isOnlyOneParamValue());
    }

    @Test
    void getParamValue() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertEquals("1", filterQueryParam.getParamValue());

        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setIn(List2.of(1L, 2L))).get(0);

        assertEquals("1,2", filterQueryParam2.getParamValue());
    }

    @Test
    void getParamValues() {
        final FilterQueryParam filterQueryParam = criteriaQueryParam.getFilterQueryParams("a", longFilter).get(0);

        assertEquals(List2.of("1"), filterQueryParam.getParamValues());

        final FilterQueryParam filterQueryParam2 = criteriaQueryParam.getFilterQueryParams("a", new LongFilter().setIn(List2.of(1L, 2L))).get(0);

        assertEquals(List2.of("1", "2"), filterQueryParam2.getParamValues());
    }
}
