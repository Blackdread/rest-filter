package org.blackdread.lib.restfilter.filter;

import java.util.List;

/**
 * Class for filtering attributes with {@link Boolean} type. It can be added to a criteria class as a member, to support
 * the following query parameters:
 * <pre>
 *      fieldName.equals=true
 *      fieldName.notEquals=true
 *      fieldName.specified=true
 *      fieldName.specified=false
 *      fieldName.in=true,false
 *      fieldName.notIn=true,false
 * </pre>
 */
public class BooleanFilter extends Filter<Boolean> {

    private static final long serialVersionUID = 1L;

    public BooleanFilter() {
    }

    public BooleanFilter(final BooleanFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter copy() {
        return new BooleanFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter setEquals(Boolean equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter setNotEquals(Boolean notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter setIn(List<Boolean> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BooleanFilter setNotIn(List<Boolean> notIn) {
        super.setNotIn(notIn);
        return this;
    }
}
