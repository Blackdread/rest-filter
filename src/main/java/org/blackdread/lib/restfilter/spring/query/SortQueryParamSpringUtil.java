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
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriBuilder;

import java.util.StringJoiner;

/**
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class SortQueryParamSpringUtil {

    private static final Logger log = LoggerFactory.getLogger(SortQueryParamSpringUtil.class);

    private static final String SORT_KEY = "sort";

    private SortQueryParamSpringUtil() {
    }

    // todo later will not be static so can configure better

    public static UriBuilder addSortQueryParams(final Sort sort, UriBuilder builder) {
        for (final Sort.Order order : sort) {
            builder = builder.queryParam(SORT_KEY, formatParamValue(order));
        }
        return builder;
    }

    public static String formatSort(final Sort sort) {
        final StringJoiner builder = new StringJoiner("&");
        for (final Sort.Order order : sort) {
            builder.add(formatFull(order));
        }
        return builder.toString();
    }

    public static String formatFull(final Sort.Order order) {
        return SORT_KEY + "=" + formatParamValue(order);
    }

    public static String formatParamValue(final Sort.Order order) {
        return order.getProperty() + "," + order.getDirection().name().toLowerCase();
    }
}
