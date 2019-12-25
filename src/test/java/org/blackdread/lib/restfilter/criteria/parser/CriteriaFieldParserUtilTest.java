/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
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
 */
package org.blackdread.lib.restfilter.criteria.parser;

import org.apache.commons.lang3.time.StopWatch;
import org.assertj.core.util.Lists;
import org.blackdread.lib.restfilter.criteria.CriteriaUtil;
import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaFieldParserUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void buildFromSameCriteriaClassShouldBeLotFasterThanFirstTime() {
        final MyCriteria criteria = new MyCriteria();

        criteria.setMessage(CriteriaUtil.buildEqualsCriteria("Should be equals"));
        criteria.setId(CriteriaUtil.buildInCriteria(Lists.newArrayList(1L, 5L)));
        criteria.setWrittenByUserId(CriteriaUtil.buildGreaterThanCriteria(5L));
        criteria.setWrittenByUserId(CriteriaUtil.buildLessThanOrEqualCriteria(criteria.getWrittenByUserId(), 10L));


        final StopWatch stopwatch = StopWatch.createStarted();
        final Map<String, Filter> filters = CriteriaFieldParserUtil.build(criteria);
        stopwatch.stop();
        final long timeSpentFirstTimeNanos = stopwatch.getNanoTime();

        criteria.setMessage(CriteriaUtil.buildEqualsCriteria("Second times"));
        criteria.setId(CriteriaUtil.buildInCriteria(Lists.newArrayList(2L, 7L)));
        criteria.setWrittenByUserId(CriteriaUtil.buildGreaterThanCriteria(6L));
        criteria.setWrittenByUserId(CriteriaUtil.buildLessThanOrEqualCriteria(criteria.getWrittenByUserId(), 11L));
        stopwatch.reset();
        stopwatch.start();
        final Map<String, Filter> filters2 = CriteriaFieldParserUtil.build(criteria);
        stopwatch.stop();
        final long timeSpentSecondTimeNanos = stopwatch.getNanoTime();
        final int TIME_FASTER = 5;
        Assertions.assertTrue(() -> timeSpentFirstTimeNanos > timeSpentSecondTimeNanos * TIME_FASTER, () -> "First time should be around " + TIME_FASTER + " times longer than second time (1st: " + timeSpentFirstTimeNanos + " nanos, 2nd: " + timeSpentSecondTimeNanos + " nanos)");
        Assertions.assertTrue(() -> filters.size() == filters2.size());
        Assertions.assertTrue(() -> filters.keySet().containsAll(filters2.keySet()));

        stopwatch.reset();
        stopwatch.start();
        final Map<String, Filter> filters3 = CriteriaFieldParserUtil.build(criteria);
        stopwatch.stop();
        final long timeSpentThirdTimeNanos = stopwatch.getNanoTime();
        final int DELTA_NANOS = 100000; // 0.1 ms
        Assertions.assertTrue(() -> Math.abs(timeSpentSecondTimeNanos - timeSpentThirdTimeNanos) < DELTA_NANOS, () -> "Second time should have a delta difference of " + DELTA_NANOS + " from third time (2nd: " + timeSpentSecondTimeNanos + " nanos, 3rd: " + timeSpentThirdTimeNanos + " nanos, real delta=" + Math.abs(timeSpentSecondTimeNanos - timeSpentThirdTimeNanos) + ")");
        Assertions.assertTrue(() -> filters2.size() == filters3.size());
        Assertions.assertTrue(() -> filters2.keySet().containsAll(filters3.keySet()));
    }

    @Test
    void emptyMapWhenAllNull() {
        final MyCriteria2 criteria = new MyCriteria2();

        final Map<String, Filter> filterMap = CriteriaFieldParserUtil.build(criteria);

        Map<String, Filter> expected = new HashMap<>();
        Assertions.assertEquals(expected, filterMap);
    }

    @Test
    void notNullFiltersAreFound() {
        final MyCriteria2 criteria = new MyCriteria2();
        criteria.setId2(new LongFilter());
        criteria.setMessage(new StringFilter());

        final Map<String, Filter> filterMap = CriteriaFieldParserUtil.build(criteria);

        Map<String, Filter> expected = new HashMap<>();
        expected.put("id2", criteria.getId2());
        expected.put("message", criteria.getMessage());
        Assertions.assertEquals(expected, filterMap);
    }


    private static class MyCriteria {

        private LongFilter id;
        private StringFilter message;
        private LongFilter writtenByUserId;

        public LongFilter getId() {
            return id;
        }

        public void setId(final LongFilter id) {
            this.id = id;
        }

        public StringFilter getMessage() {
            return message;
        }

        public void setMessage(final StringFilter message) {
            this.message = message;
        }

        public LongFilter getWrittenByUserId() {
            return writtenByUserId;
        }

        public void setWrittenByUserId(final LongFilter writtenByUserId) {
            this.writtenByUserId = writtenByUserId;
        }
    }


    private static class MyCriteria2 extends MyCriteria {

        private LongFilter id2;

        public LongFilter getId2() {
            return id2;
        }

        public void setId2(final LongFilter id2) {
            this.id2 = id2;
        }
    }
}

