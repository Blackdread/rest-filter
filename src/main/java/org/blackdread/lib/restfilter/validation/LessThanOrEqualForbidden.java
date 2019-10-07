package org.blackdread.lib.restfilter.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.blackdread.lib.restfilter.validation.LessThanOrEqualForbidden.List;

/**
 *
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
@Documented
@Constraint(validatedBy = LessThanOrEqualForbiddenValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface LessThanOrEqualForbidden {

    String message() default "{org.blackdread.lib.restfilter.validation.LessThanOrEqualForbidden.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @LessThanOrEqualForbidden} constraints on the same element.
     *
     * @see LessThanOrEqualForbidden
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        LessThanOrEqualForbidden[] value();
    }
}
