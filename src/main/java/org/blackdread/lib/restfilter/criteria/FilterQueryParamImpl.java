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
 */
package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

import static org.blackdread.lib.restfilter.criteria.CriteriaQueryParam.*;

/**
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
final class FilterQueryParamImpl implements FilterQueryParam {

    private static final Logger log = LoggerFactory.getLogger(FilterQueryParamImpl.class);

    private final String criteriaName;
    private final String filterPropertyName;
    private final String paramValue;
    private final List<String> paramValues;

    static FilterQueryParam ofEquals(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, EQUALS_FILTER, paramValue);
    }

    static FilterQueryParam ofNotEquals(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, NOT_EQUALS_FILTER, paramValue);
    }

    static FilterQueryParam ofIn(final String criteriaName, final List<String> paramValues) {
        return ofMultipleValues(criteriaName, IN_FILTER, paramValues);
    }

    static FilterQueryParam ofNotIn(final String criteriaName, final List<String> paramValues) {
        return ofMultipleValues(criteriaName, NOT_IN_FILTER, paramValues);
    }

    static FilterQueryParam ofSpecified(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, SPECIFIED_FILTER, paramValue);
    }

    static FilterQueryParam ofGreaterThan(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, GREATER_THAN_FILTER, paramValue);
    }

    static FilterQueryParam ofGreaterThanOrEqual(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, GREATER_THAN_OR_EQUAL_FILTER, paramValue);
    }

    static FilterQueryParam ofLessThan(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, LESS_THAN_FILTER, paramValue);
    }

    static FilterQueryParam ofLessThanOrEqual(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, LESS_OR_EQUAL_THAN_FILTER, paramValue);
    }

    static FilterQueryParam ofContains(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, CONTAINS_FILTER, paramValue);
    }

    static FilterQueryParam ofNotContains(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, NOT_CONTAINS_FILTER, paramValue);
    }

    static FilterQueryParam ofIgnoreCase(final String criteriaName, final String paramValue) {
        return ofSingleValue(criteriaName, IGNORE_CASE_FILTER, paramValue);
    }

    private static FilterQueryParam ofSingleValue(final String criteriaName, final String filterPropertyName, final String paramValue) {
        return new FilterQueryParamImpl(criteriaName, filterPropertyName, paramValue, List.of(paramValue));
    }

    private static FilterQueryParam ofMultipleValues(final String criteriaName, final String filterPropertyName, final List<String> paramValues) {
        return new FilterQueryParamImpl(criteriaName, filterPropertyName, String.join(",", paramValues), List.copyOf(paramValues));
    }

    private FilterQueryParamImpl(final String criteriaName, final String filterPropertyName, final String paramValue, final List<String> paramValues) {
        this.criteriaName = Objects.requireNonNull(criteriaName);
        this.filterPropertyName = Objects.requireNonNull(filterPropertyName);
        this.paramValue = Objects.requireNonNull(paramValue);
        this.paramValues = Objects.requireNonNull(paramValues);
    }

    @Override
    public String getCriteriaName() {
        return criteriaName;
    }

    @Override
    public String getFilterPropertyName() {
        return filterPropertyName;
    }

    @Override
    public boolean hasParamValue() {
        return paramValues.size() >= 1;
    }

    @Override
    public boolean hasMultipleParamValue() {
        return paramValues.size() > 1;
    }

    @Override
    public String getParamValue() {
// it is allowed to query single value already joined with ','
//        if (paramValues.size() > 1) {
//            throw new IllegalStateException(String.format("Multiple values are available for field '%s', filter '%s', with values [\"%s\"]", criteriaName, filterPropertyName, String.join("\", \"", paramValues)));
//        }
        return paramValue;
    }

    @Override
    public List<String> getParamValues() {
        return paramValues;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FilterQueryParamImpl that = (FilterQueryParamImpl) o;
        return criteriaName.equals(that.criteriaName) &&
            filterPropertyName.equals(that.filterPropertyName) &&
            paramValue.equals(that.paramValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteriaName, filterPropertyName, paramValue);
    }

    @Override
    public String toString() {
        return "FilterQueryParamImpl{" +
            "criteriaName='" + criteriaName + '\'' +
            ", filterPropertyName='" + filterPropertyName + '\'' +
            ", paramValue='" + paramValue + '\'' +
            ", paramValues=" + paramValues +
            '}';
    }
}
