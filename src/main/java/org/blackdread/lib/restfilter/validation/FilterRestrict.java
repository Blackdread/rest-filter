package org.blackdread.lib.restfilter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.blackdread.lib.restfilter.validation.FilterRestrict.List;

/**
 * Restrict use of filtering option inside {@link org.blackdread.lib.restfilter.filter.Filter}, {@link org.blackdread.lib.restfilter.filter.RangeFilter}, {@link org.blackdread.lib.restfilter.filter.StringFilter}, etc.
 * <p>
 * Allow to use one annotation instead of {@link EqualsForbidden}, {@link NotEqualsForbidden}, {@link InForbidden}, etc
 *
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
@Documented
@Constraint(validatedBy = FilterRestrictValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface FilterRestrict {

    String message() default "{org.blackdread.lib.restfilter.validation.FilterRestrict.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Allow to make all restricted by default.
     * Only those marked as true <b>will not be restricted</b>.
     *
     * @return inverse restriction
     */
    boolean inverseRestrict() default false;

    boolean restrictEquals() default false;

    boolean restrictNotEquals() default false;

    boolean restrictIn() default false;

    boolean restrictNotIn() default false;

    boolean restrictSpecified() default false;

    boolean restrictGreaterThan() default false;

    boolean restrictGreaterThanOrEqual() default false;

    boolean restrictLessThan() default false;

    boolean restrictLessThanOrEqual() default false;

    boolean restrictContains() default false;

    boolean restrictNotContains() default false;

    /**
     * Defines several {@code @FilterRestrict} constraints on the same element.
     *
     * @see FilterRestrict
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        FilterRestrict[] value();
    }
}
