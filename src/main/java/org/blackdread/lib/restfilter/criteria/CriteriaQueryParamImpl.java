package org.blackdread.lib.restfilter.criteria;

import org.blackdread.lib.restfilter.filter.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriBuilder;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    private static String buildParam(final String fieldName, final String filterType) {
        return fieldName + FIELD_NAME_AND_FILTER_SEPARATOR + filterType;
    }

    @Override
    public UriBuilder buildQueryParams(final Object criteria, UriBuilder builder) {
        final Map<String, Filter> filtersByFieldName = CriteriaFieldParserUtil.build(criteria);
        final List<FilterQueryParam> filterQueryParams = getFilterQueryParams(filtersByFieldName);
        for (final FilterQueryParam filterQueryParam : filterQueryParams) {
            builder = builder.queryParam(filterQueryParam.getParamName(), filterQueryParam.getParamValues());
        }
        return builder;
    }

    @Override
    public MultiValueMap<String, String> buildQueryParams(final String fieldName, final Filter filter) {
        final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        getFilterQueryParams(fieldName, filter)
            .forEach(filterQueryParam -> map.addAll(filterQueryParam.getParamName(), filterQueryParam.getParamValues()));
        return map;
    }

    @Override
    public UriBuilder buildQueryParams(final String fieldName, final Filter filter, UriBuilder builder) {
        final List<FilterQueryParam> filterQueryParams = getFilterQueryParams(fieldName, filter);
        for (final FilterQueryParam filterQueryParam : filterQueryParams) {
            builder = builder.queryParam(filterQueryParam.getParamName(), filterQueryParam.getParamValues());
        }
        return builder;
    }

    private List<FilterQueryParam> getFilterQueryParams(final Map<String, Filter> filtersByFieldName) {
        return filtersByFieldName.entrySet().stream()
            .flatMap(e -> getFilterQueryParams(e.getKey(), e.getValue()).stream())
            .collect(Collectors.toList());
    }

    private List<FilterQueryParam> getFilterQueryParams(final String fieldName, final Filter filter) {
//        if(customQueryParamMap.isNotEmpty() &&){
//
//        }
        // todo handle enums
        if (filter instanceof StringFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (StringFilter) filter);
        } else if (filter instanceof LongFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (LongFilter) filter, longFormatter);
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
        } else if (filter instanceof InstantFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (InstantFilter) filter, instantFormatter);
        } else if (filter instanceof LocalDateFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateFilter) filter, localDateFormatter);
//        }  else if (filter instanceof LocalDateTimeFilter) {
//            return QueryParamUtil.buildQueryParams(fieldName, (LocalDateTimeFilter) filter, localDateTimeFormatter);
        } else if (filter instanceof ZonedDateTimeFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (ZonedDateTimeFilter) filter, zonedDateTimeFormatter);
        } else if (filter instanceof BooleanFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (BooleanFilter) filter, booleanFormatter);
        } else if (filter instanceof UUIDFilter) {
            return QueryParamUtil.buildQueryParams(fieldName, (UUIDFilter) filter, uuidFormatter);
        } else {
            throw new IllegalStateException("TODO, custom mappers, default one, etc");
        }
    }
}
