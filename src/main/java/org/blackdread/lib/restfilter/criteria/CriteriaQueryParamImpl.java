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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author Yoann CAPLAIN
 */
@Immutable
@ThreadSafe
final class CriteriaQueryParamImpl implements CriteriaQueryParam {

    @FunctionalInterface
    interface CriteriaFieldIgnore {
        boolean isIgnored(Class fieldClass, String fieldName, @Nullable Object fieldValue);
    }

//    interface CriteriaFieldSupport {
//        boolean support(...);
//
//        boolean apply(Class fieldClass, String fieldName, @Nullable Object fieldValue);
//    }

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamImpl.class);

    private final Function<Enum, String> enumFormatter;

//    private final List<FieldIgnore> fieldIgnores; todo
//    private final Map<String, List<>>

    private final boolean matchSubclassForDefaultFilterFormatters;
    /**
     * Default supported filters to be transformed in FilterQueryParam with implementation provided by {@link FilterQueryParamUtil}
     */
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> defaultFilterClassFormatterMap = new HashMap<>(16);
    /**
     * Functions to take an object (Boolean, String, Integer, BigDecimal, etc) as input and return a string as output to be used as param value.
     * Key is object class that function will take as input
     */
    private final Map<Class, Function<Object, String>> simpleTypeFormatterMap = new HashMap<>(16);
    private final Function<Boolean, String> booleanFormatter;
    private final Function<String, String> stringFormatter = string -> string;
    private final Function<Integer, String> integerFormatter = integer -> integer.toString();
    private final Function<Long, String> longFormatter = aLong -> aLong.toString();
    private final Function<Short, String> shortFormatter = aShort -> aShort.toString();
    private final Function<BigDecimal, String> bigDecimalFormatter;
    private final Function<Double, String> doubleFormatter;
    private final Function<Float, String> floatFormatter;
    private final Function<Instant, String> instantFormatter;
    private final Function<LocalDate, String> localDateFormatter;
    private final Function<LocalDateTime, String> localDateTimeFormatter;
    private final Function<ZonedDateTime, String> zonedDateTimeFormatter;
    private final Function<Duration, String> durationFormatter;
    private final Function<UUID, String> uuidFormatter;

    /**
     * Custom formatters from user, short-circuit the logic if filter class is found, user provide its own logic to map a filter to a list of {@link FilterQueryParam}
     */
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap;
    //    private final Map<Class, Function<Object, String>> defaultFormatterMap;
//    private final Map<Class, Function<Filter, MultiValueMap<String, String>>> customFormatterMultiValueMap;
//    private final Map<Class, Function<Object, String>> formatterMap;
//    BiFunction -> fieldName and filter
//    private final Map<Class, BiFunction<String, Filter, List<FilterQueryParam>>> customQueryParamMap;

