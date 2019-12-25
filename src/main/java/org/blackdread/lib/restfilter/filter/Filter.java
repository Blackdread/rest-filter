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
 *
 * Original taken from jhipster project, this class has been modified.
 */
package org.blackdread.lib.restfilter.filter;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for the various attribute filters. It can be added to a criteria class as a member, to support the
 * following query parameters:
 * <pre>
 *      fieldName.equals='something'
 *      fieldName.notEquals='something'
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in='something','other'
 *      fieldName.notIn='something','other'
 * </pre>
 * <br>
 * Criteria classes are not expected to declare field like:
 * <pre>
 *     class MyCriteria {
 *         private Filter&lt;XXX&gt; myFilter;
 *     }
 * </pre>
 * but actual subclasses like {@link org.blackdread.lib.restfilter.filter.LongFilter}, {@link org.blackdread.lib.restfilter.filter.InstantFilter}, etc
 */
public class Filter<FIELD_TYPE> implements Serializable {

    private static final long serialVersionUID = 1L;
    private FIELD_TYPE equals;
    private FIELD_TYPE notEquals;
    private Boolean specified;
    private List<FIELD_TYPE> in;
    private List<FIELD_TYPE> notIn;

    public Filter() {
    }

    public Filter(Filter<FIELD_TYPE> filter) {
        this.equals = filter.equals;
        this.notEquals = filter.notEquals;
        this.specified = filter.specified;
        this.in = filter.in == null ? null : new ArrayList<>(filter.in);
        this.notIn = filter.notIn == null ? null : new ArrayList<>(filter.notIn);
    }

    /**
     * @return new filter with all fields copied (deep copy), collections are copied (no just the reference)
     */
    public Filter<FIELD_TYPE> copy() {
        return new Filter<>(this);
    }

    /**
     * Just to compute it once lazily (no lock used)
     */
    private Class<FIELD_TYPE> genericClass;

    /**
     * The class in the generic definition part of this class.
     * <pre>
     *     class Filter&lt;XXX&gt; {}
     * </pre>
     * Where XXX is the class returned by this method.
     * <br>
     * <br>
     * <b>Important:</b>This method does not support to obtain generic type if filter instance was created with <b>'new Filter&lt;XXX&gt;()'</b> or <b>'new RangeFilter&lt;XXX&gt;()'</b>.
     * <br>
     * <b>Important:</b>This methods is subject to change at any time, this is for internal usage of library. It is not stable and may be removed at any time.
     * <br>
     * <br>
     * Note: Sub-classes can override this methods to return the correct/custom type if needed.
     * <br>
     * Method name does not start with 'get' so jackson/etc will try to get this value. Cannot use {@literal @}JsonIgnore.
     *
     * @return class of this generic filter
     * @throws IllegalStateException when filter was created via 'new Filter()' or 'new RangeFilter()'
     * @throws ClassCastException    when fail to cast raw type to generic type
     */
    @SuppressWarnings("unchecked")
    public Class<FIELD_TYPE> obtainGenericClass() throws IllegalStateException, ClassCastException {
        if (genericClass != null)
            return genericClass;
        Class<?> aClass = getClass();
        Class<?> nextClass = aClass.getSuperclass();
        boolean isNextRangeFilter = RangeFilter.class.equals(nextClass);
        boolean isNextFilter = Filter.class.equals(nextClass);
        while (nextClass != null && !Object.class.equals(nextClass) && isNextRangeFilter == isNextFilter) {
            aClass = nextClass;
            nextClass = aClass.getSuperclass();
            isNextRangeFilter = RangeFilter.class.equals(nextClass);
            isNextFilter = Filter.class.equals(nextClass);
        }

        final Type genericSuperclass1 = aClass.getGenericSuperclass();
        final ParameterizedType genericSuperclass;
        try {
            genericSuperclass = (ParameterizedType) genericSuperclass1; // ClassCastException if user created a filter directly with 'new Filter<XXX>()'
        } catch (ClassCastException e) {
            throw new IllegalStateException("Method 'obtainGenericClass' does not support generic retrieval for filters created with 'new Filter<XXX>()'", e);
        }
        final Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        final Type actualTypeArgument = actualTypeArguments[0];
        try {
            return (Class<FIELD_TYPE>) actualTypeArgument; // ClassCastException if user defined a class with multiple generics like 'class MyCustomFilter extends Filter<List<String>>' but should never do that!
        } catch (ClassCastException e) {
            final ParameterizedType actualTypeArgument1 = (ParameterizedType) actualTypeArgument;
            return genericClass = (Class<FIELD_TYPE>) actualTypeArgument1.getRawType();
        }
    }

    public FIELD_TYPE getEquals() {
        return equals;
    }

    public Filter<FIELD_TYPE> setEquals(FIELD_TYPE equals) {
        this.equals = equals;
        return this;
    }

    public FIELD_TYPE getNotEquals() {
        return notEquals;
    }

    public Filter<FIELD_TYPE> setNotEquals(FIELD_TYPE notEquals) {
        this.notEquals = notEquals;
        return this;
    }

    public Boolean getSpecified() {
        return specified;
    }

    public Filter<FIELD_TYPE> setSpecified(Boolean specified) {
        this.specified = specified;
        return this;
    }

    public List<FIELD_TYPE> getIn() {
        return in;
    }

    /**
     * Best is to provide a list that will be referenced only by this filter and that is not immutable
     *
     * @param in list
     * @return itself
     */
    public Filter<FIELD_TYPE> setIn(List<FIELD_TYPE> in) {
        this.in = in;
        return this;
    }

    public List<FIELD_TYPE> getNotIn() {
        return notIn;
    }

    /**
     * Best is to provide a list that will be referenced only by this filter and that is not immutable
     *
     * @param notIn list
     * @return itself
     */
    public Filter<FIELD_TYPE> setNotIn(List<FIELD_TYPE> notIn) {
        this.notIn = notIn;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Filter<?> filter = (Filter<?>) o;
        return Objects.equals(equals, filter.equals) &&
            Objects.equals(notEquals, filter.notEquals) &&
            Objects.equals(specified, filter.specified) &&
            Objects.equals(in, filter.in) &&
            Objects.equals(notIn, filter.notIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(equals, notEquals, specified, in, notIn);
    }

    @Override
    public String toString() {
        return (getFilterName() + " ["
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getIn() != null ? "in=" + getIn() + ", " : "")
            + (getNotIn() != null ? "notIn=" + getNotIn() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() : "")
            + "]").replace(", ]", "]");
    }

    protected String getFilterName() {
        return this.getClass().getSimpleName();
    }
}
