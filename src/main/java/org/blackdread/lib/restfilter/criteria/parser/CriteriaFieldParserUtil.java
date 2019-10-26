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
package org.blackdread.lib.restfilter.criteria.parser;


import org.apache.commons.lang3.StringUtils;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;
import org.blackdread.lib.restfilter.filter.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Created on 2019/10/19.</p>
 *
 * @author yoann CAPLAIN
 */
public final class CriteriaFieldParserUtil {

    private static final Logger log = LoggerFactory.getLogger(CriteriaFieldParserUtil.class);

    // Reflection is expensive
    private static final Map<Class, List<Field>> cachedFilterFieldsOfClass = new ConcurrentHashMap<>();

    private static final Map<Class, CriteriaData> cachedCriteriaDataByClass = new ConcurrentHashMap<>();

    /**
     * Key of map is field name.
     * Fields with value null are skipped
     *
     * @param criteria A criteria object used for filtering
     * @return All fields (and inherited) of criteria class, fields (not null) that extends {@link Filter}.
     * @deprecated only provide Filter fields, need more
     */
    @Deprecated
    public static Map<String, Filter> build(@Nullable final Object criteria) {
        if (criteria == null) {
            return Collections.emptyMap();
        }
        return parse(criteria);
    }

    @Deprecated
    private static Map<String, Filter> parse(final Object criteria) {
        final List<Field> fields = getFilterFields(criteria.getClass());

        final Map<String, Filter> filtersByFieldName = new LinkedHashMap<>(fields.size());
        for (Field field : fields) {
            try {
                final Object fieldValue = field.get(criteria);
                if (fieldValue != null)
                    filtersByFieldName.put(field.getName(), (Filter) fieldValue);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }
        return filtersByFieldName;
    }

    @Deprecated
    private static List<Field> getFilterFields(final Class<?> clazz) {
        return cachedFilterFieldsOfClass
            .computeIfAbsent(clazz, clazzKey -> getFields(clazz).stream()
                .filter(fieldEntry -> Filter.class.isAssignableFrom(fieldEntry.getType()))
                .peek(fieldEntry -> fieldEntry.setAccessible(true))
                .collect(Collectors.toList()));
    }

    @Deprecated
    private static Collection<Field> getFields(Class<?> clazz) {
        final Class<?> baseClass = clazz;
        final Map<String, Field> fields = new LinkedHashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!fields.containsKey(field.getName())) {
                    fields.put(field.getName(), field);
                } else {
                    log.warn("Class '{}' has fields (inherited) with same name '{}', extra ones are ignored in '{}'!", baseClass.getName(), field.getName(), clazz.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields.values();
    }

    private static CriteriaData getCriteriaData(final Object criteria) {
        return cachedCriteriaDataByClass
            .computeIfAbsent(criteria.getClass(), aClass -> {
                // todo
                return null;
            });
    }

    static List<Field> getCriteriaCompatibleFields(Class<?> clazz) {
        final Class<?> baseClass = clazz;
        final List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            final Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {

            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * Only public and inherited methods are taken.
     * <br>
     * Only those annotated with {@link CriteriaInclude}.
     * <br>
     * Only those with no parameter.
     * <br>
     * Only those with a return type of value (int, long, Integer, Long, Instant, etc), or of {@link Filter} type or of an {@link Iterable}.
     *
     * @param clazz class
     * @return methods
     */
    static List<Method> getCriteriaCompatibleMethods(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .filter(CriteriaFieldParserUtil::isAnnotationCriteriaIncludePresent)
            .filter(CriteriaFieldParserUtil::isNoParameter)
            .filter(CriteriaFieldParserUtil::isReturnTypeSupported)
            .collect(Collectors.toList());
    }

    private static boolean isAnnotationCriteriaIncludePresent(final Method method) {
        final boolean annotationPresent = method.isAnnotationPresent(CriteriaInclude.class);
        if (!annotationPresent && log.isDebugEnabled()) {
            log.debug("Annotation CriteriaInclude not present for method: {}", method.toString());
        }
        return annotationPresent;
    }

    private static boolean isNoParameter(final Method method) {
        final int parameterCount = method.getParameterCount();
        if (log.isDebugEnabled()) {
            log.debug("Parameter count '{}' for method: {}", parameterCount, method.toString());
        }
        return parameterCount == 0;
    }

    private static boolean isReturnTypeSupported(final Method method) {
        final Class<?> returnType = method.getReturnType();
        if (log.isDebugEnabled()) {
            log.debug("For method '{}', return type '{}', generic: '{}'", method.toString(), returnType.getName(), method.toGenericString());
        }
        if (Filter.class.isAssignableFrom(returnType)) {
            return true;
        }
        if (Void.class.equals(returnType)) {
            log.info("Bad return type: {}", method.toString());
            return false;
        }
        // no way to test if is a list with correct type for values
        return true;
    }

    /**
     * Iterable passed should have not side-effect since it will be iterated for its first value if exist
     *
     * @param iterable iterable
     * @return first value if present and <b>not null</b>
     */
    static <T> Optional<T> getFirstValue(final Iterable<T> iterable) {
        final Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            final T firstValue = iterator.next();
            if (firstValue == null) { // null are forbidden, no further test
                log.warn("Got null in an iterable");
                return Optional.empty();
            }
            return Optional.of(firstValue);
        }
        return Optional.empty();
    }

    /**
     * Iterable passed should have not side-effect since it will be iterated for its first value if exist
     *
     * @param iterable            iterable
     * @param valueClassPredicate test the class of the first value from the {@code iterable}
     * @return true if no value or first value type is supported
     */
    static boolean isIterableValueTypeSupported(final Iterable<?> iterable, final Predicate<Class> valueClassPredicate) {
        return getFirstValue(iterable)
            .map(value -> valueClassPredicate.test(value.getClass()))
            .orElse(true);
    }


    static boolean isFilterType(final Class clazz) {
        return Filter.class.isAssignableFrom(clazz);
    }

    static boolean isIterableType(final Class clazz) {
        return Iterable.class.isAssignableFrom(clazz);
    }

    /**
     * @param alias    alias from {@link org.blackdread.lib.restfilter.criteria.annotation.CriteriaAlias}
     * @param isFilter true if field is a {@link Filter} or sub-class
     * @return alias formatted
     * @throws IllegalArgumentException if after formatting, alias is blank
     */
    static String formatFieldAlias(final String alias, final boolean isFilter) throws IllegalArgumentException {
        String result;
        if (isFilter) {
            if (alias.contains(".")) {
                log.info("Alias '{}' for filter field should not contain '.'", alias);
            }
            // for filter fields, any separation with '.' does not make sense
            result = StringUtils.substringBefore(alias, ".");
        } else {
            result = alias;
        }
        result = result.trim();
        if (StringUtils.isBlank(result)) {
            throw new IllegalArgumentException("Field alias cannot result to be blank, given alias: " + alias);
        }
        return result;
    }

    /**
     * @param methodName method name
     * @return method name formatted
     * @throws IllegalArgumentException if after formatting, name is blank
     */
    static String formatMethodName(final String methodName) throws IllegalArgumentException {
        String result = methodName;
        if (result.startsWith("get")) {
            return result.substring(3);
        }
        if (result.startsWith("is")) {
            return result.substring(3);
        }
        final String firstCharLowercase = result.substring(0, 1).toLowerCase();
        result = firstCharLowercase + result.substring(1);
        result = result.trim();
        if (StringUtils.isBlank(result)) {
            throw new IllegalArgumentException("Method name cannot result to be blank, given method name: " + methodName);
        }
        return result;
    }

}
