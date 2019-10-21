package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;

import java.util.List;

/**
 * <p>Created on 2019/10/21.</p>
 *
 * @author Yoann CAPLAIN
 */
@FunctionalInterface
interface FilterQueryParamFormatter {

    /**
     * @param fieldName field name of filter
     * @param filter    filter to extract FilterQueryParam (never null)
     * @param <T>       filter type
     * @return query params, may be empty
     */
    <T extends Filter> List<FilterQueryParam> getFilterQueryParams(String fieldName, T filter);

}
