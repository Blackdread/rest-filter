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

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.blackdread.lib.restfilter.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CriteriaQueryParam {

    String FIELD_NAME_AND_FILTER_SEPARATOR = ".";

    String EQUALS_FILTER = "equals";
    String NOT_EQUALS_FILTER = "notEquals";
    String IN_FILTER = "in";
    String NOT_IN_FILTER = "notIn";
    String SPECIFIED_FILTER = "specified";

    String GREATER_THAN_FILTER = "greaterThan";
    String GREATER_THAN_OR_EQUAL_FILTER = "greaterThanOrEqual";
    String LESS_THAN_FILTER = "lessThan";
    String LESS_OR_EQUAL_THAN_FILTER = "lessThanOrEqual";

    String CONTAINS_FILTER = "contains";
    String NOT_CONTAINS_FILTER = "notContains";
    String IGNORE_CASE_FILTER = "ignoreCase";

    /**
     * Extract criteria params from {@code criteria} and map the param name as key.
     *
     * @param criteria criteria
     * @return Map with param name as key
     */
    default MultiValueMap<String, String> buildQueryParamsMap(final Criteria criteria) {
        return buildQueryParamsMap((Object) criteria);
    }

    /**
     * Extract criteria params from {@code criteria} and map the param name as key.
     *
     * @param criteria criteria
     * @return Map with param name as key
     */
    default MultiValueMap<String, String> buildQueryParamsMap(final Object criteria) {
        final LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        buildQueryParams(criteria)
            .forEach(queryParam -> {
                multiValueMap.addAll(queryParam.getParamName(), queryParam.getParamValues());
            });
        return multiValueMap;
    }

    /**
     * @param paramName a
     * @param filter a
     * @deprecated not sure to keep
     */
    default MultiValueMap<String, String> buildQueryParamsMap(final String paramName, final Filter filter) {
        final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        getFilterQueryParams(paramName, filter)
            .forEach(filterQueryParam -> map.addAll(filterQueryParam.getParamName(), filterQueryParam.getParamValues()));
        return map;
    }

    /**
     * Extract {@link QueryParam} from criteria object
     *
     * @param criteria criteria to extract {@link QueryParam}
     * @return query params from given criteria
     */
    default List<QueryParam> buildQueryParams(final Criteria criteria) {
        return buildQueryParams((Object) criteria);
    }

    /**
     * Extract {@link QueryParam} from criteria object
     *
     * @param criteria criteria to extract {@link QueryParam}
     * @return query params from given criteria
     */
    List<QueryParam> buildQueryParams(final Object criteria);

    /**
     * todo might be better to return QueryParam
     *
     * @param filtersByFilterName a
     * @return a
     * @deprecated not sure to keep as public API
     */
    default List<FilterQueryParam> getFilterQueryParams(final Map<String, Filter> filtersByFilterName) {
        return filtersByFilterName.entrySet().stream()
            .flatMap(e -> getFilterQueryParams(e.getKey(), e.getValue()).stream())
            .collect(Collectors.toList());
    }

    /**
     * todo might be better to return QueryParam
     *
     * @param paramName name of filter (from field/method/alias/etc)
     * @param filter    filter to extract query params
     * @return list of query params, may be empty
     * @deprecated not sure to keep as public API
     */
    List<FilterQueryParam> getFilterQueryParams(final String paramName, final Filter filter);
}

