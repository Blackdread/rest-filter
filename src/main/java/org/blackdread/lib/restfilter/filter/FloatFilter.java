package org.blackdread.lib.restfilter.filter;

import java.util.List;

/**
 * Filter class for {@link Float} type attributes.
 *
 * @see RangeFilter
 */
public class FloatFilter extends RangeFilter<Float> {

    private static final long serialVersionUID = 1L;

    public FloatFilter() {
    }

    public FloatFilter(final FloatFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter copy() {
        return new FloatFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setEquals(Float equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setIn(List<Float> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setGreaterThan(Float greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setGreaterThanOrEqual(Float greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setLessThan(Float lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatFilter setLessThanOrEqual(Float lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
