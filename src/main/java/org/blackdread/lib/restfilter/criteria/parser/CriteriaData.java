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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class CriteriaData {

    private final Class<?> criteriaClass;

    private final List<CriteriaFieldData> fields = new ArrayList<>();

    private final List<CriteriaMethodData> methods = new ArrayList<>();

    static CriteriaData of(final Class<?> criteriaClass) {
        return new CriteriaData(Objects.requireNonNull(criteriaClass));
    }

    private CriteriaData(final Class<?> criteriaClass) {
        // extraction hard-coded as should not change based on user options/etc
        this.criteriaClass = criteriaClass;

        final List<Field> fields = CriteriaFieldParserUtil.getCriteriaCompatibleFields(criteriaClass);
        fields.forEach(field -> this.fields.add(CriteriaFieldData.of(field)));

        final Collection<Method> methods = CriteriaFieldParserUtil.getCriteriaCompatibleMethods(criteriaClass);
        methods.forEach(method -> this.methods.add(CriteriaMethodData.of(method)));
    }

    public Class<?> getCriteriaClass() {
        return criteriaClass;
    }

    public List<CriteriaFieldData> getFields() {
        return fields;
    }

    public List<CriteriaMethodData> getMethods() {
        return methods;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CriteriaData that = (CriteriaData) o;
        return Objects.equals(criteriaClass, that.criteriaClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteriaClass);
    }

}
