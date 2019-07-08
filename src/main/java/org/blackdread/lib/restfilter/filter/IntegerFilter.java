/*
 * Copyright 2016-2019 the original author or authors.
 *
 * This file is part of the JHipster project, see https://www.jhipster.tech/
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public IntegerFilter copy() {
        return new IntegerFilter(this);
    }

    @Override
    public IntegerFilter setEquals(Integer equals) {
        super.setEquals(equals);
        return this;
    }

    @Override
    public IntegerFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    @Override
    public IntegerFilter setIn(List<Integer> in) {
        super.setIn(in);
        return this;
    }

    @Override
    public IntegerFilter setGreaterThan(Integer greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    @Override
    public IntegerFilter setGreaterThanOrEqual(Integer greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    @Override
    public IntegerFilter setLessThan(Integer lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    @Override
    public IntegerFilter setLessThanOrEqual(Integer lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
