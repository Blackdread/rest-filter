/*
 * Copyright 2016-2019 the original author or authors from the JHipster project.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.blackdread.lib.restfilter.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class ShortFilterTest {

    private ShortFilter filter;

    private Short value = 42;

    @BeforeEach
    public void setup() {
        filter = new ShortFilter();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getGreaterThan());
        Assertions.assertNull(filter.getGreaterOrEqualThan());
        Assertions.assertNull(filter.getLessThan());
        Assertions.assertNull(filter.getLessOrEqualThan());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertEquals("ShortFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final ShortFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getGreaterThan());
        Assertions.assertNull(copy.getGreaterOrEqualThan());
        Assertions.assertNull(copy.getLessThan());
        Assertions.assertNull(copy.getLessOrEqualThan());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertEquals("ShortFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<Short> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetLessThan() {
        Filter<Short> chain = filter.setLessThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessThan());
    }

    @Test
    public void testSetLessOrEqualThan() {
        Filter<Short> chain = filter.setLessOrEqualThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessOrEqualThan());
    }

    @Test
    public void testSetGreaterThan() {
        Filter<Short> chain = filter.setGreaterThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterThan());
    }

    @Test
    public void testSetGreaterOrEqualThan() {
        Filter<Short> chain = filter.setGreaterOrEqualThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterOrEqualThan());
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
    public void testToString() {
        filter.setEquals(value);
        filter.setLessThan(value);
        filter.setLessOrEqualThan(value);
        filter.setGreaterThan(value);
        filter.setGreaterOrEqualThan(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        String str = value.toString();
        Assertions.assertEquals("ShortFilter "
            + "[greaterThan=" + str + ", greaterOrEqualThan=" + str + ", lessThan=" + str + ", "
            + "lessOrEqualThan=" + str + ", equals=" + str + ", specified=true, in=[]]", filter.toString());
    }
}
