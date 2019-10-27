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
package org.blackdread.lib.restfilter.spring.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriBuilder;

/**
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class PageableQueryParamSpringUtil {

    private static final Logger log = LoggerFactory.getLogger(PageableQueryParamSpringUtil.class);

    private PageableQueryParamSpringUtil() {
    }

    // todo later will not be static so can configure param key (page & size can be different for some back-end since it can be configured)

    public static UriBuilder addPageAndSortQueryParams(final Pageable pageable, UriBuilder builder) {
        if(pageable.isUnpaged())
            return builder;
        builder = addPageOnlyQueryParams(pageable, builder);
        return SortQueryParamSpringUtil.addSortQueryParams(pageable.getSort(), builder);
    }

    public static UriBuilder addPageOnlyQueryParams(final Pageable pageable, UriBuilder builder) {
        if(pageable.isUnpaged())
            return builder;
        return builder
            .queryParam("page", pageable.getPageNumber())
            .queryParam("size", pageable.getPageSize());
    }

    public static String formatPage(final Pageable pageable) {
        return "page=" + pageable.getPageNumber();
    }

    public static String formatPageSize(final Pageable pageable) {
        return "size=" + pageable.getPageSize();
    }

}
