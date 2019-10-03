package org.blackdread.lib.restfilter.filter;

import java.util.List;

/**
 * Filter class for {@link Short} type attributes.
 *
 * @see RangeFilter
 */
public class ShortFilter extends RangeFilter<Short> {

    private static final long serialVersionUID = 1L;

    public ShortFilter() {
    }

    public ShortFilter(final ShortFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter copy() {
        return new ShortFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setEquals(Short equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setNotEquals(Short notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setIn(List<Short> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setNotIn(List<Short> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setGreaterThan(Short greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setGreaterThanOrEqual(Short greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setLessThan(Short lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortFilter setLessThanOrEqual(Short lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
