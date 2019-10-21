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

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
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

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamImpl.class);

    private final Function<Enum, String> enumFormatter;
    private final Function<Boolean, String> booleanFormatter;
    private final Function<Integer, String> intFormatter = integer -> integer.toString();
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
    private final Function<UUID, String> uuidFormatter = UUID::toString;
//    private final Map<Class, Function<Object, String>> defaultFormatterMap;
//    private final Map<Class, Function<Filter, MultiValueMap<String, String>>> customFormatterMultiValueMap;
//    private final Map<Class, Function<Filter, UriBuilder>> customFormatterUriBuilderMap;
//    private final Map<Class, Function<Object, String>> formatterMap;
//    BiFunction -> fieldName and filter
//    private final Map<Class, BiFunction<String, Filter, List<FilterQueryParam>>> customQueryParamMap;

    CriteriaQueryParamImpl(final Function<Enum, String> enumFormatter, final Function<Boolean, String> booleanFormatter, final Function<BigDecimal, String> bigDecimalFormatter, final Function<Double, String> doubleFormatter, final Function<Float, String> floatFormatter, final Function<Instant, String> instantFormatter, final Function<LocalDate, String> localDateFormatter, final Function<LocalDateTime, String> localDateTimeFormatter, final Function<ZonedDateTime, String> zonedDateTimeFormatter, final Function<Duration, String> durationFormatter) {
        this.enumFormatter = Objects.requireNonNull(enumFormatter);
        this.booleanFormatter = Objects.requireNonNull(booleanFormatter);
        this.bigDecimalFormatter = Objects.requireNonNull(bigDecimalFormatter);
        this.doubleFormatter = Objects.requireNonNull(doubleFormatter);
        this.floatFormatter = Objects.requireNonNull(floatFormatter);
        this.instantFormatter = Objects.requireNonNull(instantFormatter);
        this.localDateFormatter = Objects.requireNonNull(localDateFormatter);
        this.localDateTimeFormatter = Objects.requireNonNull(localDateTimeFormatter);
        this.zonedDateTimeFormatter = Objects.requireNonNull(zonedDateTimeFormatter);
        this.durationFormatter = Objects.requireNonNull(durationFormatter);
    }

    @Override
    public List<FilterQueryParam> getFilterQueryParams(final String fieldName, final Filter filter) {
//        if(customQueryParamMap.isNotEmpty() &&){
//
//        }
        // todo handle enums
        if (filter instanceof StringFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (StringFilter) filter);
        } else if (filter instanceof LongFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (LongFilter) filter, longFormatter);
        } else if (filter instanceof InstantFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (InstantFilter) filter, instantFormatter);
        } else if (filter instanceof IntegerFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (IntegerFilter) filter, intFormatter);
        } else if (filter instanceof DoubleFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (DoubleFilter) filter, doubleFormatter);
        } else if (filter instanceof FloatFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (FloatFilter) filter, floatFormatter);
        } else if (filter instanceof ShortFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (ShortFilter) filter, shortFormatter);
        } else if (filter instanceof BigDecimalFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (BigDecimalFilter) filter, bigDecimalFormatter);
        } else if (filter instanceof LocalDateFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateFilter) filter, localDateFormatter);
//        }  else if (filter instanceof LocalDateTimeFilter) {
//            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateTimeFilter) filter, localDateTimeFormatter);
        } else if (filter instanceof ZonedDateTimeFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (ZonedDateTimeFilter) filter, zonedDateTimeFormatter);
        }  else if (filter instanceof DurationFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (DurationFilter) filter, durationFormatter);
        } else if (filter instanceof BooleanFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (BooleanFilter) filter, booleanFormatter);
        } else if (filter instanceof UUIDFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (UUIDFilter) filter, uuidFormatter);
        } else {
            throw new IllegalStateException("TODO, custom mappers, default one, etc");
        }
    }
}
