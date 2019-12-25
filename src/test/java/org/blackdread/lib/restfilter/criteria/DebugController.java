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
package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.DurationFilter;
import org.blackdread.lib.restfilter.filter.InstantFilter;
import org.blackdread.lib.restfilter.filter.LocalDateFilter;
import org.blackdread.lib.restfilter.filter.ZonedDateTimeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
@RestController
public class DebugController {

    private static final Logger log = LoggerFactory.getLogger(DebugController.class);

    @GetMapping("/api/test")
    public MyDTO test(MyDTO dto) {
        return dto;
    }

    public static class MyDTO {
        private InstantFilter instantFilter;
        private LocalDateFilter localDateFilter;
        private ZonedDateTimeFilter zonedDateTimeFilter;
        private DurationFilter durationFilter;

        public InstantFilter getInstantFilter() {
            return instantFilter;
        }

        public void setInstantFilter(final InstantFilter instantFilter) {
            this.instantFilter = instantFilter;
        }

        public LocalDateFilter getLocalDateFilter() {
            return localDateFilter;
        }

        public void setLocalDateFilter(final LocalDateFilter localDateFilter) {
            this.localDateFilter = localDateFilter;
        }

        public ZonedDateTimeFilter getZonedDateTimeFilter() {
            return zonedDateTimeFilter;
        }

        public void setZonedDateTimeFilter(final ZonedDateTimeFilter zonedDateTimeFilter) {
            this.zonedDateTimeFilter = zonedDateTimeFilter;
        }

        public DurationFilter getDurationFilter() {
            return durationFilter;
        }

        public void setDurationFilter(final DurationFilter durationFilter) {
            this.durationFilter = durationFilter;
        }
    }
}
