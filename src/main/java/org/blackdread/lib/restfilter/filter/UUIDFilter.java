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
 *
 * Original taken from jhipster project, this class has been modified.
 */
package org.blackdread.lib.restfilter.filter;

import java.util.List;
import java.util.UUID;

/**
 * Filter class for {@link UUID} type attributes.
 *
 * @see Filter
 */
public class UUIDFilter extends Filter<UUID> {

    private static final long serialVersionUID = 1L;

    public UUIDFilter() {
    }

    public UUIDFilter(final UUIDFilter filter) {
        super(filter);
    }

    @Override
    public Class<UUID> obtainGenericClass() {
        return UUID.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter copy() {
        return new UUIDFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setEquals(UUID equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setNotEquals(UUID notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setIn(List<UUID> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setNotIn(List<UUID> notIn) {
        super.setNotIn(notIn);
        return this;
    }
}
