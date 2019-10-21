/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.criteria;

import java.util.List;

/**
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface FilterQueryParam {

    /**
     * Name of field of a filter that may have been defined inside a criteria.
     * <pre>
     *  class MyCriteria implements Criteria {
     *      // ommitted functions/constructors/etc
     *      private LongFilter myFieldName;
     *  }
     * </pre>
     *
     * @return field name
     */
    String getFieldName();


    /**
     * Values like:
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#EQUALS_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#IN_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#GREATER_THAN_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#CONTAINS_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#NOT_CONTAINS_FILTER},
     * etc
     *
     * @return filter property name
     */
    String getFilterPropertyName();

    /**
     * Concat {@link #getFieldName()} +
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#FIELD_NAME_AND_FILTER_SEPARATOR} +
     * {@link #getFilterPropertyName()}
     *
     * @return Concatenation of field name, separator and filter property name
     */
    default String getParamName() {
        return getFieldName() + CriteriaQueryParam.FIELD_NAME_AND_FILTER_SEPARATOR + getFilterPropertyName();
    }

    /**
     * @return true if at least one param value is available
     */
    boolean hasParamValue();

    /**
     * @return true if multiple param value is available
     */
    boolean hasMultipleParamValue();

    /**
     * @return true if only one param value is available
     */
    default boolean isOnlyOneParamValue() {
        return hasParamValue() && !hasMultipleParamValue();
    }

    /**
     * Value to use for query, 'myField.equals=XXX', where XXX is the returned value.
     * <p>
     * For multiple values, it will return all values separated by ','.
     * You can use {@link #getParamValues()} to get each values separately.
     *
     * @return value of a query param
     */
    String getParamValue();

    /**
     * @return param values
     */
    List<String> getParamValues();
}
