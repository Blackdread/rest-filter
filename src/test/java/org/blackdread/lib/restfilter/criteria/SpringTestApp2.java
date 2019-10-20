package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * <p>Created on 2019/07/09.</p>
 *
 * @author Yoann CAPLAIN
 */
@SpringBootApplication
public class SpringTestApp2 {

    private static final Logger log = LoggerFactory.getLogger(SpringTestApp2.class);

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    public Formatter<ZonedDateTime> zonedDateTimeFormatter() {
        return new Formatter<ZonedDateTime>() {
            @Override
            public ZonedDateTime parse(String text, Locale locale) {
                DateTimeParseException firstException = null;
                try {
                    return ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME);
                } catch (DateTimeParseException e) {
                    firstException = e;
                }
                try {
                    return DateTimeFormatter.ISO_LOCAL_DATE.parse(text, LocalDate::from).atStartOfDay(ZoneOffset.UTC);
                } catch (DateTimeParseException ignore) {
                }
                try {
                    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(text, LocalDateTime::from).atZone(ZoneOffset.UTC);
                } catch (DateTimeParseException ignore) {
                }
                try {
                    return DateTimeFormatter.ISO_INSTANT.parse(text, Instant::from).atZone(ZoneOffset.UTC);
                } catch (DateTimeParseException ignore) {
                }
                throw firstException;
            }

            @Override
            public String print(ZonedDateTime object, Locale locale) {
                return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(object);
            }
        };
    }

    public static void main(String[] args) {

    }
}
