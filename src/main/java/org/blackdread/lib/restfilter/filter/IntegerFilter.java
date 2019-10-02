package org.blackdread.lib.restfilter.filter;

import java.util.List;

/**
 * Filter class for {@link Integer} type attributes.
 *
 * @see RangeFilter
 */
public class IntegerFilter extends RangeFilter<Integer> {

    private static final long serialVersionUID = 1L;

    public IntegerFilter() {
    }

    public IntegerFilter(final IntegerFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter copy() {
        return new IntegerFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setEquals(Integer equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setIn(List<Integer> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setGreaterThan(Integer greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setGreaterThanOrEqual(Integer greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setLessThan(Integer lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntegerFilter setLessThanOrEqual(Integer lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
