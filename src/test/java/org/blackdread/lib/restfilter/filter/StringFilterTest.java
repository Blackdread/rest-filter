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

public class StringFilterTest {

    private StringFilter filter;

    private String value = "foo";

    @BeforeEach
    public void setup() {
        filter = new StringFilter();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getNotEquals());
        Assertions.assertNull(filter.getContains());
        Assertions.assertNull(filter.getNotContains());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertNull(filter.getNotIn());
        Assertions.assertEquals("StringFilter [ignoreCase=true]", filter.toString());
    }

    @Test
    public void testCopy() {
        final StringFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getNotEquals());
        Assertions.assertNull(copy.getContains());
        Assertions.assertNull(copy.getNotContains());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertNull(copy.getNotIn());
        Assertions.assertEquals("StringFilter [ignoreCase=true]", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<String> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetNotEquals() {
        Filter<String> chain = filter.setNotEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getNotEquals());
    }

    @Test
    public void testSetContains() {
        Filter<String> chain = filter.setContains(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getContains());
    }

    @Test
    public void testSetNotContains() {
        Filter<String> chain = filter.setNotContains(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getNotContains());
    }

    @Test
    public void testSetSpecified() {
        Filter<String> chain = filter.setSpecified(true);
        Assertions.assertEquals(filter, chain);
        Assertions.assertTrue(filter.getSpecified());
    }

    @Test
    public void testSetIn() {
        List<String> list = new LinkedList<>();
        Filter<String> chain = filter.setIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getIn());
    }

    @Test
    public void testSetNotIn() {
        List<String> list = new LinkedList<>();
        Filter<String> chain = filter.setNotIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getNotIn());
    }

    @Test
    public void testEquals() {
        final StringFilter filter2 = new StringFilter();
        Assertions.assertEquals(filter2, filter);
        filter.setEquals(value);
        filter2.setEquals(value);
        Assertions.assertEquals(filter2, filter);
        filter.setIn(Arrays.asList(value, value));
        filter2.setIn(Arrays.asList(value, value));
        Assertions.assertEquals(filter2, filter);
        filter.setContains(value);
        filter2.setContains(value);
        Assertions.assertEquals(filter2, filter);
        final StringFilter filter3 = new StringFilter();
        filter3.setEquals(value);
        Assertions.assertNotEquals(filter, filter3);
        Assertions.assertNotEquals(filter2, filter3);

        Assertions.assertEquals(filter, filter);
    }

    @Test
    public void testHashCode() {
        final StringFilter filter2 = new StringFilter();
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setEquals(value);
        filter2.setEquals(value);
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setIn(Arrays.asList(value, value));
        filter2.setIn(Arrays.asList(value, value));
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        filter.setContains(value);
        filter2.setContains(value);
        Assertions.assertEquals(filter2.hashCode(), filter.hashCode());
        final StringFilter filter3 = new StringFilter();
        filter3.setEquals(value);
        Assertions.assertNotEquals(filter.hashCode(), filter3.hashCode());
        Assertions.assertNotEquals(filter2.hashCode(), filter3.hashCode());

        Assertions.assertEquals(filter.hashCode(), filter.hashCode());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setContains(value);
        filter.setNotContains(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        Assertions.assertEquals("StringFilter [contains=foo, notContains=foo, equals=foo, notEquals=foo, specified=true, in=[], notIn=[], ignoreCase=true]", filter.toString());
    }
}
