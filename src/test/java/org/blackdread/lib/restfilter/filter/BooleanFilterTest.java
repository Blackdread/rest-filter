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

import java.util.LinkedList;
import java.util.List;

public class BooleanFilterTest {

    private BooleanFilter filter;

    private Boolean value = true;

    @BeforeEach
    public void setup() {
        filter = new BooleanFilter();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getNotEquals());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertNull(filter.getNotIn());
        Assertions.assertEquals("BooleanFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final BooleanFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getNotEquals());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertNull(copy.getNotIn());
        Assertions.assertEquals("BooleanFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<Boolean> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetNotEquals() {
        Filter<Boolean> chain = filter.setNotEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getNotEquals());
    }

    @Test
    public void testSetSpecified() {
        Filter<Boolean> chain = filter.setSpecified(true);
        Assertions.assertEquals(filter, chain);
        Assertions.assertTrue(filter.getSpecified());
    }

    @Test
    public void testSetIn() {
        List<Boolean> list = new LinkedList<>();
        Filter<Boolean> chain = filter.setIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getIn());
    }

    @Test
    public void testSetNotIn() {
        List<Boolean> list = new LinkedList<>();
        Filter<Boolean> chain = filter.setNotIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getNotIn());
    }

    @Test
    public void testtoString() {
        filter.setEquals(value);
        filter.setNotEquals(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        filter.setNotIn(new LinkedList<>());
        Assertions.assertEquals("BooleanFilter [equals=true, notEquals=true, in=[], notIn=[], specified=true]", filter.toString());
    }
}
