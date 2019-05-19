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
        Assertions.assertNull(filter.getContains());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertEquals("StringFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final StringFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getContains());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertEquals("StringFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<String> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetContains() {
        Filter<String> chain = filter.setContains(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getContains());
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
        filter.setContains(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        Assertions.assertEquals("StringFilter [contains=foo, equals=foo, specified=true]", filter.toString());
    }
}
