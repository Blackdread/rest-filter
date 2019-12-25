/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * <p>Created on 2019/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class SortQueryParamSpringUtilTest {

    private UriBuilder builder;

    @BeforeEach
    void setUp() {
        builder = UriComponentsBuilder.fromHttpUrl("http://example.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void doesNotThrowWithUnsorted() {
        assertDoesNotThrow(() -> SortQueryParamSpringUtil.addSortQueryParams(Sort.unsorted(), builder));
        assertDoesNotThrow(() -> SortQueryParamSpringUtil.formatSort(Sort.unsorted()));

    }

    @Test
    void addSortQueryParamsNone() {
        final UriBuilder uriBuilder = SortQueryParamSpringUtil.addSortQueryParams(Sort.unsorted(), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com", uri);
    }

    @Test
    void addSortQueryParamsOne() {
        final UriBuilder uriBuilder = SortQueryParamSpringUtil.addSortQueryParams(Sort.by(Sort.Order.asc("name")), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?sort=name,asc", uri);
    }

    @Test
    void addSortQueryParamsMultiple() {
        final UriBuilder uriBuilder = SortQueryParamSpringUtil.addSortQueryParams(Sort.by(Sort.Order.asc("name"), Sort.Order.desc("createTime")), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?sort=name,asc&sort=createTime,desc", uri);
    }

    @Test
    void formatSort() {
        final String sortNone = SortQueryParamSpringUtil.formatSort(Sort.unsorted());
        assertEquals("", sortNone);

        final String sortOne = SortQueryParamSpringUtil.formatSort(Sort.by(Sort.Order.asc("name")));
        assertEquals("sort=name,asc", sortOne);

        final String sortMultiple = SortQueryParamSpringUtil.formatSort(Sort.by(Sort.Order.asc("name"), Sort.Order.desc("createTime")));
        assertEquals("sort=name,asc&sort=createTime,desc", sortMultiple);
    }

    @Test
    void formatOrder() {
        final String formatFull = SortQueryParamSpringUtil.formatOrder(Sort.Order.asc("name"));
        assertEquals("sort=name,asc", formatFull);

        final String formatFull2 = SortQueryParamSpringUtil.formatOrder(Sort.Order.desc("createTime"));
        assertEquals("sort=createTime,desc", formatFull2);
    }

    @Test
    void formatParamValue() {
        final String formatParamValue = SortQueryParamSpringUtil.formatParamValue(Sort.Order.asc("name"));
        assertEquals("name,asc", formatParamValue);

        final String formatParamValue2 = SortQueryParamSpringUtil.formatParamValue(Sort.Order.desc("createTime"));
        assertEquals("createTime,desc", formatParamValue2);
    }
}
