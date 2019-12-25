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
import org.blackdread.lib.restfilter.filter.LongFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
@SuppressWarnings("unused")
public class NoAnnotationCriteria {

    public static NoAnnotationCriteria allWithValue() {
        final NoAnnotationCriteria criteria = new NoAnnotationCriteria();
        criteria.primitiveLong = 1L;
        criteria.objectLong = 1L;
        criteria.longFilter = new LongFilter();
        criteria.longFilter.setEquals(1L);
        criteria.arrayPrimitiveLong = new long[]{1L, 2L};
        criteria.arrayObjectLong = new Long[]{1L, 2L};
        criteria.arrayLongFilter = new LongFilter[]{new LongFilter(), new LongFilter()};
        criteria.listLong = List2.of(1L, 2L);
        criteria.listLongFilter = List2.of(new LongFilter(), new LongFilter());
        criteria.mapLong = Map2.of("first", 1L, "second", 2L);
        criteria.mapLongFilter = Map2.of("first", new LongFilter(), "second", new LongFilter());
        return criteria;
    }

    private long primitiveLong;

    private Long objectLong;

    private LongFilter longFilter;

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

    public Long getComputed2() {
        return 1L;
    }

    public LongFilter getComputed3() {
        return new LongFilter();
    }

    public long getWithParam1(long param){
        return 1;
    }

    public Long getWithParam2(long param){
        return 1L;
    }

    public LongFilter getWithParam3(long param){
        return new LongFilter();
    }

    public long getPrimitiveLong() {
        return primitiveLong;
    }

    public void setPrimitiveLong(final long primitiveLong) {
        this.primitiveLong = primitiveLong;
    }

    public Long getObjectLong() {
        return objectLong;
    }

    public void setObjectLong(final Long objectLong) {
        this.objectLong = objectLong;
    }

    public LongFilter getLongFilter() {
        return longFilter;
    }

    public void setLongFilter(final LongFilter longFilter) {
        this.longFilter = longFilter;
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
