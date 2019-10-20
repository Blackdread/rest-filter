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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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

    /**
     * Key of map is field name
     *
     * @param criteria A criteria object used for filtering
     * @return All fields (and inherited) of criteria class, fields that extends {@link Filter}.
     * Key of map is field name
     */
    public static Map<String, Filter> build(@Nullable final Object criteria) {
        if (criteria == null) {
            return Collections.emptyMap();
        }
        return parse(criteria);
    }

    private static Map<String, Filter> parse(final Object criteria) {
        final List<Field> fields = getFilterFields(criteria.getClass());

        final Map<String, Filter> filtersByFieldName = new HashMap<>(fields.size());
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

    private static List<Field> getFilterFields(final Class<?> clazz) {
        return cachedFilterFieldsOfClass
            .computeIfAbsent(clazz, clazzKey -> getFields(clazz).stream()
                .filter(fieldEntry -> Filter.class.isAssignableFrom(fieldEntry.getType()))
                .peek(fieldEntry -> fieldEntry.setAccessible(true))
                .collect(Collectors.toList()));
    }

    private static Collection<Field> getFields(Class<?> clazz) {
        final Class<?> baseClass = clazz;
        final Map<String, Field> fields = new HashMap<>();
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
}
