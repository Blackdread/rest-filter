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