//    CriteriaQueryParamImpl(final Map<Class, Function<Object, String>> simpleTypeFormatterMap, final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap){
//        this.simpleTypeFormatterMap = Map.copyOf(simpleTypeFormatterMap);
//        this.customQueryParamFormatterMap = Map.copyOf(customQueryParamFormatterMap);
//    }

    CriteriaQueryParamImpl(final Function<Enum, String> enumFormatter, final boolean matchSubclassForDefaultFilterFormatters, final Function<Boolean, String> booleanFormatter, final Function<BigDecimal, String> bigDecimalFormatter, final Function<Double, String> doubleFormatter, final Function<Float, String> floatFormatter, final Function<Instant, String> instantFormatter, final Function<LocalDate, String> localDateFormatter, final Function<LocalDateTime, String> localDateTimeFormatter, final Function<ZonedDateTime, String> zonedDateTimeFormatter, final Function<Duration, String> durationFormatter, final Function<UUID, String> uuidFormatter, @Nullable final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap) {
        this.enumFormatter = Objects.requireNonNull(enumFormatter);
        this.matchSubclassForDefaultFilterFormatters = matchSubclassForDefaultFilterFormatters;

        this.booleanFormatter = Objects.requireNonNull(booleanFormatter);
        this.bigDecimalFormatter = Objects.requireNonNull(bigDecimalFormatter);
        this.doubleFormatter = Objects.requireNonNull(doubleFormatter);
        this.floatFormatter = Objects.requireNonNull(floatFormatter);
        this.instantFormatter = Objects.requireNonNull(instantFormatter);
        this.localDateFormatter = Objects.requireNonNull(localDateFormatter);
        this.localDateTimeFormatter = Objects.requireNonNull(localDateTimeFormatter);
        this.zonedDateTimeFormatter = Objects.requireNonNull(zonedDateTimeFormatter);
        this.durationFormatter = Objects.requireNonNull(durationFormatter);
        this.uuidFormatter = Objects.requireNonNull(uuidFormatter);
        this.customQueryParamFormatterMap = customQueryParamFormatterMap == null ? Map.of() : Map.copyOf(customQueryParamFormatterMap);


        if (!matchSubclassForDefaultFilterFormatters) {
            defaultFilterClassFormatterMap.put(StringFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (StringFilter) filter, stringFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(LongFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (LongFilter) filter, longFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(InstantFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (InstantFilter) filter, instantFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(IntegerFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (IntegerFilter) filter, integerFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(DoubleFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (DoubleFilter) filter, doubleFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(FloatFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (FloatFilter) filter, floatFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(ShortFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (ShortFilter) filter, shortFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(BigDecimalFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (BigDecimalFilter) filter, bigDecimalFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(LocalDateFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (LocalDateFilter) filter, localDateFormatter, booleanFormatter));
//            defaultQueryParamFormatterMap.put(LocalDateTimeFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (LocalDateTimeFilter) filter, localDateTimeFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(ZonedDateTimeFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (ZonedDateTimeFilter) filter, zonedDateTimeFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(DurationFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (DurationFilter) filter, durationFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(BooleanFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (BooleanFilter) filter, booleanFormatter, booleanFormatter));
            defaultFilterClassFormatterMap.put(UUIDFilter.class, (fieldName, filter) -> FilterQueryParamUtil.buildQueryParams(fieldName, (UUIDFilter) filter, uuidFormatter, booleanFormatter));
        }
    }

    private void checkLogic() {
        if (!simpleTypeFormatterMap.containsKey(Boolean.class)) {
            throw new IllegalStateException("Boolean formatter is mandatory");
        }
    }

    @Override
    public List<FilterQueryParam> getFilterQueryParams(final String fieldName, final Filter filter) {
        final Class<? extends Filter> filterClass = filter.getClass();
        if (!customQueryParamFormatterMap.isEmpty() && customQueryParamFormatterMap.containsKey(filterClass)) {
            return customQueryParamFormatterMap.get(filterClass).getFilterQueryParams(fieldName, filter);
        }

        if (matchSubclassForDefaultFilterFormatters) {
            final List<FilterQueryParam> x = getFilterQueryParamsByInstanceOf(fieldName, filter);
            if (x != null) return x;
        } else {
            if (!defaultFilterClassFormatterMap.isEmpty() && defaultFilterClassFormatterMap.containsKey(filterClass)) {
                return defaultFilterClassFormatterMap.get(filterClass).getFilterQueryParams(fieldName, filter);
            }
//            final List<FilterQueryParam> x = getFilterQueryParamsByClass(fieldName, filter);
//            if (x != null) return x;
        }

        final Class genericClass = filter.obtainGenericClass();
        // todo for enum we should find formatter if defined for this specific enum class, if not found then we fallback to default Enum class formatter
        if (genericClass.isEnum()) {
            return buildForEnum(fieldName, filter);
        }

        throw new UnsupportedFilterForQueryParamException(fieldName, filter);
    }

    @SuppressWarnings("unchecked")
    private List<FilterQueryParam> buildForEnum(final String fieldName, final Filter filter) {
        return FilterQueryParamUtil.buildQueryParams(fieldName, filter, enumFormatter);
    }

    private List<FilterQueryParam> getFilterQueryParamsByInstanceOf(final String fieldName, final Filter filter) {
        // todo change to:
//        Optional<Map.Entry<Class<? extends Filter>, FilterQueryParamFormatter>> first = defaultFilterClassFormatterMap.entrySet().stream()
//            .filter(e -> e.getKey().isAssignableFrom(filter.getClass()))
//            .findFirst();
        // then do the actual logic needed


        if (filter instanceof StringFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (StringFilter) filter, stringFormatter, booleanFormatter);
        } else if (filter instanceof LongFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (LongFilter) filter, longFormatter, booleanFormatter);
        } else if (filter instanceof InstantFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (InstantFilter) filter, instantFormatter, booleanFormatter);
        } else if (filter instanceof IntegerFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (IntegerFilter) filter, integerFormatter, booleanFormatter);
        } else if (filter instanceof DoubleFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (DoubleFilter) filter, doubleFormatter, booleanFormatter);
        } else if (filter instanceof FloatFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (FloatFilter) filter, floatFormatter, booleanFormatter);
        } else if (filter instanceof ShortFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (ShortFilter) filter, shortFormatter, booleanFormatter);
        } else if (filter instanceof BigDecimalFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (BigDecimalFilter) filter, bigDecimalFormatter, booleanFormatter);
        } else if (filter instanceof LocalDateFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (LocalDateFilter) filter, localDateFormatter, booleanFormatter);
