package org.blackdread.lib.restfilter.filter;

import java.util.List;

/**
 * Filter class for {@link Double} type attributes.
 *
 * @see RangeFilter
 */
public class DoubleFilter extends RangeFilter<Double> {

    private static final long serialVersionUID = 1L;

    public DoubleFilter() {
    }

    public DoubleFilter(final DoubleFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter copy() {
        return new DoubleFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setEquals(Double equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setNotEquals(Double notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setIn(List<Double> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setGreaterThan(Double greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setGreaterThanOrEqual(Double greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setLessThan(Double lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DoubleFilter setLessThanOrEqual(Double lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
