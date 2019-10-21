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
 */
package org.blackdread.lib.restfilter.criteria;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Function;

@NotThreadSafe
public class CriteriaQueryParamBuilder {

    private Function<Enum, String> enumFormatter = Enum::name;
    private Function<Boolean, String> booleanFormatter = Object::toString;
    private Function<BigDecimal, String> bigDecimalFormatter = BigDecimal::toString;
    private Function<Double, String> doubleFormatter = Object::toString;
    private Function<Float, String> floatFormatter = Object::toString;
    private Function<Instant, String> instantFormatter = DateTimeFormatter.ISO_INSTANT::format;
    private Function<LocalDate, String> localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE::format;
    private Function<LocalDateTime, String> localDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME::format;
    private Function<ZonedDateTime, String> zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME::format;
    private Function<Duration, String> durationFormatter = Duration::toString;

    /**
     * @param formatter transform {@link java.lang.Enum} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withEnumFormatter(final Function<Enum, String> formatter) {
        this.enumFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.lang.Boolean} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withBooleanFormatter(final Function<Boolean, String> formatter) {
        this.booleanFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.math.BigDecimal} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withBigDecimalFormatter(final Function<BigDecimal, String> formatter) {
        this.bigDecimalFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.lang.Double} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withDoubleFormatter(final Function<Double, String> formatter) {
        this.doubleFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.lang.Float} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withFloatFormatter(final Function<Float, String> formatter) {
        this.floatFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.time.Instant} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withInstantFormatter(final Function<Instant, String> formatter) {
        this.instantFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.LocalDate} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withLocalDateFormatter(final Function<LocalDate, String> formatter) {
        this.localDateFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.LocalDateTime} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withLocalDateTimeFormatter(final Function<LocalDateTime, String> formatter) {
        this.localDateTimeFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.ZonedDateTime} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withZonedDateTimeFormatter(final Function<ZonedDateTime, String> formatter) {
        this.zonedDateTimeFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * @param formatter transform {@link java.time.Duration} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withDurationFormatter(final Function<Duration, String> formatter) {
        this.durationFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    public CriteriaQueryParam build() {
        return new CriteriaQueryParamImpl(enumFormatter, booleanFormatter, bigDecimalFormatter, doubleFormatter, floatFormatter, instantFormatter, localDateFormatter, localDateTimeFormatter, zonedDateTimeFormatter, durationFormatter);
    }

}
