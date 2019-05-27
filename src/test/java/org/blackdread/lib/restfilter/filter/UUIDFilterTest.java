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
import java.util.UUID;

public class UUIDFilterTest {

    private UUIDFilter filter;

    private UUID value = UUID.fromString("dbc36987-d354-4ddf-9b53-38ca19b5a409");

    @BeforeEach
    public void setup() {
        filter = new UUIDFilter();
    }

    @Test
    public void testConstructor() {
        Assertions.assertNull(filter.getEquals());
        Assertions.assertNull(filter.getSpecified());
        Assertions.assertNull(filter.getIn());
        Assertions.assertEquals("UUIDFilter []", filter.toString());
    }

    @Test
    public void testCopy() {
        final UUIDFilter copy = filter.copy();
        Assertions.assertNotSame(copy, filter);
        Assertions.assertNull(copy.getEquals());
        Assertions.assertNull(copy.getSpecified());
        Assertions.assertNull(copy.getIn());
        Assertions.assertEquals("UUIDFilter []", copy.toString());
    }

    @Test
    public void testSetEquals() {
        Filter<UUID> chain = filter.setEquals(value);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(value, filter.getEquals());
    }

    @Test
    public void testSetSpecified() {
        Filter<UUID> chain = filter.setSpecified(true);
        Assertions.assertEquals(filter, chain);
        Assertions.assertTrue(filter.getSpecified());
    }

    @Test
    public void testSetIn() {
        List<UUID> list = new LinkedList<>();
        Filter<UUID> chain = filter.setIn(list);
        Assertions.assertEquals(filter, chain);
        Assertions.assertEquals(list, filter.getIn());
    }

    @Test
    public void testToString() {
        filter.setEquals(value);
        filter.setSpecified(true);
        filter.setIn(new LinkedList<>());
        Assertions.assertEquals("UUIDFilter [equals=dbc36987-d354-4ddf-9b53-38ca19b5a409, in=[], specified=true]", filter.toString());
    }
}
