package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;

/**
 * Thrown when {@link CriteriaQueryParam} could not format a filter type.
 * <p>Created on 2019/10/21.</p>
 *
 * @author Yoann CAPLAIN
 */
public class UnsupportedFilterForQueryParamException extends IllegalArgumentException {

    private final String fieldName;
    private final Filter filter;

    public UnsupportedFilterForQueryParamException(final String fieldName, final Filter filter) {
        this.fieldName = fieldName;
        this.filter = filter;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Filter getFilter() {
        return filter;
    }
}
