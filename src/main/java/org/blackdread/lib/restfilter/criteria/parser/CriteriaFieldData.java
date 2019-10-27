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

import org.blackdread.lib.restfilter.criteria.annotation.CriteriaAlias;
import org.blackdread.lib.restfilter.criteria.annotation.CriteriaInclude;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Optional;

/**
 * All data that can be extracted from field, annotations, etc
 */
@ThreadSafe
@Immutable
public final class CriteriaFieldData {
    private final Field field;

    private final boolean isValue;
    private final boolean isFilter;
    private final boolean isIterable;
    private final boolean isArray;

    private final String fieldName;
    private final Class<?> fieldType;

    @Nullable
    private final CriteriaAlias criteriaAlias;
    @Nullable
    private final String formattedAliasName;

    @Nullable
    private final CriteriaInclude criteriaInclude;
    @Nullable
    private final Class<?> wrappedType;

    static CriteriaFieldData of(final Field field) {
        return new CriteriaFieldData(Objects.requireNonNull(field));
    }

    private CriteriaFieldData(final Field field) {
        // extraction hard-coded as should not change based on user options/etc
        this.fieldType = field.getType();
        this.field = field;
        this.isIterable = CriteriaFieldParserUtil.isIterableType(fieldType);
        this.isFilter = CriteriaFieldParserUtil.isFilterType(fieldType);
        this.isArray = fieldType.isArray();
        // Default is value if none above
        this.isValue = !isIterable && !isFilter && !isArray;

        this.fieldName = field.getName();
        this.criteriaAlias = field.getAnnotation(CriteriaAlias.class);
        if (criteriaAlias != null) {
            this.formattedAliasName = CriteriaFieldParserUtil.formatFieldAlias(criteriaAlias.value(), isFilter);
        } else {
            formattedAliasName = null;
        }

        this.criteriaInclude = field.getAnnotation(CriteriaInclude.class);
        if (criteriaInclude != null) {
            this.wrappedType = criteriaInclude.value().equals(Void.class) ? null : criteriaInclude.value();
        } else {
            wrappedType = null;
        }
    }

    public Field getField() {
        return field;
    }

    /**
     * @return true when is none of {@link #isFilter()}, {@link #isIterable()} and {@link #isArray()}
     */
    public boolean isValue() {
        return isValue;
    }

    /**
     * @return true if field is of type {@link org.blackdread.lib.restfilter.filter.Filter} or subclass.
     */
    public boolean isFilter() {
        return isFilter;
    }

    /**
     * @return true if field is of type {@link java.util.Iterator} or subclass.
     */
    public boolean isIterable() {
        return isIterable;
    }

    /**
     * @return true if field is of type array
     */
    public boolean isArray() {
        return isArray;
    }

    /**
     * Same as {@link Field#getName()}
     *
     * @return field name unchanged
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Same as {@link Field#getType()}
     *
     * @return field type
     */
    public Class<?> getFieldType() {
        return fieldType;
    }

    public Optional<CriteriaAlias> getCriteriaAlias() {
        return Optional.ofNullable(criteriaAlias);
    }

    /**
     * @return formatted alias with static rules on alias for fields
     */
    public Optional<String> getFormattedAliasName() {
        return Optional.ofNullable(formattedAliasName);
    }

    public Optional<CriteriaInclude> getCriteriaInclude() {
        return Optional.ofNullable(criteriaInclude);
    }

    /**
     * @return type from {@link CriteriaInclude#value()} ()} but empty if is default value
     */
    public Optional<Class<?>> getWrappedType() {
        return Optional.ofNullable(wrappedType);
    }

    @Override
    public String toString() {
        return "CriteriaFieldData{" +
            "field=" + field +
            ", isValue=" + isValue +
            ", isFilter=" + isFilter +
            ", isIterable=" + isIterable +
            ", isArray=" + isArray +
            ", fieldName='" + fieldName + '\'' +
            ", fieldType=" + fieldType +
            ", criteriaAlias=" + criteriaAlias +
            ", formattedAliasName='" + formattedAliasName + '\'' +
            ", criteriaInclude=" + criteriaInclude +
            ", wrappedType=" + wrappedType +
            '}';
    }
}
