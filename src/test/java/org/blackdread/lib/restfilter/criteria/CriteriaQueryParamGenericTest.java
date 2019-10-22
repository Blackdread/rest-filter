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
        Assertions.assertEquals("Method 'obtainGenericClass' does not support generic retrieval for filters created with 'new Filter<XXX>()'", exception.getMessage());
    }

    @Test
    void withCustomClassWrapperFails() {
        Filter<Custom> filter = new Filter<>();

        IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, filter::obtainGenericClass);
        Assertions.assertEquals("Method 'obtainGenericClass' does not support generic retrieval for filters created with 'new Filter<XXX>()'", exception.getMessage());
    }

    @Test
    void withCustomFilterWrapperOk() {
        CustomFilter1 filter = new CustomFilter1();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(Custom.class, aClass);
    }

    @Test
    void withCustomFilterEnumWrapperOk() {
        CustomFilterEnum filter = new CustomFilterEnum();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(CustomEnum.class, aClass);
        Assertions.assertTrue(aClass.isEnum());
        Assertions.assertFalse(aClass.isInterface());
    }

    @Test
    void withCustomFilterEnumInterfaceWrapperOk() {
        CustomFilterEnumInterface filter = new CustomFilterEnumInterface();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(Any.class, aClass);
        Assertions.assertFalse(aClass.isEnum());
        Assertions.assertTrue(aClass.isInterface());
    }

    @Test
    void withSubclassGenericFilterFails() {
        RangeFilter<String> filter = new RangeFilter<>();

        Assertions.assertThrows(ClassCastException.class, filter::obtainGenericClass);
    }

    @Test
    void canGetFirstGenericInMultipleGenericFilter() {
        CustomFilterWithManyGeneric filter = new CustomFilterWithManyGeneric();

        Class<?> aClass = Assertions.assertDoesNotThrow(filter::obtainGenericClass);
        Assertions.assertEquals(String.class, aClass);
    }

    static class Custom extends ArrayList<String> {
    }

    static class CustomFilterEnumInterface extends Filter<Any> {
    }

    static class CustomFilterEnum extends Filter<CustomEnum> {
    }

    static class CustomFilter1 extends Filter<Custom> {
    }

    static class CustomFilter2 extends Filter<List<String>> {
        // Cannot see any reason to have filters with multiple generics but it is tested at least
    }

    static class CustomFilterWithManyGeneric extends Filter<String> implements Any, Any1<Long>, Any2<Integer> {
        @Override
        public Long getT() {
            return null;
        }

        @Override
        public Integer getT2() {
            return null;
        }
    }

    enum CustomEnum implements Any {
        CUSTOM_ENUM_1,
        CUSTOM_ENUM_2
    }

    interface Any {

    }

    interface Any1<T> {
        T getT();
    }

    interface Any2<T2> {
        T2 getT2();
    }

}

