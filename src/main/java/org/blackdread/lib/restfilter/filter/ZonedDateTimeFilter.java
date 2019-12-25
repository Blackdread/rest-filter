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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Filter class for {@link ZonedDateTime} type attributes.
 *
 * @see RangeFilter
 */
public class ZonedDateTimeFilter extends RangeFilter<ZonedDateTime> {

    private static final long serialVersionUID = 1L;

    public ZonedDateTimeFilter() {
    }

    public ZonedDateTimeFilter(final ZonedDateTimeFilter filter) {
        super(filter);
    }

    @Override
    public Class<ZonedDateTime> obtainGenericClass() {
        return ZonedDateTime.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTimeFilter copy() {
        return new ZonedDateTimeFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setEquals(ZonedDateTime equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setNotEquals(ZonedDateTime notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTimeFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setIn(List<ZonedDateTime> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setNotIn(List<ZonedDateTime> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setGreaterThan(ZonedDateTime equals) {
        super.setGreaterThan(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setGreaterThanOrEqual(ZonedDateTime equals) {
        super.setGreaterThanOrEqual(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setLessThan(ZonedDateTime equals) {
        super.setLessThan(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public ZonedDateTimeFilter setLessThanOrEqual(ZonedDateTime equals) {
        super.setLessThanOrEqual(equals);
        return this;
    }
}
