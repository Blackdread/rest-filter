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

import org.blackdread.lib.restfilter.filter.*;

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
@SuppressWarnings({"unused", "WeakerAccess"})
public class CriteriaQueryParamBuilder {

    // Default formatters
    private static final Function<Enum, String> ENUM_FORMATTER = Enum::name;
    private static final Function<String, String> STRING_FORMATTER = aString -> aString;
    private static final Function<Boolean, String> BOOLEAN_FORMATTER = aBoolean -> Boolean.toString(aBoolean);
    private static final Function<BigDecimal, String> BIG_DECIMAL_FORMATTER = BigDecimal::toString;
    private static final Function<Integer, String> INTEGER_FORMATTER = integer -> Integer.toString(integer);
    private static final Function<Long, String> LONG_FORMATTER = aLong -> Long.toString(aLong);
    private static final Function<Short, String> SHORT_FORMATTER = aShort -> Short.toString(aShort);
    private static final Function<Double, String> DOUBLE_FORMATTER = aDouble -> Double.toString(aDouble);
    private static final Function<Float, String> FLOAT_FORMATTER = aFloat -> Float.toString(aFloat);
    private static final Function<Instant, String> INSTANT_FORMATTER = DateTimeFormatter.ISO_INSTANT::format;
    private static final Function<LocalDate, String> LOCAL_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE::format;
    private static final Function<LocalDateTime, String> LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME::format;
    private static final Function<ZonedDateTime, String> ZONED_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME::format;
    private static final Function<Duration, String> DURATION_FORMATTER = Duration::toString;
    private static final Function<UUID, String> UUID_FORMATTER = UUID::toString;

    private boolean matchSubclassForDefaultFilterFormatters = false;

//    private Function<Enum, String> enumFormatter = ENUM_FORMATTER_DEFAULT;
//    private Function<Boolean, String> booleanFormatter = BOOLEAN_FORMATTER;
//    private Function<BigDecimal, String> bigDecimalFormatter = BIG_DECIMAL_FORMATTER;
//    private Function<Double, String> doubleFormatter = DOUBLE_FORMATTER;
//    private Function<Float, String> floatFormatter = FLOAT_FORMATTER;
//    private Function<Instant, String> instantFormatter = INSTANT_FORMATTER;
//    private Function<LocalDate, String> localDateFormatter = LOCAL_DATE_FORMATTER;
//    private Function<LocalDateTime, String> localDateTimeFormatter = LOCAL_DATE_TIME_FORMATTER;
//    private Function<ZonedDateTime, String> zonedDateTimeFormatter = ZONED_DATE_TIME_FORMATTER;
//    private Function<Duration, String> durationFormatter = DURATION_FORMATTER;
//    private Function<UUID, String> uuidFormatter = UUID_FORMATTER;

    private final Map<Class, Function<Object, String>> typeFormatterBySimpleTypeMap = new LinkedHashMap<>(16);
    private final Map<Class<? extends Filter>, Class> typeFormatterByFilterClassMap = new LinkedHashMap<>(16);


    //    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> defaultFilterClassFormatterMap = new LinkedHashMap<>(16);
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap = new HashMap<>(16);

    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder() {
        // Add defaults
        typeFormatterBySimpleTypeMap.put(Long.class, (Function) LONG_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Instant.class, (Function) INSTANT_FORMATTER);
        typeFormatterBySimpleTypeMap.put(String.class, (Function) STRING_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Double.class, (Function) DOUBLE_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Integer.class, (Function) INTEGER_FORMATTER);
        typeFormatterBySimpleTypeMap.put(UUID.class, (Function) UUID_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Short.class, (Function) SHORT_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Float.class, (Function) FLOAT_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Boolean.class, (Function) BOOLEAN_FORMATTER);
        typeFormatterBySimpleTypeMap.put(BigDecimal.class, (Function) BIG_DECIMAL_FORMATTER);
        typeFormatterBySimpleTypeMap.put(LocalDate.class, (Function) LOCAL_DATE_FORMATTER);
        typeFormatterBySimpleTypeMap.put(LocalDateTime.class, (Function) LOCAL_DATE_TIME_FORMATTER);
        typeFormatterBySimpleTypeMap.put(ZonedDateTime.class, (Function) ZONED_DATE_TIME_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Duration.class, (Function) DURATION_FORMATTER);
        typeFormatterBySimpleTypeMap.put(Enum.class, (Function) ENUM_FORMATTER);


        typeFormatterByFilterClassMap.put(LongFilter.class, Long.class);
        typeFormatterByFilterClassMap.put(InstantFilter.class, Instant.class);
        typeFormatterByFilterClassMap.put(StringFilter.class, String.class);
        typeFormatterByFilterClassMap.put(DoubleFilter.class, Double.class);
        typeFormatterByFilterClassMap.put(IntegerFilter.class, Integer.class);
        typeFormatterByFilterClassMap.put(UUIDFilter.class, UUID.class);
        typeFormatterByFilterClassMap.put(ShortFilter.class, Short.class);
        typeFormatterByFilterClassMap.put(FloatFilter.class, Float.class);
        typeFormatterByFilterClassMap.put(BooleanFilter.class, Boolean.class);
        typeFormatterByFilterClassMap.put(BigDecimalFilter.class, BigDecimal.class);
        typeFormatterByFilterClassMap.put(LocalDateFilter.class, LocalDate.class);
//        typeFormatterByFilterClassMap.put(LocalDateTimeFilter.class, LocalDateTime.class);
        typeFormatterByFilterClassMap.put(ZonedDateTimeFilter.class, ZonedDateTime.class);
        typeFormatterByFilterClassMap.put(DurationFilter.class, Duration.class);
//        typeFormatterByFilterClassMap.put(EnumXXXXX.class, Enum.class); -> handled differently in impl

    }

