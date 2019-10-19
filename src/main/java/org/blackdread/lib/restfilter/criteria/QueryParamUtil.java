package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated no use of static
 */
public final class QueryParamUtil {

    private static final Logger log = LoggerFactory.getLogger(QueryParamUtil.class);

    // format for dates: 2018-01-01T10:10:00Z

    private static final String FIELD_NAME_AND_FILTER_SEPARATOR = ".";

    private static final String EQUALS_FILTER = "equals";
    private static final String NOT_EQUALS_FILTER = "notEquals";
    private static final String IN_FILTER = "in";
    private static final String NOT_IN_FILTER = "notIn";
    private static final String SPECIFIED_FILTER = "specified";

    private static final String GREATER_THAN_FILTER = "greaterThan";
    private static final String GREATER_OR_EQUAL_THAN_FILTER = "greaterOrEqualThan";
    private static final String LESS_THAN_FILTER = "lessThan";
    private static final String LESS_OR_EQUAL_THAN_FILTER = "lessOrEqualThan";

    private static final String CONTAINS_FILTER = "contains";
    private static final String NOT_CONTAINS_FILTER = "notContains";

    private QueryParamUtil() {
    }

    public static Object buildQueryParams(Criteria criteria) {
        return null;
    }

    public static Object buildQueryParams(Object criteria) {
        return null;
    }
}
