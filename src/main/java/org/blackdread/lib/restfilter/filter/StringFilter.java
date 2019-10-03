package org.blackdread.lib.restfilter.filter;

import java.util.List;
import java.util.Objects;

/**
 * Class for filtering attributes with {@link String} type.
 * It can be added to a criteria class as a member, to support the following query parameters:
 * <code>
 * fieldName.equals='something'
 * fieldName.notEquals='something'
 * fieldName.specified=true
 * fieldName.specified=false
 * fieldName.in='something','other'
 * fieldName.notIn='something','other'
 * fieldName.contains='thing'
 * fieldName.notContains='thing'
 * </code>
 */
public class StringFilter extends Filter<String> {

    private static final long serialVersionUID = 1L;

    private String contains;
    private String notContains;

    public StringFilter() {
    }

    public StringFilter(final StringFilter filter) {
        super(filter);
        this.contains = filter.contains;
        this.notContains = filter.notContains;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter copy() {
        return new StringFilter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter setEquals(String equals) {
        super.setEquals(equals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter setNotEquals(String notEquals) {
        super.setNotEquals(notEquals);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter setIn(List<String> in) {
        super.setIn(in);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StringFilter setNotIn(List<String> notIn) {
        super.setNotIn(notIn);
        return this;
    }

    public String getContains() {
        return contains;
    }

    public StringFilter setContains(String contains) {
        this.contains = contains;
        return this;
    }

    public String getNotContains() {
        return notContains;
    }

    public StringFilter setNotContains(String notContains) {
        this.notContains = notContains;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        final StringFilter that = (StringFilter) o;
        return Objects.equals(contains, that.contains) &&
            Objects.equals(notContains, that.notContains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contains, notContains);
    }

    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getContains() != null ? "contains=" + getContains() + ", " : "")
            + (getNotContains() != null ? "notContains=" + getNotContains() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() : "")
            + "]";
    }

}
