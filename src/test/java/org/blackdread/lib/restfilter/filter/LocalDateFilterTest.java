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

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class LocalDateFilterTest {

    private LocalDateFilter filter;

    private LocalDate value = LocalDate.now();

    @BeforeEach
    public void setup() {
        filter = new LocalDateFilter();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getGreaterThan());
        Assertions.assertNull(filter.getGreaterThanOrEqual());
        Assertions.assertNull(filter.getLessThan());
        Assertions.assertNull(filter.getLessThanOrEqual());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertEquals("LocalDateFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final LocalDateFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getGreaterThan());
        Assertions.assertNull(copy.getGreaterThanOrEqual());
        Assertions.assertNull(copy.getLessThan());
        Assertions.assertNull(copy.getLessThanOrEqual());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertEquals("LocalDateFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<LocalDate> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetLessThan() {
        Filter<LocalDate> chain = filter.setLessThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessThan());
    }

    @Test
    public void testSetlessThanOrEqual() {
        Filter<LocalDate> chain = filter.setLessThanOrEqual(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getLessThanOrEqual());
    }

    @Test
    public void testSetGreaterThan() {
        Filter<LocalDate> chain = filter.setGreaterThan(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterThan());
    }

    @Test
    public void testSetgreaterThanOrEqual() {
        Filter<LocalDate> chain = filter.setGreaterThanOrEqual(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getGreaterThanOrEqual());
    }

    @Test
    public void testSetSpecified() {
        Filter<LocalDate> chain = filter.setSpecified(true);
        Assertions.assertEquals(filter, chain);
        Assertions.assertTrue(filter.getSpecified());
    }

    @Test
    public void testSetIn() {
        List<LocalDate> list = new LinkedList<>();
        Filter<LocalDate> chain = filter.setIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getIn());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setLessThan(value);
        filter.setLessThanOrEqual(value);
        filter.setGreaterThan(value);
        filter.setGreaterThanOrEqual(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        String str = value.toString();
        Assertions.assertEquals("LocalDateFilter "
            + "[greaterThan=" + str + ", greaterThanOrEqual=" + str + ", lessThan=" + str + ", "
            + "lessThanOrEqual=" + str + ", equals=" + str + ", specified=true, in=[]]", filter.toString());
    }
}
