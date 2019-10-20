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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not sure to have implementation with extend, anyway needed methods that were private became public so no need anymore
 */
class CriteriaQueryParamSpringExtend /*extends CriteriaQueryParamImpl implements CriteriaQueryParamSpring*/ {

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamSpringExtend.class);

    private CriteriaQueryParamSpringExtend() {

    }

//    @Override
//    public UriBuilder buildQueryParams(final Object criteria, final UriBuilder builder) {
//        final Map<String, Filter> filtersByFieldName = CriteriaFieldParserUtil.build(criteria);
//        final List<FilterQueryParam> filterQueryParams = getFilterQueryParams(filtersByFieldName);
//        for (final FilterQueryParam filterQueryParam : filterQueryParams) {
//            builder = builder.queryParam(filterQueryParam.getParamName(), filterQueryParam.getParamValues());
//        }
//        return builder;
//    }

//    @Override
//    public UriBuilder buildQueryParams(final String fieldName, final Filter filter, final UriBuilder builder) {
//        final List<FilterQueryParam> filterQueryParams = getFilterQueryParams(fieldName, filter);
//        for (final FilterQueryParam filterQueryParam : filterQueryParams) {
//            builder = builder.queryParam(filterQueryParam.getParamName(), filterQueryParam.getParamValues());
//        }
//        return builder;
//    }
}
