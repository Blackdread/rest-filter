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
 *   private String name;
 *
 *   &#64;CriteriaInclude
 *   &#64;CriteriaAlias("createTime.greaterThan"}) // can use like if back-end use Filter class but client code is not
 *   private Instant createdAfter;
 *
 *   &#64;CriteriaAlias("updateTime"})
 *   private InstantFilter updatedAt;
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
     * For basic type (Boolean, String, etc), name may use '.' if back-end is using some Filter class but client code criteria is not using.
     * <br>
     * For filter type, if '.' is used, behavior is unknown. May throw or ignore any character after first '.'.
     *
     * @return alias to use for param name
     */
    String value();

}
