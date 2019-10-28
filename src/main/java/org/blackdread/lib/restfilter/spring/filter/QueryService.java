/*
 * MIT License
 *
 * Copyright (c) 2018-2019 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.spring.filter;

import org.blackdread.lib.restfilter.filter.Filter;
import org.blackdread.lib.restfilter.filter.RangeFilter;
import org.blackdread.lib.restfilter.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * Base service for constructing and executing complex queries.
 * <p>Created on 2019/06/12</p>
 *
 * @param <ENTITY> the type of the entity which is queried.
 * @author Yoann CAPLAIN
 */
@Transactional(readOnly = true)
public interface QueryService<ENTITY> {
    /**
     * Helper function to return a specification for filtering on a single field, where equality, and null/non-null
     * conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X> field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a single field, where equality, and null/non-null
     * conditions are supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction the function, which navigates from the current entity to a column, for which the filter applies.
     * @param <X>               The type of the attribute which is filtered.
     * @return a Specification
     */
    default <X> Specification<ENTITY> buildSpecification(Filter<X> filter, Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getNotEquals() != null) {
            result = result.and(notEqualsSpecification(metaclassFunction, filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            result = result.and(valueNotIn(metaclassFunction, filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on a {@link String} field, where equality, containment,
     * and null/non-null conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @return a Specification
     */
    default Specification<ENTITY> buildStringSpecification(StringFilter filter, SingularAttribute<? super ENTITY, String> field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a {@link String} field, where equality, containment,
     * and null/non-null conditions are supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction lambda, which based on a Root&lt;ENTITY&gt; returns Expression - basicaly picks a column
     * @return a Specification
     */
    default Specification<ENTITY> buildSpecification(StringFilter filter, Function<Root<ENTITY>, Expression<String>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return filter.isIgnoreCase() ?
                equalsIgnoreCaseSpecification(metaclassFunction, filter.getEquals()) :
                equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return filter.isIgnoreCase() ?
                valueInIgnoreCase(metaclassFunction, filter.getIn()) :
                valueIn(metaclassFunction, filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getNotEquals() != null) {
            result = result.and(
                filter.isIgnoreCase() ?
                    notEqualsIgnoreCaseSpecification(metaclassFunction, filter.getNotEquals()) :
                    notEqualsSpecification(metaclassFunction, filter.getNotEquals())
            );
        }
        if (filter.getNotIn() != null) {
            result = result.and(
                filter.isIgnoreCase() ?
                    valueNotInIgnoreCase(metaclassFunction, filter.getNotIn()) :
                    valueNotIn(metaclassFunction, filter.getNotIn())
            );
        }
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
        }
        if (filter.getContains() != null) {
            result = result.and(
                filter.isIgnoreCase() ?
                    likeIgnoreCaseSpecification(metaclassFunction, filter.getContains()) :
                    likeSpecification(metaclassFunction, filter.getContains())
            );
        }
        if (filter.getNotContains() != null) {
            result = result.and(
                filter.isIgnoreCase() ?
                    notLikeIgnoreCaseSpecification(metaclassFunction, filter.getNotContains()) :
                    notLikeSpecification(metaclassFunction, filter.getNotContains())
            );
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on a single {@link Comparable}, where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter,
                                                                                            SingularAttribute<? super ENTITY, X> field) {
        return buildSpecification(filter, root -> root.get(field));
    }

    /**
     * Helper function to return a specification for filtering on a single {@link Comparable}, where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported.
     *
     * @param filter            the individual attribute filter coming from the frontend.
     * @param metaclassFunction lambda, which based on a Root&lt;ENTITY&gt; returns Expression - basicaly picks a column
     * @param <X>               The type of the attribute which is filtered.
     * @return a Specification
     */
    default <X extends Comparable<? super X>> Specification<ENTITY> buildSpecification(RangeFilter<X> filter,
                                                                                       Function<Root<ENTITY>, Expression<X>> metaclassFunction) {
        if (filter.getEquals() != null) {
            return equalsSpecification(metaclassFunction, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(metaclassFunction, filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getNotEquals() != null) {
            result = result.and(notEqualsSpecification(metaclassFunction, filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            result = result.and(valueNotIn(metaclassFunction, filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(metaclassFunction, filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(metaclassFunction, filter.getGreaterThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            result = result.and(greaterThanOrEqualTo(metaclassFunction, filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(metaclassFunction, filter.getLessThan()));
        }
        if (filter.getLessThanOrEqual() != null) {
            result = result.and(lessThanOrEqualTo(metaclassFunction, filter.getLessThanOrEqual()));
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on one-to-one or many-to-one reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByProjectId = buildReferringEntitySpecification(criteria.getProjectId(),
     * Employee_.project, Project_.id);
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(criteria.getProjectName(),
     * Employee_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if nullness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                               SingularAttribute<? super ENTITY, OTHER> reference,
                                                                               SingularAttribute<? super OTHER, X> valueField) {
        return buildSpecification(filter, root -> root.get(reference).get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                               SetAttribute<ENTITY, OTHER> reference,
                                                                               SingularAttribute<OTHER, X> valueField) {
        return buildReferringEntitySpecification(filter, root -> root.join(reference), entity -> entity.get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference.Usage:<pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(
     *          criteria.getEmployeId(),
     *          root -&gt; root.get(Project_.company).join(Company_.employees),
     *          entity -&gt; entity.get(Employee_.id));
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(
     *          criteria.getProjectName(),
     *          root -&gt; root.get(Project_.project)
     *          entity -&gt; entity.get(Project_.name));
     * </pre>
     *
     * @param filter           the filter object which contains a value, which needs to match or a flag if emptiness is
     *                         checked.
     * @param functionToEntity the function, which joins he current entity to the entity set, on which the filtering is applied.
     * @param entityToColumn   the function, which of the static metamodel of the referred entity, where the equality should be
     *                         checked.
     * @param <OTHER>          The type of the referenced entity.
     * @param <MISC>           The type of the entity which is the last before the OTHER in the chain.
     * @param <X>              The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <OTHER, MISC, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                     Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity,
                                                                                     Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {
        if (filter.getEquals() != null) {
            return equalsSpecification(functionToEntity.andThen(entityToColumn), filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(functionToEntity.andThen(entityToColumn), filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getNotEquals() != null) {
            result = result.and(notEqualsSpecification(functionToEntity.andThen(entityToColumn), filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            result = result.and(valueNotIn(functionToEntity.andThen(entityToColumn), filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            // Interestingly, 'functionToEntity' doesn't work, we need the longer lambda formula
            result = result.and(byFieldSpecified(root -> functionToEntity.apply(root), filter.getSpecified()));
        }

        return result;
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     * @deprecated prefer methods with metaclassFunction
     */
    @Deprecated
    default <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(RangeFilter<X> filter,
                                                                                                             SetAttribute<ENTITY, OTHER> reference,
                                                                                                             SingularAttribute<OTHER, X> valueField) {
        return buildReferringEntitySpecification(filter, root -> root.join(reference), entity -> entity.get(valueField));
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre><code>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(
     *          criteria.getEmployeId(),
     *          root -&gt; root.get(Project_.company).join(Company_.employees),
     *          entity -&gt; entity.get(Employee_.id));
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(
     *          criteria.getProjectName(),
     *          root -&gt; root.get(Project_.project)
     *          entity -&gt; entity.get(Project_.name));
     * </code>
     * </pre>
     *
     * @param filter           the filter object which contains a value, which needs to match or a flag if emptiness is
     *                         checked.
     * @param functionToEntity the function, which joins he current entity to the entity set, on which the filtering is applied.
     * @param entityToColumn   the function, which of the static metamodel of the referred entity, where the equality should be
     *                         checked.
     * @param <OTHER>          The type of the referenced entity.
     * @param <MISC>           The type of the entity which is the last before the OTHER in the chain.
     * @param <X>              The type of the attribute which is filtered.
     * @return a Specification
     */
    default <OTHER, MISC, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(
        RangeFilter<X> filter,
        Function<Root<ENTITY>, SetJoin<MISC, OTHER>> functionToEntity,
        Function<SetJoin<MISC, OTHER>, Expression<X>> entityToColumn) {

        Function<Root<ENTITY>, Expression<X>> fused = functionToEntity.andThen(entityToColumn);
        if (filter.getEquals() != null) {
            return equalsSpecification(fused, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(fused, filter.getIn());
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getNotEquals() != null) {
            result = result.and(notEqualsSpecification(fused, filter.getNotEquals()));
        }
        if (filter.getNotIn() != null) {
            result = result.and(valueNotIn(fused, filter.getNotIn()));
        }
        if (filter.getSpecified() != null) {
            // Interestingly, 'functionToEntity' doesn't work, we need the longer lambda formula
            result = result.and(byFieldSpecified(root -> functionToEntity.apply(root), filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(fused, filter.getGreaterThan()));
        }
        if (filter.getGreaterThanOrEqual() != null) {
            result = result.and(greaterThanOrEqualTo(fused, filter.getGreaterThanOrEqual()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(fused, filter.getLessThan()));
        }
        if (filter.getLessThanOrEqual() != null) {
            result = result.and(lessThanOrEqualTo(fused, filter.getLessThanOrEqual()));
        }
        return result;
    }

    /**
     * Generic method, which based on a Root&lt;ENTITY&gt; returns an Expression which type is the same as the given 'value' type.
     *
     * @param metaclassFunction function which returns the column which is used for filtering.
     * @param value             the actual value to filter for.
     * @param <X>               The type of the attribute which is filtered.
     * @return a Specification.
     */
    default <X> Specification<ENTITY> equalsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.equal(metaclassFunction.apply(root), value);
    }

    default <X> Specification<ENTITY> notEqualsSpecification(Function<Root<ENTITY>, Expression<X>> metaclassFunction, X value) {
        return (root, query, builder) -> builder.notEqual(metaclassFunction.apply(root), value);
    }

    default Specification<ENTITY> equalsIgnoreCaseSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> builder.equal(builder.upper(metaclassFunction.apply(root)), value.toUpperCase());
    }

    default Specification<ENTITY> notEqualsIgnoreCaseSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction, String value) {
        return (root, query, builder) -> builder.notEqual(builder.upper(metaclassFunction.apply(root)), value.toUpperCase());
    }

    default Specification<ENTITY> likeSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                    String value) {
        return (root, query, builder) -> builder.like(metaclassFunction.apply(root), wrapLikeQuery(value));
    }

    default Specification<ENTITY> notLikeSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                       String value) {
        return (root, query, builder) -> builder.notLike(metaclassFunction.apply(root), wrapLikeQuery(value));
    }

    default Specification<ENTITY> likeIgnoreCaseSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                              String value) {
        return (root, query, builder) -> builder.like(builder.upper(metaclassFunction.apply(root)), wrapLikeUpperQuery(value));
    }

    default Specification<ENTITY> notLikeIgnoreCaseSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                                 String value) {
        return (root, query, builder) -> builder.notLike(builder.upper(metaclassFunction.apply(root)), wrapLikeUpperQuery(value));
    }

    @Deprecated
    default Specification<ENTITY> likeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                         String value) {
        return likeIgnoreCaseSpecification(metaclassFunction, value);
    }

    @Deprecated
    default Specification<ENTITY> notLikeUpperSpecification(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                            String value) {
        return notLikeIgnoreCaseSpecification(metaclassFunction, value);
    }

    default <X> Specification<ENTITY> byFieldSpecified(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                       boolean specified) {
        return specified ?
            (root, query, builder) -> builder.isNotNull(metaclassFunction.apply(root)) :
            (root, query, builder) -> builder.isNull(metaclassFunction.apply(root));
    }

    default <X> Specification<ENTITY> byFieldEmptiness(Function<Root<ENTITY>, Expression<Set<X>>> metaclassFunction,
                                                       boolean specified) {
        return specified ?
            (root, query, builder) -> builder.isNotEmpty(metaclassFunction.apply(root)) :
            (root, query, builder) -> builder.isEmpty(metaclassFunction.apply(root));
    }

    default <X> Specification<ENTITY> valueIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                              Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(metaclassFunction.apply(root));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    default <X> Specification<ENTITY> valueNotIn(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                 Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(metaclassFunction.apply(root));
            for (X value : values) {
                in = in.value(value);
            }
            return in.not();
        };
    }

    default Specification<ENTITY> valueInIgnoreCase(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                    Collection<String> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<String> in = builder.in(builder.upper(metaclassFunction.apply(root)));
            for (String value : values) {
                in = in.value(value.toUpperCase());
            }
            return in;
        };
    }

    default Specification<ENTITY> valueNotInIgnoreCase(Function<Root<ENTITY>, Expression<String>> metaclassFunction,
                                                       Collection<String> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<String> in = builder.in(metaclassFunction.apply(root));
            for (String value : values) {
                in = in.value(value.toUpperCase());
            }
            return in.not();
        };
    }

    default <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                         X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    default <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                X value) {
        return (root, query, builder) -> builder.greaterThan(metaclassFunction.apply(root), value);
    }

    default <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                                      X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(metaclassFunction.apply(root), value);
    }

    default <X extends Comparable<? super X>> Specification<ENTITY> lessThan(Function<Root<ENTITY>, Expression<X>> metaclassFunction,
                                                                             X value) {
        return (root, query, builder) -> builder.lessThan(metaclassFunction.apply(root), value);
    }

    default String wrapLikeQuery(String txt) {
        return "%" + txt + '%';
    }

    default String wrapLikeUpperQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }
}
