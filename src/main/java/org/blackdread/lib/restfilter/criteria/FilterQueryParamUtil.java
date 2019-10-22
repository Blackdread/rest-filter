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

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.blackdread.lib.restfilter.criteria.FilterQueryParamImpl.*;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class FilterQueryParamUtil {

    private static final Logger log = LoggerFactory.getLogger(FilterQueryParamUtil.class);

    private FilterQueryParamUtil() {
    }

//    public static Object buildQueryParams(Criteria criteria) {
//        return null;
//    }

//    public static Object buildQueryParams(Object criteria) {
//        return null;
//    }

    public static <T> List<FilterQueryParam> buildQueryParams(final String fieldName, final Filter<T> filter, final Function<T, String> formatter) {
        return buildQueryParams(fieldName, filter, formatter, Object::toString);
    }

    public static <T> List<FilterQueryParam> buildQueryParams(final String fieldName, final Filter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        return applyFilter(fieldName, filter, formatter, booleanFormatter);
    }

    public static <T extends Comparable<? super T>> List<FilterQueryParam> buildQueryParams(final String fieldName, final RangeFilter<T> filter, final Function<T, String> formatter) {
        return buildQueryParams(fieldName, filter, formatter, Object::toString);
    }

    public static <T extends Comparable<? super T>> List<FilterQueryParam> buildQueryParams(final String fieldName, final RangeFilter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        final List<FilterQueryParam> filterQueryParams = applyFilter(fieldName, filter, formatter, booleanFormatter);
        if (filter.getGreaterThan() != null) {
            filterQueryParams.add(ofGreaterThan(fieldName, formatter.apply(filter.getGreaterThan())));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            filterQueryParams.add(ofGreaterThanOrEqual(fieldName, formatter.apply(filter.getGreaterThanOrEqual())));
        }
        if (filter.getLessThan() != null) {
            filterQueryParams.add(ofLessThan(fieldName, formatter.apply(filter.getLessThan())));
        }
        if (filter.getLessThanOrEqual() != null) {
            filterQueryParams.add(ofLessThanOrEqual(fieldName, formatter.apply(filter.getLessThanOrEqual())));
        }
        return filterQueryParams;
    }

    public static List<FilterQueryParam> buildQueryParams(final String fieldName, final StringFilter filter) {
        return buildQueryParams(fieldName, filter, String::toString);
    }

    public static List<FilterQueryParam> buildQueryParams(final String fieldName, final StringFilter filter, final Function<String, String> formatter) {
        return buildQueryParams(fieldName, filter, formatter, Object::toString);
    }

    public static List<FilterQueryParam> buildQueryParams(final String fieldName, final StringFilter filter, final Function<String, String> formatter, final Function<Boolean, String> booleanFormatter) {
        final List<FilterQueryParam> filterQueryParams = applyFilter(fieldName, filter, formatter, booleanFormatter);
        if (filter.getContains() != null) {
            filterQueryParams.add(ofContains(fieldName, formatter.apply(filter.getContains())));
        }
        if (filter.getNotContains() != null) {
            filterQueryParams.add(ofNotContains(fieldName, formatter.apply(filter.getNotContains())));
        }
        if (!filter.isIgnoreCase()) {
            filterQueryParams.add(ofIgnoreCase(fieldName, booleanFormatter.apply(false)));
        }
        return filterQueryParams;
    }

    private static <T> List<FilterQueryParam> applyFilter(final String fieldName, final Filter<T> filter, final Function<T, String> formatter) {
        return applyFilter(fieldName, filter, formatter, Object::toString);
    }

    private static <T> List<FilterQueryParam> applyFilter(final String fieldName, final Filter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        final List<FilterQueryParam> filterQueryParams = new ArrayList<>();
        if (filter.getEquals() != null) {
            filterQueryParams.add(ofEquals(fieldName, formatter.apply(filter.getEquals())));
        }
        if (filter.getNotEquals() != null) {
            filterQueryParams.add(ofNotEquals(fieldName, formatter.apply(filter.getNotEquals())));
        }
        if (filter.getIn() != null && !filter.getIn().isEmpty()) {
            filterQueryParams.add(ofIn(fieldName, filter.getIn().stream().map(formatter).collect(Collectors.toList())));
        }
        if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
            filterQueryParams.add(ofNotIn(fieldName, filter.getNotIn().stream().map(formatter).collect(Collectors.toList())));
        }
        if (filter.getSpecified() != null) {
            filterQueryParams.add(ofSpecified(fieldName, booleanFormatter.apply(filter.getSpecified())));
        }
        return filterQueryParams;
    }

}
