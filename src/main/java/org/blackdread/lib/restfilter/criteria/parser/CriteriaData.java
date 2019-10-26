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
final class CriteriaData {

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
