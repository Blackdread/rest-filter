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
package org.blackdread.lib.restfilter.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RangeFilterTest {

    private RangeFilter<Short> filter;

    private Short value = 42;

    @BeforeEach
    public void setup() {
        filter = new RangeFilter<Short>();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getNotEquals());
        Assertions.assertNull(filter.getGreaterThan());
        Assertions.assertNull(filter.getGreaterThanOrEqual());
        Assertions.assertNull(filter.getLessThan());
        Assertions.assertNull(filter.getLessThanOrEqual());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertNull(filter.getNotIn());
        Assertions.assertEquals("RangeFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final RangeFilter<Short> copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getNotEquals());
        Assertions.assertNull(copy.getGreaterThan());
        Assertions.assertNull(copy.getGreaterThanOrEqual());
        Assertions.assertNull(copy.getLessThan());
        Assertions.assertNull(copy.getLessThanOrEqual());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertNull(copy.getNotIn());
        Assertions.assertEquals("RangeFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<Short> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetNotEquals() {
        Filter<Short> chain = filter.setNotEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getNotEquals());
    }

    @Test
    public void testSetLessThan() {
        Filter<Short> chain = filter.setLessThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessThan());
    }

    @Test
    public void testSetlessThanOrEqual() {
        Filter<Short> chain = filter.setLessThanOrEqual(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessThanOrEqual());
    }

    @Test
    public void testSetGreaterThan() {
        Filter<Short> chain = filter.setGreaterThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterThan());
    }

    @Test
    public void testSetGreaterThanOrEqual() {
        Filter<Short> chain = filter.setGreaterThanOrEqual(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterThanOrEqual());
    }

    @Test
    public void testSetSpecified() {
        Filter<Short> chain = filter.setSpecified(true);
        Assertions.assertEquals(filter, chain);
        Assertions.assertTrue(filter.getSpecified());
    }

    @Test
    public void testSetIn() {
        List<Short> list = new LinkedList<>();
        Filter<Short> chain = filter.setIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getIn());
    }

    @Test
    public void testSetNotIn() {
        List<Short> list = new LinkedList<>();
        Filter<Short> chain = filter.setNotIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getNotIn());
    }

    @Test
    public void testEquals() {
        final RangeFilter<Short> filter2 = new RangeFilter<>();
        Assertions.assertEquals(filter2, filter);
        filter.setEquals(value);
        filter2.setEquals(value);
        Assertions.assertEquals(filter2, filter);
        filter.setIn(Arrays.asList(value, value));
        filter2.setIn(Arrays.asList(value, value));
        Assertions.assertEquals(filter2, filter);
        filter.setLessThan(value);
        filter2.setLessThan(value);
        Assertions.assertEquals(filter2, filter);
        filter.setGreaterThanOrEqual(value);
        filter2.setGreaterThanOrEqual(value);
        Assertions.assertEquals(filter2, filter);
        final Filter<Short> filter3 = new Filter<>();
        filter3.setEquals(value);
        Assertions.assertNotEquals(filter, filter3);
        Assertions.assertNotEquals(filter2, filter3);

        Assertions.assertEquals(filter, filter);
    }

    @Test
    public void testHashCode() {
        final RangeFilter<Short> filter2 = new RangeFilter<>();
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setEquals(value);
        filter2.setEquals(value);
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setIn(Arrays.asList(value, value));
        filter2.setIn(Arrays.asList(value, value));
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setLessThan(value);
        filter2.setLessThan(value);
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setGreaterThanOrEqual(value);
        filter2.setGreaterThanOrEqual(value);
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        final Filter<Short> filter3 = new Filter<>();
        filter3.setEquals(value);
        Assertions.assertNotEquals(filter.hashCode(), filter3.hashCode());
        Assertions.assertNotEquals(filter2.hashCode(), filter3.hashCode());

        Assertions.assertEquals(filter.hashCode(), filter.hashCode());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setLessThan(value);
        filter.setLessThanOrEqual(value);
        filter.setGreaterThan(value);
        filter.setGreaterThanOrEqual(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        Assertions.assertEquals("RangeFilter [greaterThan=42, greaterThanOrEqual=42, lessThan=42, lessThanOrEqual=42, equals=42, notEquals=42, specified=true, in=[], notIn=[]]", filter.toString());
    }
}
