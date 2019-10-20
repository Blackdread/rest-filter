package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.util.LinkedMultiValueMap;
import org.blackdread.lib.restfilter.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Depends on Spring util and web, could do without but makes more complicated than needed, Spring is the default for REST.
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

    default MultiValueMap<String, String> buildQueryParams(final Criteria criteria) {
        return buildQueryParams((Object) criteria);
    }

    default UriBuilder buildQueryParams(final Criteria criteria, final UriBuilder builder) {
        return buildQueryParams((Object) criteria, builder);
    }

    default MultiValueMap<String, String> buildQueryParams(final Object criteria) {
        final Map<String, Filter> filters = CriteriaFieldParserUtil.build(criteria);
        final List<MultiValueMap<String, String>> maps = filters.entrySet().stream()
            .map(entry -> buildQueryParams(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        final LinkedMultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        maps.forEach(multiValueMap::addAll);
        return multiValueMap;
    }

    UriBuilder buildQueryParams(final Object criteria, final UriBuilder builder);

    MultiValueMap<String, String> buildQueryParams(final String fieldName, final Filter filter);

    UriBuilder buildQueryParams(final String fieldName, final Filter filter, UriBuilder builder);

}

