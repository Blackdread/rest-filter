package org.blackdread.lib.restfilter.spring.query;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * <p>Created on 2019/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
class PageableQueryParamSpringUtilTest {

    private UriBuilder builder;

    @BeforeEach
    void setUp() {
        builder = UriComponentsBuilder.fromHttpUrl("http://example.com");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPageAndSortQueryParamsNone() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageAndSortQueryParams(PageRequest.of(0, 1), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=0&size=1", uri);
    }

    @Test
    void addPageAndSortQueryParamsOne() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageAndSortQueryParams(PageRequest.of(5, 50, Sort.by(Sort.Order.asc("name"))), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=5&size=50&sort=name,asc", uri);
    }

    @Test
    void addPageAndSortQueryParamsMultiple() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageAndSortQueryParams(PageRequest.of(10, 100, Sort.by(Sort.Order.asc("name"), Sort.Order.desc("createTime"))), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=10&size=100&sort=name,asc&sort=createTime,desc", uri);
    }

    @Test
    void addPageOnlyQueryParamsNone() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageOnlyQueryParams(PageRequest.of(0, 1), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=0&size=1", uri);
    }

    @Test
    void addPageOnlyQueryParamsOne() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageOnlyQueryParams(PageRequest.of(5, 50, Sort.by(Sort.Order.asc("name"))), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=5&size=50", uri);
    }

    @Test
    void addPageOnlyQueryParamsMultiple() {
        final UriBuilder uriBuilder = PageableQueryParamSpringUtil.addPageOnlyQueryParams(PageRequest.of(10, 100, Sort.by(Sort.Order.asc("name"), Sort.Order.desc("createTime"))), builder);
        final String uri = uriBuilder.build().toString();
        assertEquals("http://example.com?page=10&size=100", uri);
    }

    @Test
    void formatPageThrowsIfUnPaged() {
        assertThrows(UnsupportedOperationException.class, () -> PageableQueryParamSpringUtil.formatPage(Pageable.unpaged()));
    }

    @Test
    void formatPage() {
        final String formatPage = PageableQueryParamSpringUtil.formatPage(PageRequest.of(0, 1));
        assertEquals("page=0", formatPage);

        final String formatPage1 = PageableQueryParamSpringUtil.formatPage(PageRequest.of(5, 50));
        assertEquals("page=5", formatPage1);

        final String formatPage2 = PageableQueryParamSpringUtil.formatPage(PageRequest.of(10, 100));
        assertEquals("page=10", formatPage2);
    }

    @Test
    void formatPageSizeThrowsIfUnPaged() {
        assertThrows(UnsupportedOperationException.class, () -> PageableQueryParamSpringUtil.formatPageSize(Pageable.unpaged()));
    }

    @Test
    void formatPageSize() {
        final String formatPageSize = PageableQueryParamSpringUtil.formatPageSize(PageRequest.of(0, 1));
        assertEquals("size=1", formatPageSize);

        final String formatPageSize1 = PageableQueryParamSpringUtil.formatPageSize(PageRequest.of(5, 50));
        assertEquals("size=50", formatPageSize1);

        final String formatPageSize2 = PageableQueryParamSpringUtil.formatPageSize(PageRequest.of(10, 100));
        assertEquals("size=100", formatPageSize2);
    }
}
