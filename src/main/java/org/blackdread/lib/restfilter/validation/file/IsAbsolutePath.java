package org.blackdread.lib.restfilter.validation.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Asserts that the annotated File or Path is an absolute path.
 * <p>Created on 2018/4/4.</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Repeatable(IsAbsolutePath.List.class)
public @interface IsAbsolutePath {

    String message() default "{org.blackdread.lib.restfilter.validation.IsAbsolutePath.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link IsAbsolutePath} annotations on the same element.
     *
     * @see IsAbsolutePath
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        IsAbsolutePath[] value();
    }
}
