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
 * Allow to exclude a field/method (filter type or not) to be used with query param creation.
 * <p>
 * This annotation is mostly useful for field of type {@link org.blackdread.lib.restfilter.filter.Filter}, to exclude some fields from being used by the library.
 * <p>
 * Behavior on annotated methods is unknown.
 * <p>
 * If same element is annotated with {@link CriteriaInclude} and {@link CriteriaIgnore}, behavior is unknown.
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
 *   &#64;CriteriaIgnore // would already not be included as CriteriaInclude is not on field
 *   private boolean doNotWantToIncludeWithFieldAccess;
 *
 *   &#64;CriteriaIgnore
 *   private LongFilter doNotWantToIncludeWithFieldAccess2;
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
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CriteriaIgnore {

    // similar to validation groups but not used for now, simple logic for now
//    Class<?>[] groups() default {};

}
