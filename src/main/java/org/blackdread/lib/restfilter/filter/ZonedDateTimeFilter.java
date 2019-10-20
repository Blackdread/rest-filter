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
