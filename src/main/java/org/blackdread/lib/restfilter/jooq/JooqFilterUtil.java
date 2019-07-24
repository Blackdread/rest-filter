package org.blackdread.lib.restfilter.jooq;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import java.util.stream.Collectors;

/**
 * Build condition based on filter passed, for more complex conditions, might require to manually write it instead of using this class.
 * Class is not final to let user shadow or add static methods while keeping
 * <p>Created on 2019/07/23</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not sure of API
 */
public class JooqFilterUtil {

    public static <X, T extends Field<X>> Condition buildCondition(final Filter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

    public static <X extends Comparable<? super X>, T extends Field<X>> Condition buildCondition(final RangeFilter<X> filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        }

        Condition condition = DSL.trueCondition();
        if (filter.getSpecified() != null) {
            condition = condition.and(filter.getSpecified() ? field.isNotNull() : field.isNull());
        }
        if (filter.getGreaterThan() != null) {
            condition = condition.and(field.greaterThan(filter.getGreaterThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            condition = condition.and(field.greaterOrEqual(filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThan() != null) {
            condition = condition.and(field.lessThan(filter.getLessThan()));
        }
        if (filter.getLessThanOrEqual() != null) {
            condition = condition.and(field.lessOrEqual(filter.getLessThanOrEqual()));
        }
        return condition;
    }

    public static <T extends Field<String>> Condition buildCondition(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equal(filter.getEquals());
        } else if (filter.getIn() != null) {
            return field.in(filter.getIn());
        } else if (filter.getContains() != null) {
            return field.containsIgnoreCase(filter.getContains());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

    public static <T extends Field<String>> Condition buildConditionIgnoreCase(final StringFilter filter, final T field) {
        if (filter.getEquals() != null) {
            return field.equalIgnoreCase(filter.getEquals());
        } else if (filter.getIn() != null) {
            return DSL.upper(field).in(filter.getIn().stream().map(String::toUpperCase).collect(Collectors.toSet()));
        } else if (filter.getContains() != null) {
            return field.containsIgnoreCase(filter.getContains());
        } else if (filter.getSpecified() != null) {
            return filter.getSpecified() ? field.isNotNull() : field.isNull();
        }
        return DSL.trueCondition();
    }

    private JooqFilterUtil() {
    }
}
