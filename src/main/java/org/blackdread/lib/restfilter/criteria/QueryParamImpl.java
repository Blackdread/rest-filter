package org.blackdread.lib.restfilter.criteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * <p>Created on 2019/10/24.</p>
 *
 * @author Yoann CAPLAIN
 */
final class QueryParamImpl implements QueryParam {

    private static final Logger log = LoggerFactory.getLogger(QueryParamImpl.class);

    private final String criteriaName;
    private final String paramValue;
    private final List<String> paramValues;

    static QueryParam ofNameOnly(final String criteriaName) {
        return new QueryParamImpl(criteriaName);
    }

    static QueryParam ofSingleValue(final String criteriaName, final String paramValue) {
        return new QueryParamImpl(criteriaName, paramValue);
    }

    static QueryParam ofMultipleValues(final String criteriaName, final List<String> paramValues) {
        return new QueryParamImpl(criteriaName, paramValues);
    }

    private QueryParamImpl(final String criteriaName) {
        this.criteriaName = Objects.requireNonNull(criteriaName);
        this.paramValue = null;
        this.paramValues = List.of();
    }

    private QueryParamImpl(final String criteriaName, final String paramValue) {
        this.criteriaName = Objects.requireNonNull(criteriaName);
        this.paramValue = Objects.requireNonNull(paramValue);
        this.paramValues = List.of(paramValue);
    }

    private QueryParamImpl(final String criteriaName, final List<String> paramValues) {
        this.criteriaName = Objects.requireNonNull(criteriaName);
        this.paramValues = List.copyOf(paramValues);
        this.paramValue = String.join(",", this.paramValues);
    }


    @Override
    public String getCriteriaName() {
        return criteriaName;
    }

    @Override
    public boolean hasParamValue() {
        return paramValues.size() >= 1;
    }

    @Override
    public boolean hasMultipleParamValue() {
        return paramValues.size() > 1;
    }

    @Override
    public String getParamValue() {
        return paramValue;
    }

    @Override
    public List<String> getParamValues() {
        return paramValues;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final QueryParamImpl that = (QueryParamImpl) o;
        return criteriaName.equals(that.criteriaName) &&
            paramValue.equals(that.paramValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(criteriaName, paramValue);
    }

    @Override
    public String toString() {
        return "FilterQueryParamImpl{" +
            "criteriaName='" + criteriaName + '\'' +
            ", paramValue='" + paramValue + '\'' +
            ", paramValues=" + paramValues +
            '}';
    }
}
