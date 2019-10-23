package org.blackdread.lib.restfilter.criteria.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allow to include a field (filter type or not) to be used with query param creation.
 * <p>
 * This annotation is mostly useful for boolean values mostly as other type should prefer to use actual implementation of {@link org.blackdread.lib.restfilter.filter.Filter}
 * <p>
 * Examples:
 * <pre>
 * public class MyCriteria implements Criteria {
 *   &#64;CriteriaInclude
 *   public String name;
 *
 *   &#64;CriteriaInclude
 *   public boolean showHidden;
 *
 *   &#64;CriteriaAlias("fullName.contains"})
 *   &#64;CriteriaInclude
 *   public String getFullName(){
 *       // ...
 *   }
 *
 *   &#64;CriteriaAlias("allow")
 *   &#64;CriteriaInclude
 *   public boolean isAnything(){
 *       // ...
 *   }
 * }
 * </pre>
 * <p>Created on 2019/10/23.</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CriteriaInclude {

    /**
     * Type of field or method return type when it cannot be determined,
     * or a different one should be used when formatting the value.
     */
    Class<?> type() default Void.class;

    // similar to validation groups but not used for now, simple logic for now
//    Class<?>[] groups() default {};
}
