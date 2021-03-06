/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
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
 * Annotated methods must not take any parameter and must return a single value (filter type or not), must be public.
 * <p>
 * Iterable types can only be of simple types like Integer, Long, String, Instant, etc.
 * <p>
 * Examples:
 * <pre>
 * public class MyCriteria implements Criteria {
 *   &#64;CriteriaInclude
 *   private String name;
 *
 *   &#64;CriteriaInclude
 *   private boolean showHidden;
 *
 *   &#64;CriteriaInclude
 *   private MyEnum status;
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
 *
 *   &#64;CriteriaInclude(Long.class)
 *   public List&lt;Long&gt; anything2(){
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
     * Type of object contained in an iterable/collection/array.
     * Type of field that should be used when formatting values for query params.
     *
     * @return type of wrapped value in iterable/collection/array
     */
    Class<?> value() default Void.class;

    /**
     * Can only be true on boolean field and method with a return type of boolean.
     * If found on others, an exception will be thrown.
     *
     * @return true if value is not formatted, so only key is kept for query param
     */
    boolean keyOnly() default false;

    /*
     * If {@link #value()} is defined, it is not necessary to set this type, if set then it will use the formatter matching the {@code type()} class.
     */
//    Class<?> formatterType() default Void.class;

    // similar to validation groups but not used for now, simple logic for now
//    Class<?>[] groups() default {};
}
