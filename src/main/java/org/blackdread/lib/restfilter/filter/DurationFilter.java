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

import java.time.Duration;
import java.util.List;

/**
 * Filter class for {@link Duration} type attributes.
 *
 * @see Filter
 */
public class DurationFilter extends RangeFilter<Duration> {

    private static final long serialVersionUID = 1L;

    public DurationFilter() {
    }

    public DurationFilter(final DurationFilter filter) {
        super(filter);
    }

    @Override
    public Class<Duration> obtainGenericClass() {
        return Duration.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter copy() {
        return new DurationFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setEquals(Duration equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setNotEquals(Duration notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setIn(List<Duration> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setNotIn(List<Duration> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setGreaterThan(Duration greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setGreaterThanOrEqual(Duration greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setLessThan(Duration lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DurationFilter setLessThanOrEqual(Duration lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
