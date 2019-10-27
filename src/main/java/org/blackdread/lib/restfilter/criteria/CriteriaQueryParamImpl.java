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

import org.blackdread.lib.restfilter.criteria.parser.CriteriaData;
import org.blackdread.lib.restfilter.criteria.parser.CriteriaFieldData;
import org.blackdread.lib.restfilter.criteria.parser.CriteriaFieldParserUtil;
import org.blackdread.lib.restfilter.criteria.parser.CriteriaMethodData;
import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

// not sure if useful below
//    @FunctionalInterface
//    interface CriteriaFieldIgnore {
//        boolean isIgnored(Class fieldClass, String fieldName, @Nullable Object fieldValue);
//    }

// not sure if useful below
//    interface CriteriaFieldSupport {
//        boolean support(...);
//
//        boolean apply(Class fieldClass, String fieldName, @Nullable Object fieldValue);
//    }

    private static final Logger log = LoggerFactory.getLogger(CriteriaQueryParamImpl.class);

//    private final List<FieldIgnore> fieldIgnores; todo
//    private final Map<String, List<>>

    private final boolean matchSubclassForDefaultFilterFormatters;
    /**
     * Default supported filters to be transformed in FilterQueryParam with implementation provided by {@link FilterQueryParamUtil}
     */
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> defaultFilterClassFormatterMap = new LinkedHashMap<>(16);
    /**
     * Functions to take an object (Boolean, String, Integer, BigDecimal, etc) as input and return a string as output to be used as param value.
     * Key is object class that function will take as input (Boolean, String, Integer, BigDecimal, etc)
     */
    private final Map<Class, Function<Object, String>> typeFormatterBySimpleTypeMap = new LinkedHashMap<>(16);

    private final Function<Enum, String> enumFormatter;
    private final Function<Boolean, String> booleanFormatter;
//    private final Function<String, String> stringFormatter = string -> string;
//    private final Function<Integer, String> integerFormatter = integer -> integer.toString();
//    private final Function<Long, String> longFormatter = aLong -> aLong.toString();
//    private final Function<Short, String> shortFormatter = aShort -> aShort.toString();
//    private final Function<BigDecimal, String> bigDecimalFormatter;
//    private final Function<Double, String> doubleFormatter;
//    private final Function<Float, String> floatFormatter;
//    private final Function<Instant, String> instantFormatter;
//    private final Function<LocalDate, String> localDateFormatter;
//    private final Function<LocalDateTime, String> localDateTimeFormatter;
//    private final Function<ZonedDateTime, String> zonedDateTimeFormatter;
//    private final Function<Duration, String> durationFormatter;
//    private final Function<UUID, String> uuidFormatter;

    /**
     * Custom formatters from user, short-circuit the logic if filter class is found, user provide its own logic to map a filter to a list of {@link FilterQueryParam}
     */
    private final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap;
    //    private final Map<Class, Function<Object, String>> defaultFormatterMap;
