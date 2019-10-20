package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.blackdread.lib.restfilter.criteria.FilterQueryParamImpl.ofEquals;
import static org.blackdread.lib.restfilter.criteria.FilterQueryParamImpl.ofNotEquals;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class QueryParamUtil {

    private static final Logger log = LoggerFactory.getLogger(QueryParamUtil.class);

    private QueryParamUtil() {
    }

    public static Object buildQueryParams(Criteria criteria) {
        return null;
    }

    public static Object buildQueryParams(Object criteria) {
        return null;
    }

    public static <T> List<FilterQueryParam> buildQueryParams(final String fieldName, final Filter<T> filter, final Function<T, String> formatter) {
        final ArrayList<FilterQueryParam> filterQueryParams = new ArrayList<>();
        if (filter.getEquals() != null) {
            filterQueryParams.add(ofEquals(fieldName, formatter.apply(filter.getEquals())));
        }
        if (filter.getNotEquals() != null) {
            filterQueryParams.add(ofNotEquals(fieldName, formatter.apply(filter.getNotEquals())));
        }
        return filterQueryParams;
    }

    public static <T extends Comparable<? super T>> List<FilterQueryParam> buildQueryParams(final RangeFilter<T> filter, final Function<T, String> formatter) {
        return null;
    }

    public static List<FilterQueryParam> buildQueryParams(final StringFilter filter) {
        return buildQueryParams(filter, String::toString);
    }

    public static List<FilterQueryParam> buildQueryParams(final StringFilter filter, final Function<String, String> formatter) {
        return null;
    }

}
