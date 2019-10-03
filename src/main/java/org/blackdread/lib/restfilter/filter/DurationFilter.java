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
