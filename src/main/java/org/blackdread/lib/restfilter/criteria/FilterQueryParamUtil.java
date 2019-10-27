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
@SuppressWarnings({"unused", "WeakerAccess"})
public final class FilterQueryParamUtil {

    private static final Logger log = LoggerFactory.getLogger(FilterQueryParamUtil.class);

    private FilterQueryParamUtil() {
    }

    public static <T> List<FilterQueryParam> buildQueryParams(final String criteriaName, final Filter<T> filter, final Function<T, String> formatter) {
        return buildQueryParams(criteriaName, filter, formatter, Object::toString);
    }

    public static <T> List<FilterQueryParam> buildQueryParams(final String criteriaName, final Filter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        return applyFilter(criteriaName, filter, formatter, booleanFormatter);
    }

    public static List<FilterQueryParam> buildFilterQueryParams(final String criteriaName, final Filter filter, final Function objectToStringFormatter, final Function booleanToStringFormatter) {
        return applyFilterWildcard(criteriaName, filter, objectToStringFormatter, booleanToStringFormatter);
    }

    public static <T extends Comparable<? super T>> List<FilterQueryParam> buildQueryParams(final String criteriaName, final RangeFilter<T> filter, final Function<T, String> formatter) {
        return buildQueryParams(criteriaName, filter, formatter, Object::toString);
    }

    public static <T extends Comparable<? super T>> List<FilterQueryParam> buildQueryParams(final String criteriaName, final RangeFilter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        return buildRangeFilterQueryParams(criteriaName, filter, formatter, booleanFormatter);
    }

    @SuppressWarnings("unchecked")
    public static List<FilterQueryParam> buildRangeFilterQueryParams(final String criteriaName, final RangeFilter filter, final Function objectToStringFormatter, final Function booleanFormatter) {
        final List<FilterQueryParam> filterQueryParams = applyFilterWildcard(criteriaName, filter, objectToStringFormatter, booleanFormatter);
        if (filter.getGreaterThan() != null) {
            filterQueryParams.add(ofGreaterThan(criteriaName, (String) objectToStringFormatter.apply(filter.getGreaterThan())));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            filterQueryParams.add(ofGreaterThanOrEqual(criteriaName, (String) objectToStringFormatter.apply(filter.getGreaterThanOrEqual())));
        }
        if (filter.getLessThan() != null) {
            filterQueryParams.add(ofLessThan(criteriaName, (String) objectToStringFormatter.apply(filter.getLessThan())));
        }
        if (filter.getLessThanOrEqual() != null) {
            filterQueryParams.add(ofLessThanOrEqual(criteriaName, (String) objectToStringFormatter.apply(filter.getLessThanOrEqual())));
        }
        return filterQueryParams;
    }

    public static List<FilterQueryParam> buildQueryParams(final String criteriaName, final StringFilter filter) {
        return buildQueryParams(criteriaName, filter, String::toString);
    }

    public static List<FilterQueryParam> buildQueryParams(final String criteriaName, final StringFilter filter, final Function<String, String> formatter) {
        return buildQueryParams(criteriaName, filter, formatter, Object::toString);
    }

    public static List<FilterQueryParam> buildQueryParams(final String criteriaName, final StringFilter filter, final Function<String, String> formatter, final Function<Boolean, String> booleanFormatter) {
        return buildStringFilterQueryParams(criteriaName, filter, formatter, booleanFormatter);
    }

    @SuppressWarnings("unchecked")
    public static List<FilterQueryParam> buildStringFilterQueryParams(final String criteriaName, final StringFilter filter, final Function stringToStringFormatter, final Function booleanToStringFormatter) {
        final List<FilterQueryParam> filterQueryParams = applyFilterWildcard(criteriaName, filter, stringToStringFormatter, booleanToStringFormatter);
        if (filter.getContains() != null) {
            filterQueryParams.add(ofContains(criteriaName, (String) stringToStringFormatter.apply(filter.getContains())));
        }
        if (filter.getNotContains() != null) {
            filterQueryParams.add(ofNotContains(criteriaName, (String) stringToStringFormatter.apply(filter.getNotContains())));
        }
        if (!filter.isIgnoreCase()) {
            filterQueryParams.add(ofIgnoreCase(criteriaName, (String) booleanToStringFormatter.apply(false)));
        }
        return filterQueryParams;
    }

    private static <T> List<FilterQueryParam> applyFilter(final String criteriaName, final Filter<T> filter, final Function<T, String> formatter) {
        return applyFilter(criteriaName, filter, formatter, Object::toString);
    }

    private static <T> List<FilterQueryParam> applyFilter(final String criteriaName, final Filter<T> filter, final Function<T, String> formatter, final Function<Boolean, String> booleanFormatter) {
        return applyFilterWildcard(criteriaName, filter, formatter, booleanFormatter);
    }

//    private static List<FilterQueryParam> applyFilterWildcard(final String criteriaName, final Filter<?> filter, final Function<?, String> objectToStringFormatter, final Function<?, String> booleanToStringFormatter) {
//
//    }

    @SuppressWarnings("unchecked")
    private static List<FilterQueryParam> applyFilterWildcard(final String criteriaName, final Filter filter, final Function objectToStringFormatter, final Function booleanToStringFormatter) {
        final List<FilterQueryParam> filterQueryParams = new ArrayList<>();
        if (filter.getEquals() != null) {
            filterQueryParams.add(ofEquals(criteriaName, (String) objectToStringFormatter.apply(filter.getEquals())));
        }
        if (filter.getNotEquals() != null) {
            filterQueryParams.add(ofNotEquals(criteriaName, (String) objectToStringFormatter.apply(filter.getNotEquals())));
        }
        if (filter.getIn() != null && !filter.getIn().isEmpty()) {
            filterQueryParams.add(ofIn(criteriaName, (List<String>) filter.getIn().stream().map(objectToStringFormatter).collect(Collectors.toList())));
        }
        if (filter.getNotIn() != null && !filter.getNotIn().isEmpty()) {
            filterQueryParams.add(ofNotIn(criteriaName, (List<String>) filter.getNotIn().stream().map(objectToStringFormatter).collect(Collectors.toList())));
        }
        if (filter.getSpecified() != null) {
            filterQueryParams.add(ofSpecified(criteriaName, (String) booleanToStringFormatter.apply(filter.getSpecified())));
        }
        return filterQueryParams;
    }

}