//    private final Map<Class, Function<Filter, MultiValueMap<String, String>>> customFormatterMultiValueMap;
//    private final Map<Class, Function<Object, String>> formatterMap;
//    BiFunction -> fieldName and filter
//    private final Map<Class, BiFunction<String, Filter, List<FilterQueryParam>>> customQueryParamMap;

    CriteriaQueryParamImpl(final boolean matchSubclassForDefaultFilterFormatters,
                           final Map<Class, Function<Object, String>> simpleTypeFormatterMap,
                           final Map<Class<? extends Filter>, Class> typeFormatterByFilterClassMap,
                           final Map<Class<? extends Filter>, FilterQueryParamFormatter> customQueryParamFormatterMap) {
        checkBasicFormatterArePresent(simpleTypeFormatterMap);

        this.matchSubclassForDefaultFilterFormatters = matchSubclassForDefaultFilterFormatters;
        this.typeFormatterBySimpleTypeMap.putAll(simpleTypeFormatterMap);
//        this.typeFormatterByFilterClassMap = new LinkedHashMap<>(typeFormatterByFilterClassMap);
        this.customQueryParamFormatterMap = new HashMap<>(customQueryParamFormatterMap);

        this.enumFormatter = checkAndGet(Enum.class, simpleTypeFormatterMap);
        this.booleanFormatter = checkAndGet(Boolean.class, simpleTypeFormatterMap);

        typeFormatterByFilterClassMap.forEach((filterClass, formatterTypeClass) -> {
            // eagerly get the formatter then pass the reference to the lambdas
            final Function<Object, String> objectToStringFormatter = simpleTypeFormatterMap.get(formatterTypeClass);
            if (StringFilter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass,
                    (fieldName, filter) ->
                        FilterQueryParamUtil.buildStringFilterQueryParams(fieldName, (StringFilter) filter, objectToStringFormatter, booleanFormatter)
                );
            } else if (RangeFilter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass,
                    (fieldName, filter) ->
                        FilterQueryParamUtil.buildRangeFilterQueryParams(fieldName, (RangeFilter) filter, objectToStringFormatter, booleanFormatter)
                );
            } else if (Filter.class.isAssignableFrom(filterClass)) {
                defaultFilterClassFormatterMap.put(filterClass,
                    (fieldName, filter) ->
                        FilterQueryParamUtil.buildFilterQueryParams(fieldName, filter, objectToStringFormatter, booleanFormatter)
                );
            } else {
                throw new IllegalStateException("Unsupported filter class: " + filterClass.getName());
            }
        });

        log.debug("typeFormatterBySimpleTypeMap keys: {}", this.typeFormatterBySimpleTypeMap.keySet());
        log.debug("typeFormatterByFilterClassMap: {}", typeFormatterByFilterClassMap);
        log.debug("customQueryParamFormatterMap: {}", this.customQueryParamFormatterMap);
    }

    private static Function checkAndGet(final Class typeFormatter, final Map<Class, Function<Object, String>> simpleTypeFormatterMap) {
        return Objects.requireNonNull(simpleTypeFormatterMap.get(typeFormatter), () -> "Formatter is mandatory for type: " + typeFormatter.getName());
    }

    private static void checkBasicFormatterArePresent(final Map<Class, Function<Object, String>> simpleTypeFormatterMap) {
        Objects.requireNonNull(simpleTypeFormatterMap.get(Boolean.class), () -> "Formatter is mandatory for type: " + Boolean.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Integer.class), () -> "Formatter is mandatory for type: " + Integer.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Long.class), () -> "Formatter is mandatory for type: " + Long.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(BigDecimal.class), () -> "Formatter is mandatory for type: " + BigDecimal.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Double.class), () -> "Formatter is mandatory for type: " + Double.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Float.class), () -> "Formatter is mandatory for type: " + Float.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Duration.class), () -> "Formatter is mandatory for type: " + Duration.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Instant.class), () -> "Formatter is mandatory for type: " + Instant.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(LocalDate.class), () -> "Formatter is mandatory for type: " + LocalDate.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(Short.class), () -> "Formatter is mandatory for type: " + Short.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(String.class), () -> "Formatter is mandatory for type: " + String.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(UUID.class), () -> "Formatter is mandatory for type: " + UUID.class.getName());
        Objects.requireNonNull(simpleTypeFormatterMap.get(ZonedDateTime.class), () -> "Formatter is mandatory for type: " + ZonedDateTime.class.getName());
    }

    @Override
    public List<QueryParam> buildQueryParams(final Object criteria) {
        if (criteria == null) {
            return Collections.emptyList();
        }
        final CriteriaData criteriaData = CriteriaFieldParserUtil.getCriteriaData(criteria);
        final List<CriteriaFieldData> fields = criteriaData.getFields();
        final List<CriteriaMethodData> methods = criteriaData.getMethods();

        final List<QueryParam> result = new ArrayList<>(fields.size() + methods.size());

        for (final CriteriaFieldData fieldData : fields) {
            final String paramName = fieldData.getFormattedAliasName()
                .orElse(fieldData.getFieldName());
            final Object fieldValue;
            try {
                fieldValue = fieldData.getField().get(criteria);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not access field", e);
            }

            if (fieldValue == null) {
                log.trace("Skipped field as is null: {}", fieldData.getField());
                continue;
            }

            if (fieldData.isFilter()) {
                result.addAll(getFilterQueryParams(paramName, (Filter) fieldValue));
            } else if (fieldData.isValue()) {

            } else if (fieldData.isIterable()) {

            } else if (fieldData.isArray()) {

            } else {
                throw new IllegalStateException("Does not support field data: " + fieldData);
            }
        }

        for (final CriteriaMethodData methodData : methods) {
            if (methodData.isFilter()) {

            }
        }

        return result;
    }

    @Override
    public List<FilterQueryParam> getFilterQueryParams(final String paramName, final Filter filter) {
        final Class<? extends Filter> filterClass = filter.getClass();
        if (!customQueryParamFormatterMap.isEmpty() && customQueryParamFormatterMap.containsKey(filterClass)) {
            return customQueryParamFormatterMap.get(filterClass).getFilterQueryParams(paramName, filter);
        }

        if (matchSubclassForDefaultFilterFormatters) {
            final List<FilterQueryParam> x = getFilterQueryParamsWithSubclass(paramName, filter);
            if (x != null) return x;
        } else {
            if (!defaultFilterClassFormatterMap.isEmpty() && defaultFilterClassFormatterMap.containsKey(filterClass)) {
                return defaultFilterClassFormatterMap.get(filterClass).getFilterQueryParams(paramName, filter);
            }
        }

        final Class genericClass = filter.obtainGenericClass();
        if (genericClass.isEnum()) {
            if (typeFormatterBySimpleTypeMap.containsKey(genericClass)) {
                return buildForEnum(paramName, filter, typeFormatterBySimpleTypeMap.get(genericClass));
            }
            return buildForEnum(paramName, filter);
        }

        // todo do we try to find 'simpleTypeFormatterMap.containsKey(genericClass)' when not an enum ? Then try to apply a default formatter when we find the formatter for the genericClass
        // well no in theory since it should have been found by getFilterQueryParamsWithSubclass/defaultFilterClassFormatterMap/customQueryParamFormatterMap already
        log.info("Filter {} could not be formatted, generic class is '{}' and available formatter is '{}'", filter.getClass().getName(), genericClass.getName(), typeFormatterBySimpleTypeMap.containsKey(genericClass));

        throw new UnsupportedFilterForQueryParamException(paramName, filter);
    }

    @SuppressWarnings("unchecked")
    private List<FilterQueryParam> buildForEnum(final String paramName, final Filter filter) {
        return FilterQueryParamUtil.buildQueryParams(paramName, filter, enumFormatter);
    }

    @SuppressWarnings("unchecked")
    private List<FilterQueryParam> buildForEnum(final String paramName, final Filter filter, final Function enumFormatter) {
        return FilterQueryParamUtil.buildQueryParams(paramName, filter, enumFormatter);
    }

    private List<FilterQueryParam> getFilterQueryParamsWithSubclass(final String fieldName, final Filter filter) {
        final Class<? extends Filter> filterClass = filter.getClass();
        final List<Map.Entry<Class<? extends Filter>, FilterQueryParamFormatter>> formatters = defaultFilterClassFormatterMap.entrySet().stream()
            .filter(e -> e.getKey().isAssignableFrom(filterClass))
            .limit(2)
            .collect(Collectors.toList());
        // todo should we throw if more than one match? -> maybe not as we could define specific filters then at end of list the default generic one (Map is ordered by insertion)

        if (formatters.size() > 1) {
            log.warn("At least two formatters for filter '{}' were found, first one will be used: {}", filterClass, formatters);
        }

        if (formatters.isEmpty()) {
            // very specific behavior of returning null here for a list return type
            return null;
        }

        return formatters.get(0)
            .getValue()
            .getFilterQueryParams(fieldName, filter);
    }

}
