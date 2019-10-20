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
