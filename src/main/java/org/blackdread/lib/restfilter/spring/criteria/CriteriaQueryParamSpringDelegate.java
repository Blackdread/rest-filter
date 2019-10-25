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
import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.util.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamSpringDelegate implements CriteriaQueryParamSpring {

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamSpringDelegate.class);

    // better to use delegate than extend other implementation
    private final CriteriaQueryParam delegate;

    CriteriaQueryParamSpringDelegate(final CriteriaQueryParam delegate) {
        this.delegate = delegate;
    }

    @Override
    public MultiValueMap<String, String> buildQueryParams(final Criteria criteria) {
        return delegate.buildQueryParams(criteria);
    }

    @Override
    public MultiValueMap<String, String> buildQueryParams(final Object criteria) {
        return delegate.buildQueryParams(criteria);
    }

    @Override
    public MultiValueMap<String, String> buildQueryParams(final String filterName, final Filter filter) {
        return delegate.buildQueryParams(filterName, filter);
    }

    @Override
    public List<FilterQueryParam> getFilterQueryParams(final Map<String, Filter> filtersByFilterName) {
        return delegate.getFilterQueryParams(filtersByFilterName);
    }

    @Override
    public List<FilterQueryParam> getFilterQueryParams(final String filterName, final Filter filter) {
        return delegate.getFilterQueryParams(filterName, filter);
    }

}
