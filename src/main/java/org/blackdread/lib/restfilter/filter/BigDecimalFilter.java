package org.blackdread.lib.restfilter.filter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Filter class for {@link BigDecimal} type attributes.
 *
 * @see RangeFilter
 */
public class BigDecimalFilter extends RangeFilter<BigDecimal> {

    private static final long serialVersionUID = 1L;

    public BigDecimalFilter() {
    }

    public BigDecimalFilter(final BigDecimalFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter copy() {
        return new BigDecimalFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setEquals(BigDecimal equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setNotEquals(BigDecimal notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setIn(List<BigDecimal> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setGreaterThan(BigDecimal greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setGreaterThanOrEqual(BigDecimal greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setLessThan(BigDecimal lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimalFilter setLessThanOrEqual(BigDecimal lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