    /**
     *
     * @param matchSubclassForDefaultFilterFormatters whether subclass of a given filter will
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder matchSubclassForDefaultFilterFormatters(final boolean matchSubclassForDefaultFilterFormatters) {
        this.matchSubclassForDefaultFilterFormatters = matchSubclassForDefaultFilterFormatters;
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link T} to query param compatible {@code String}
     * @param <T> type to be formatted to {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public <T> CriteriaQueryParamBuilder withTypeFormatter(final Class<T> tClass, final Function<T, String> formatter) {
        Objects.requireNonNull(formatter);
        this.typeFormatterBySimpleTypeMap.put(tClass, (Function<Object, String>) formatter);
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.lang.Enum} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withEnumFormatter(final Function<Enum, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Enum.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.lang.Boolean} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withBooleanFormatter(final Function<Boolean, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Boolean.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.math.BigDecimal} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withBigDecimalFormatter(final Function<BigDecimal, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(BigDecimal.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.lang.Double} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withDoubleFormatter(final Function<Double, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Double.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.lang.Float} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withFloatFormatter(final Function<Float, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Float.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.time.Instant} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withInstantFormatter(final Function<Instant, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Instant.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.LocalDate} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withLocalDateFormatter(final Function<LocalDate, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(LocalDate.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.LocalDateTime} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withLocalDateTimeFormatter(final Function<LocalDateTime, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(LocalDateTime.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     * In Spring context for web with REST, you might need to register org.springframework.format.Formatter or Converter beans.
     *
     * @param formatter transform {@link java.time.ZonedDateTime} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withZonedDateTimeFormatter(final Function<ZonedDateTime, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(ZonedDateTime.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.time.Duration} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withDurationFormatter(final Function<Duration, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(Duration.class, (Function) Objects.requireNonNull(formatter));
        return this;
    }

    /**
     * Formatter should not have side-effect.
     *
     * @param formatter transform {@link java.util.UUID} to query param compatible {@code String}
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    @SuppressWarnings("unchecked")
    public CriteriaQueryParamBuilder withUUIDFormatter(final Function<UUID, String> formatter) {
        this.typeFormatterBySimpleTypeMap.put(UUID.class, (Function) Objects.requireNonNull(formatter));
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
     * @param filterClass               filter that will match a criteria filter field
     * @param customQueryParamFormatter transform filters to query params
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder putCustomQueryParamFormatter(final Class<? extends Filter> filterClass, final FilterQueryParamFormatter customQueryParamFormatter) {
        Objects.requireNonNull(filterClass);
        Objects.requireNonNull(customQueryParamFormatter);
        this.customQueryParamFormatterMap.put(filterClass, customQueryParamFormatter);
        return this;
    }

    /**
     * Remove a previous added {@link FilterQueryParamFormatter} matching this filter class.
     *
     * @param filterClass filter class to remove
     * @return same {@code CriteriaQueryParamBuilder} instance (for chaining)
     */
    public CriteriaQueryParamBuilder removeCustomQueryParamFormatter(final Class<? extends Filter> filterClass) {
        Objects.requireNonNull(filterClass);
        this.customQueryParamFormatterMap.remove(filterClass);
        return this;
    }

    public CriteriaQueryParam build() {
        return new CriteriaQueryParamImpl(enumFormatter, matchSubclassForDefaultFilterFormatters, booleanFormatter, bigDecimalFormatter, doubleFormatter, floatFormatter, instantFormatter, localDateFormatter, localDateTimeFormatter, zonedDateTimeFormatter, durationFormatter, uuidFormatter, customQueryParamFormatterMap);
    }

}
