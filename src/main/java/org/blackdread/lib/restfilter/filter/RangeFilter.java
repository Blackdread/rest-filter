/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
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

import java.util.List;
import java.util.Objects;

/**
 * Filter class for Comparable types, where less than / greater than / etc relations could be interpreted. It can be
 * added to a criteria class as a member, to support the following query parameters:
 * <pre>
 *      fieldName.equals=42
 *      fieldName.notEquals=42
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=43,42
 *      fieldName.notIn=43,42
 *      fieldName.greaterThan=41
 *      fieldName.lessThan=44
 *      fieldName.greaterThanOrEqual=42
 *      fieldName.lessThanOrEqual=44
 * </pre>
 * Due to problems with the type conversions, the descendant classes should be used, where the generic type parameter
 * is materialized.
 * <br>
 * <br>
 * Criteria classes are not expected to declare field like:
 * <pre>
 *     class MyCriteria {
 *         private Filter&lt;XXX&gt; myFilter;
 *         private RangeFilter&lt;XXX&gt; myFilter2;
 *     }
 * </pre>
 * but actual subclasses like {@link org.blackdread.lib.restfilter.filter.LongFilter}, {@link org.blackdread.lib.restfilter.filter.InstantFilter}, etc
 *
 * @param <FIELD_TYPE> the type of filter.
 * @see IntegerFilter
 * @see DoubleFilter
 * @see FloatFilter
 * @see LongFilter
 * @see LocalDateFilter
 * @see InstantFilter
 */
public class RangeFilter<FIELD_TYPE extends Comparable<? super FIELD_TYPE>> extends Filter<FIELD_TYPE> {

    private static final long serialVersionUID = 1L;

    private FIELD_TYPE greaterThan;
    private FIELD_TYPE lessThan;
    private FIELD_TYPE greaterThanOrEqual;
    private FIELD_TYPE lessThanOrEqual;

    public RangeFilter() {
    }

    public RangeFilter(final RangeFilter<FIELD_TYPE> filter) {
        super(filter);
        this.greaterThan = filter.greaterThan;
        this.lessThan = filter.lessThan;
        this.greaterThanOrEqual = filter.greaterThanOrEqual;
        this.lessThanOrEqual = filter.lessThanOrEqual;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> copy() {
        return new RangeFilter<>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> setEquals(FIELD_TYPE equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> setNotEquals(FIELD_TYPE notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> setIn(List<FIELD_TYPE> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RangeFilter<FIELD_TYPE> setNotIn(List<FIELD_TYPE> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    public FIELD_TYPE getGreaterThan() {
        return greaterThan;
    }

    public RangeFilter<FIELD_TYPE> setGreaterThan(FIELD_TYPE greaterThan) {
        this.greaterThan = greaterThan;
        return this;
    }

    public FIELD_TYPE getGreaterThanOrEqual() {
        return greaterThanOrEqual;
    }

    public RangeFilter<FIELD_TYPE> setGreaterThanOrEqual(FIELD_TYPE greaterThanOrEqual) {
        this.greaterThanOrEqual = greaterThanOrEqual;
        return this;
    }

    public FIELD_TYPE getLessThan() {
        return lessThan;
    }

    public RangeFilter<FIELD_TYPE> setLessThan(FIELD_TYPE lessThan) {
        this.lessThan = lessThan;
        return this;
    }

    public FIELD_TYPE getLessThanOrEqual() {
        return lessThanOrEqual;
    }

    public RangeFilter<FIELD_TYPE> setLessThanOrEqual(FIELD_TYPE lessThanOrEqual) {
        this.lessThanOrEqual = lessThanOrEqual;
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
        if (!super.equals(o)) {
            return false;
        }
        final RangeFilter<?> that = (RangeFilter<?>) o;
        return Objects.equals(greaterThan, that.greaterThan) &&
            Objects.equals(lessThan, that.lessThan) &&
            Objects.equals(greaterThanOrEqual, that.greaterThanOrEqual) &&
            Objects.equals(lessThanOrEqual, that.lessThanOrEqual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), greaterThan, lessThan, greaterThanOrEqual, lessThanOrEqual);
    }

    @Override
    public String toString() {
        return (getFilterName() + " ["
            + (getGreaterThan() != null ? "greaterThan=" + getGreaterThan() + ", " : "")
            + (getGreaterThanOrEqual() != null ? "greaterThanOrEqual=" + getGreaterThanOrEqual() + ", " : "")
            + (getLessThan() != null ? "lessThan=" + getLessThan() + ", " : "")
            + (getLessThanOrEqual() != null ? "lessThanOrEqual=" + getLessThanOrEqual() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
            + (getIn() != null ? "in=" + getIn() + ", " : "")
            + (getNotIn() != null ? "notIn=" + getNotIn() : "")
            + "]").replace(", ]", "]");
    }

}
