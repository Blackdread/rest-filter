package org.blackdread.lib.restfilter.filter;

import java.util.List;
import java.util.UUID;

/**
 * Filter class for {@link UUID} type attributes.
 *
 * @see Filter
 */
public class UUIDFilter extends Filter<UUID> {

    private static final long serialVersionUID = 1L;

    public UUIDFilter() {
    }

    public UUIDFilter(final UUIDFilter filter) {
        super(filter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter copy() {
        return new UUIDFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setEquals(UUID equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setNotEquals(UUID notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUIDFilter setIn(List<UUID> in) {
        super.setIn(in);
        return this;
    }
}
