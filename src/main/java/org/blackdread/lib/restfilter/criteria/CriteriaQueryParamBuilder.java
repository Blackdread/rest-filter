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

import org.blackdread.lib.restfilter.filter.Filter;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@NotThreadSafe
public class CriteriaQueryParamBuilder {

    private static final Function<Enum, String> ENUM_FORMATTER_DEFAULT = Enum::name;
    private static final Function<Boolean, String> BOOLEAN_FORMATTER = Object::toString;
    private static final Function<BigDecimal, String> BIG_DECIMAL_FORMATTER = BigDecimal::toString;
    private static final Function<Double, String> DOUBLE_FORMATTER = Object::toString;
    private static final Function<Float, String> FLOAT_FORMATTER = Object::toString;
    private static final Function<Instant, String> INSTANT_FORMATTER = DateTimeFormatter.ISO_INSTANT::format;
    private static final Function<LocalDate, String> LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE::format;
    private static final Function<LocalDateTime, String> LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME::format;
    private static final Function<ZonedDateTime, String> ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME::format;
    private static final Function<Duration, String> DURATION_FORMATTER = Duration::toString;
    private static final Function<UUID, String> UUID_FORMATTER = UUID::toString;

    private boolean matchSubclassForDefaultFilterFormatters = false;

    private Function<Enum, String> enumFormatter = ENUM_FORMATTER_DEFAULT;
    private Function<Boolean, String> booleanFormatter = BOOLEAN_FORMATTER;
    private Function<BigDecimal, String> bigDecimalFormatter = BIG_DECIMAL_FORMATTER;
    private Function<Double, String> doubleFormatter = DOUBLE_FORMATTER;
    private Function<Float, String> floatFormatter = FLOAT_FORMATTER;
    private Function<Instant, String> instantFormatter = INSTANT_FORMATTER;
    private Function<LocalDate, String> localDateFormatter = LOCAL_DATE_FORMATTER;
    private Function<LocalDateTime, String> localDateTimeFormatter = LOCAL_DATE_TIME_FORMATTER;
    private Function<ZonedDateTime, String> zonedDateTimeFormatter = ZONED_DATE_TIME_FORMATTER;
    private Function<Duration, String> durationFormatter = DURATION_FORMATTER;
    private Function<UUID, String> uuidFormatter = UUID_FORMATTER;

    private final Map<Class, Function<Object, String>> simpleTypeFormatterMap = new HashMap<>(16);
    private final Map<Class<? extends Filter>, Class> simpleTypeFormatterByFilterClassMap = new HashMap<>(16);



    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> defaultFilterClassFormatterMap = new LinkedHashMap<>(16);
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap = new HashMap<>(16);

    public CriteriaQueryParamBuilder() {
        simpleTypeFormatterMap.put(Enum.class, o -> ((Enum) o).name());
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.lang.Enum} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public <T> CriteriaQueryParamBuilder withTypeFormatter(final Class<T> tClass, final Function<T, String> formatter) {
        Objects.requireNonNull(formatter);
        simpleTypeFormatterMap.put(tClass, (Function<Object, String>) formatter);
        return this;
    }

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

    /**
     * @param formatter transform {@link java.util.UUID} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withUUIDFormatter(final Function<UUID, String> formatter) {
        this.uuidFormatter = Objects.requireNonNull(formatter);
        return this;
    }

    /**
     * If a filter class is matched (exact class), the matching value {@link FilterQueryParamFormatter} will be called when calling methods in {@link CriteriaQueryParam}.
     * <br>
     * Map passed replaces current builder map.
     *
     * @param customQueryParamFormatterMap transform filters to query params
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder withCustomQueryParamFormatterMap(final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap) {
        this.customQueryParamFormatterMap.clear();
        if (!customQueryParamFormatterMap.isEmpty()) {
            this.customQueryParamFormatterMap.putAll(customQueryParamFormatterMap);
        }
        return this;
    }

    /**
     * If a filter class is matched, the matching value {@link FilterQueryParamFormatter} will be called when calling methods in {@link CriteriaQueryParam}.
     * <br>
     * If {@code filterClass} is already contained in map, replaces it.
     *
     * @param customQueryParamFormatter transform filters to query params
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder putCustomQueryParamFormatter(final Class<? extends Filter> filterClass, final FilterQueryParamFormatter customQueryParamFormatter) {
        Objects.requireNonNull(filterClass);
        Objects.requireNonNull(customQueryParamFormatter);
        this.customQueryParamFormatterMap.put(filterClass, customQueryParamFormatter);
        return this;
    }

    public CriteriaQueryParamBuilder removeCustomQueryParamFormatter(final Class<? extends Filter> filterClass) {
        Objects.requireNonNull(filterClass);
        this.customQueryParamFormatterMap.remove(filterClass);
        return this;
    }

    public CriteriaQueryParam build() {
        return new CriteriaQueryParamImpl(enumFormatter, matchSubclassForDefaultFilterFormatters, booleanFormatter, bigDecimalFormatter, doubleFormatter, floatFormatter, instantFormatter, localDateFormatter, localDateTimeFormatter, zonedDateTimeFormatter, durationFormatter, uuidFormatter, customQueryParamFormatterMap);
    }

}
