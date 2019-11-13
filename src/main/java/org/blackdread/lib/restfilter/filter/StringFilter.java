/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Original taken from jhipster project, this class has been modified.
 */
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
 * fieldName.ignoreCase=true
 * fieldName.ignoreCase=false
 * </code>
 */
public class StringFilter extends Filter<String> {

    private static final long serialVersionUID = 1L;

    private String contains;
    private String notContains;
    private boolean ignoreCase = true;

    public StringFilter() {
    }

    public StringFilter(final StringFilter filter) {
        super(filter);
        this.contains = filter.contains;
        this.notContains = filter.notContains;
        this.ignoreCase = filter.ignoreCase;
    }

    @Override
    public Class<String> obtainGenericClass() {
        return String.class;
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

    /**
     * Default is true
     *
     * @return if ignore case for filtering
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
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
            Objects.equals(notContains, that.notContains) &&
            Objects.equals(ignoreCase, that.ignoreCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contains, notContains, ignoreCase);
    }

    @Override
    public String toString() {
        return getFilterName() + " ["
            + (getContains() != null ? "contains=" + getContains() + ", " : "")
            + (getNotContains() != null ? "notContains=" + getNotContains() + ", " : "")
            + (getEquals() != null ? "equals=" + getEquals() + ", " : "")
            + (getNotEquals() != null ? "notEquals=" + getNotEquals() + ", " : "")
            + (getSpecified() != null ? "specified=" + getSpecified() + ", " : "")
            + (getIn() != null ? "in=" + getIn() + ", " : "")
            + (getNotIn() != null ? "notIn=" + getNotIn() + ", " : "")
            + ("ignoreCase=" + isIgnoreCase())
            + "]";
    }


}
