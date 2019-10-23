package org.blackdread.lib.restfilter.criteria.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that can be used to define one alternative name for
 * a property, accepted during query param creation as alternative to the official name.
 * <p>
 * This annotation is not a replacement of Filters, you should prefer to use actual implementation of {@link org.blackdread.lib.restfilter.filter.Filter} to have filtering features.
 * <p>
 * Examples:
 * <pre>
 * public class MyCriteria implements Criteria {
 *   &#64;CriteriaInclude
 *   &#64;CriteriaAlias("userName"})
 *   public String name;
 *
 *   &#64;CriteriaInclude
 *   &#64;CriteriaAlias("createTime.greaterThan"}) // can use like if back-end use Filter class but client code is not
 *   public Instant createdAfter;
 *
 *   &#64;CriteriaAlias("updateTime"})
 *   public InstantFilter updatedAt;
 *
 *   &#64;CriteriaInclude
 *   &#64;CriteriaAlias("extraName"})
 *   public String getFullName(){
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
public @interface CriteriaAlias {

    /**
     * Name of query param to use.
     * For field, it use alias if set instead of field name.
     * For methods, it use alias instead of method name (method name automatically removes
     * 'get' or 'is' or etc depending on options set when initializing the library.
     * <p>
     * For basic type (Boolean, String, etc), name may use '.' if back-end is using some Filter class but criteria is not using.
     * <br>
     * For filter type, if '.' is used, behavior is unknown. May throw or ignore any character after first '.'.
     */
    String name() default "";

}
