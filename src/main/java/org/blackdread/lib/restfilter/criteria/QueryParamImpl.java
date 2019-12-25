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
 */
package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * <p>Created on 2019/10/24.</p>
 *
 * @author Yoann CAPLAIN
 */
final class QueryParamImpl implements QueryParam {

    private static final Logger log = LoggerFactory.getLogger(QueryParamImpl.class);

    private final String paramName;
    private final String paramValue;
    private final List<String> paramValues;

    static QueryParam ofNameOnly(final String paramName) {
        return new QueryParamImpl(paramName);
    }

    static QueryParam ofSingleValue(final String paramName, final String paramValue) {
        return new QueryParamImpl(paramName, paramValue);
    }

    static QueryParam ofMultipleValues(final String paramName, final List<String> paramValues) {
        return new QueryParamImpl(paramName, paramValues);
    }

    private QueryParamImpl(final String paramName) {
        this.paramName = Objects.requireNonNull(paramName);
        this.paramValue = null;
        this.paramValues = Collections.emptyList();
    }

    private QueryParamImpl(final String paramName, final String paramValue) {
        this.paramName = Objects.requireNonNull(paramName);
        this.paramValue = Objects.requireNonNull(paramValue);
        this.paramValues = Collections.singletonList(paramValue);
    }

    private QueryParamImpl(final String paramName, final List<String> paramValues) {
        this.paramName = Objects.requireNonNull(paramName);
        this.paramValues = Collections.unmodifiableList(new ArrayList<>(paramValues));
        this.paramValue = String.join(",", this.paramValues);
    }


    @Override
    public String getCriteriaName() {
        return paramName;
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
        final QueryParamImpl that = (QueryParamImpl) o;
        return paramName.equals(that.paramName) &&
            paramValue.equals(that.paramValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paramName, paramValue);
    }

    @Override
    public String toString() {
        return "FilterQueryParamImpl{" +
            "paramName='" + paramName + '\'' +
            ", paramValue='" + paramValue + '\'' +
            ", paramValues=" + paramValues +
            '}';
    }
}