//        }  else if (filter instanceof LocalDateTimeFilter) {
//            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateTimeFilter) filter, localDateTimeFormatter, booleanFormatter);
        } else if (filter instanceof ZonedDateTimeFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (ZonedDateTimeFilter) filter, zonedDateTimeFormatter, booleanFormatter);
        } else if (filter instanceof DurationFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (DurationFilter) filter, durationFormatter, booleanFormatter);
        } else if (filter instanceof BooleanFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (BooleanFilter) filter, booleanFormatter, booleanFormatter);
        } else if (filter instanceof UUIDFilter) {
            return FilterQueryParamUtil.buildQueryParams(fieldName, (UUIDFilter) filter, uuidFormatter, booleanFormatter);
        }
        // very specific behavior of returning null here for a list return type
        return null;
    }

//    private List<FilterQueryParam> getFilterQueryParamsByClass(final String fieldName, final Filter filter) {
//        Class<? extends Filter> filterClass = filter.getClass();
//        if (StringFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (StringFilter) filter, stringFormatter, booleanFormatter);
//        } else if (LongFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (LongFilter) filter, longFormatter, booleanFormatter);
//        } else if (InstantFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (InstantFilter) filter, instantFormatter, booleanFormatter);
//        } else if (IntegerFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (IntegerFilter) filter, integerFormatter, booleanFormatter);
//        } else if (DoubleFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (DoubleFilter) filter, doubleFormatter, booleanFormatter);
//        } else if (FloatFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (FloatFilter) filter, floatFormatter, booleanFormatter);
//        } else if (ShortFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (ShortFilter) filter, shortFormatter, booleanFormatter);
//        } else if (BigDecimalFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (BigDecimalFilter) filter, bigDecimalFormatter, booleanFormatter);
//        } else if (LocalDateFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (LocalDateFilter) filter, localDateFormatter, booleanFormatter);
////        }  else if (LocalDateTimeFilter.class.equals(filterClass)) {
////            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateTimeFilter) filter, localDateTimeFormatter, booleanFormatter);
//        } else if (ZonedDateTimeFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (ZonedDateTimeFilter) filter, zonedDateTimeFormatter, booleanFormatter);
//        } else if (DurationFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (DurationFilter) filter, durationFormatter, booleanFormatter);
//        } else if (BooleanFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (BooleanFilter) filter, booleanFormatter, booleanFormatter);
//        } else if (UUIDFilter.class.equals(filterClass)) {
//            return FilterQueryParamUtil.buildQueryParams(fieldName, (UUIDFilter) filter, uuidFormatter, booleanFormatter);
//        }
//        // very specific behavior of returning null here for a list return type
//        return null;
//    }
}
