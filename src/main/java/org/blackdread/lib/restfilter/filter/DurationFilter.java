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

import java.time.Duration;
import java.util.List;

/**
 * Filter class for {@link Duration} type attributes.
 *
 * @see Filter
 */
public class DurationFilter extends RangeFilter<Duration> {

    private static final long serialVersionUID = 1L;

    public DurationFilter() {
    }

    public DurationFilter(final DurationFilter filter) {
        super(filter);
    }

    public DurationFilter copy() {
        return new DurationFilter(this);
    }

    @Override
    public DurationFilter setEquals(Duration equals) {
        super.setEquals(equals);
        return this;
    }

    @Override
    public DurationFilter setSpecified(Boolean specified) {
        super.setSpecified(specified);
        return this;
    }

    @Override
    public DurationFilter setIn(List<Duration> in) {
        super.setIn(in);
        return this;
    }

    @Override
    public DurationFilter setGreaterThan(Duration greaterThan) {
        super.setGreaterThan(greaterThan);
        return this;
    }

    @Override
    public DurationFilter setGreaterThanOrEqual(Duration greaterThanOrEqual) {
        super.setGreaterThanOrEqual(greaterThanOrEqual);
        return this;
    }

    @Override
    public DurationFilter setLessThan(Duration lessThan) {
        super.setLessThan(lessThan);
        return this;
    }

    @Override
    public DurationFilter setLessThanOrEqual(Duration lessThanOrEqual) {
        super.setLessThanOrEqual(lessThanOrEqual);
        return this;
    }
}
