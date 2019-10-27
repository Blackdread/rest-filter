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
package org.blackdread.lib.restfilter.spring.criteria;

import org.blackdread.lib.restfilter.criteria.Criteria;
import org.blackdread.lib.restfilter.criteria.CriteriaQueryParam;
import org.blackdread.lib.restfilter.criteria.FilterQueryParam;
import org.blackdread.lib.restfilter.criteria.QueryParam;
import org.blackdread.lib.restfilter.filter.Filter;
import org.springframework.web.util.UriBuilder;

import java.util.List;

/**
 * Same as {@link CriteriaQueryParam} but with functionality dependent on Spring.
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface CriteriaQueryParamSpring extends CriteriaQueryParam {

    static CriteriaQueryParamSpring ofDelegate(final CriteriaQueryParam delegate) {
        return new CriteriaQueryParamSpringDelegate(delegate);
    }

    default UriBuilder buildQueryParams(final Criteria criteria, final UriBuilder builder) {
        return buildQueryParams((Object) criteria, builder);
    }

    default UriBuilder buildQueryParams(final Object criteria, UriBuilder builder) {
        final List<QueryParam> queryParams = buildQueryParams(criteria);
        for (final QueryParam queryParam : queryParams) {
            builder = builder.queryParam(queryParam.getParamName(), queryParam.getParamValues());
        }
        return builder;
    }

    /**
     * @param paramName a
     * @param filter    a
     * @param builder   a
     * @return a
     * @deprecated not sure to keep as public API
     */
    default UriBuilder buildQueryParams(final String paramName, final Filter filter, UriBuilder builder) {
        final List<FilterQueryParam> filterQueryParams = getFilterQueryParams(paramName, filter);
        for (final FilterQueryParam filterQueryParam : filterQueryParams) {
            builder = builder.queryParam(filterQueryParam.getParamName(), filterQueryParam.getParamValues());
        }
        return builder;
    }

}
