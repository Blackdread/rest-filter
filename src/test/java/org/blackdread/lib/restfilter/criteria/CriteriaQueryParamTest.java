package org.blackdread.lib.restfilter.criteria;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.function.Function;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaQueryParamTest {

    private final Instant instant = Instant.parse("2019-10-19T15:50:10Z");
    private final LocalDate localDate = LocalDate.parse("2019-10-19");
    private final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("Europe/Paris"));
    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]");
    private final Duration duration = Duration.parse("P2DT20.345S");

    private final Function<Instant, String> instantFormatter = DateTimeFormatter.ISO_INSTANT::format;
    private final Function<LocalDate, String> localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE::format;
    private final Function<LocalDateTime, String> localDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME::format;
    private final Function<ZonedDateTime, String> zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME::format;
    private final Function<Duration, String> durationFormatter = Duration::toString;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void toInstant() {
        Assertions.assertEquals("2019-10-19T15:50:10Z", instantFormatter.apply(instant));
        Assertions.assertEquals("2019-10-19", localDateFormatter.apply(localDate));
        Assertions.assertEquals("2019-10-19T17:50:10", localDateTimeFormatter.apply(localDateTime));
        Assertions.assertEquals("2007-12-03T10:15:30+01:00[Europe/Paris]", zonedDateTimeFormatter.apply(zonedDateTime));
        Assertions.assertEquals("PT48H20.345S", durationFormatter.apply(duration));

        Assertions.assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDate));
        Assertions.assertThrows(UnsupportedTemporalTypeException.class, () -> DateTimeFormatter.ISO_INSTANT.format(localDateTime));
        Assertions.assertEquals("2007-12-03T09:15:30Z", DateTimeFormatter.ISO_INSTANT.format(zonedDateTime));
    }
}
