package org.blackdread.lib.restfilter.filter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import java.time.Instant;
import java.util.List;

/**
 * Filter class for {@link Instant} type attributes.
 *
 * @see RangeFilter
 */
public class InstantFilter extends RangeFilter<Instant> {

    private static final long serialVersionUID = 1L;

    public InstantFilter() {
    }

    public InstantFilter(final InstantFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InstantFilter copy() {
        return new InstantFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setEquals(Instant equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setNotEquals(Instant notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InstantFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setIn(List<Instant> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setNotIn(List<Instant> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setGreaterThan(Instant equals) {
        super.setGreaterThan(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setGreaterThanOrEqual(Instant equals) {
        super.setGreaterThanOrEqual(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setLessThan(Instant equals) {
        super.setLessThan(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DateTimeFormat(iso = ISO.DATE_TIME) // todo annotation might be useless, should register Formatters/other in Spring context
    public InstantFilter setLessThanOrEqual(Instant equals) {
        super.setLessThanOrEqual(equals);
        return this;
    }

}
