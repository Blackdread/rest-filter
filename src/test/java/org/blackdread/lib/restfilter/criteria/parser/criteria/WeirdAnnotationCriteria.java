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
package org.blackdread.lib.restfilter.criteria.parser.criteria;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.Map2;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaAlias;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaIgnore;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;
import org.blackdread.lib.restfilter.filter.LongFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings("unused")
public class WeirdAnnotationCriteria {

    public static WeirdAnnotationCriteria allWithValue() {
        final WeirdAnnotationCriteria criteria = new WeirdAnnotationCriteria();
        criteria.wontInclude1 = 1L;
        criteria.wontInclude2 = 1L;
        criteria.wontInclude3 = new LongFilter();
        criteria.wontInclude3.setEquals(1L);
        criteria.arrayPrimitiveLong = new long[]{1L, 2L};
        criteria.arrayObjectLong = new Long[]{1L, 2L};
        criteria.arrayLongFilter = new LongFilter[]{new LongFilter(), new LongFilter()};
        criteria.listLong = List2.of(1L, 2L);
        criteria.listLongFilter = List2.of(new LongFilter(), new LongFilter());
        criteria.mapLong = Map2.of("first", 1L, "second", 2L);
        criteria.mapLongFilter = Map2.of("first", new LongFilter(), "second", new LongFilter());
        return criteria;
    }

    @CriteriaIgnore
    @CriteriaInclude
    @CriteriaAlias("any")
    private long wontInclude1;

    @CriteriaIgnore
    @CriteriaInclude
    @CriteriaAlias("any")
    private Long wontInclude2;

    @CriteriaIgnore
    @CriteriaInclude
    @CriteriaAlias("any")
    private LongFilter wontInclude3;

    private long[] arrayPrimitiveLong;

    private Long[] arrayObjectLong;

    private LongFilter[] arrayLongFilter; // not supported but does not throw during parse

    private List<Long> listLong;

    private List<LongFilter> listLongFilter; // not supported but does not throw during parse

    private Map<String, Long> mapLong; // not supported

    private Map<String, LongFilter> mapLongFilter; // not supported

    public long getComputed1() {
        return 1;
    }

    @CriteriaInclude(Long.class) // useless and may throw
    public Long getComputed2() {
        return 1L;
    }

    @CriteriaIgnore
    @CriteriaInclude
    public LongFilter wontInclude3() {
        return new LongFilter();
    }

    @CriteriaIgnore
    @CriteriaInclude
    public LongFilter getComputed3() {
        return new LongFilter();
    }

    public long getWithParam1(long param) {
        return 1;
    }

    public Long getWithParam2(long param) {
        return 1L;
    }

    public LongFilter getWithParam3(long param) {
        return new LongFilter();
    }

    public long getWontInclude1() {
        return wontInclude1;
    }

    public void setWontInclude1(final long wontInclude1) {
        this.wontInclude1 = wontInclude1;
    }

    @CriteriaAlias("include2ButFromMethod")
    @CriteriaInclude
    public Long getWontInclude2() {
        return wontInclude2;
    }

    public void setWontInclude2(final Long wontInclude2) {
        this.wontInclude2 = wontInclude2;
    }

    public LongFilter getWontInclude3() {
        return wontInclude3;
    }

    public void setWontInclude3(final LongFilter wontInclude3) {
        this.wontInclude3 = wontInclude3;
    }

    public long[] getArrayPrimitiveLong() {
        return arrayPrimitiveLong;
    }

    public void setArrayPrimitiveLong(final long[] arrayPrimitiveLong) {
        this.arrayPrimitiveLong = arrayPrimitiveLong;
    }

    public Long[] getArrayObjectLong() {
        return arrayObjectLong;
    }

    public void setArrayObjectLong(final Long[] arrayObjectLong) {
        this.arrayObjectLong = arrayObjectLong;
    }

    public LongFilter[] getArrayLongFilter() {
        return arrayLongFilter;
    }

    public void setArrayLongFilter(final LongFilter[] arrayLongFilter) {
        this.arrayLongFilter = arrayLongFilter;
    }

    public List<Long> getListLong() {
        return listLong;
    }

    public void setListLong(final List<Long> listLong) {
        this.listLong = listLong;
    }

    public List<LongFilter> getListLongFilter() {
        return listLongFilter;
    }

    public void setListLongFilter(final List<LongFilter> listLongFilter) {
        this.listLongFilter = listLongFilter;
    }

    public Map<String, Long> getMapLong() {
        return mapLong;
    }

    public void setMapLong(final Map<String, Long> mapLong) {
        this.mapLong = mapLong;
    }

    public Map<String, LongFilter> getMapLongFilter() {
        return mapLongFilter;
    }

    public void setMapLongFilter(final Map<String, LongFilter> mapLongFilter) {
        this.mapLongFilter = mapLongFilter;
    }
}
