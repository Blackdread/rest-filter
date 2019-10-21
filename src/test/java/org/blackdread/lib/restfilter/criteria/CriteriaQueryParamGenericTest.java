package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CriteriaQueryParamGenericTest {

    @Test
    void directInstanceOfFilterFails() {
        Filter<String> filter = new Filter<>();

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, filter::obtainGenericClass);
        Assertions.assertEquals("Method 'obtainGenericClass' does not support generic retriveal for filters created with 'new Filter<XXX>()'", exception.getMessage());
    }

    @Test
    void withCustomClassWrapperFails() {
        Filter<Custom> filter = new Filter<>();

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, filter::obtainGenericClass);
        Assertions.assertEquals("Method 'obtainGenericClass' does not support generic retriveal for filters created with 'new Filter<XXX>()'", exception.getMessage());
    }

    @Test
    void withCustomFilterWrapperOk() {
        CustomFilter1 filter = new CustomFilter1();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(Custom.class, aClass);
    }

    @Test
    void withSubclassGenericFilterFails() {
        RangeFilter<String> filter = new RangeFilter<>();

        Assertions.assertThrows(ClassCastException.class, filter::obtainGenericClass);
    }

    @Test
    void canGetFirstGenericInMultipleGenericFilter() {
        CustomFilter2 filter = new CustomFilter2();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(List.class, aClass);
    }

    static class Custom extends ArrayList<String> {

    }

    static class CustomFilter1 extends Filter<Custom> {
    }

    static class CustomFilter2 extends Filter<List<String>> {
        // Cannot see any reason to have filters with multiple generics but it is tested at least
    }

}

