package org.blackdread.lib.restfilter.criteria;

import java.util.List;

/**
 * <p>Created on 2019/10/20.</p>
 *
 * @author Yoann CAPLAIN
 */
public interface FilterQueryParam {

    /**
     * @return field name
     */
    String getFieldName();


    /**
     * Values like:
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#EQUALS_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#IN_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#GREATER_THAN_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#CONTAINS_FILTER},
     * {@link org.blackdread.lib.restfilter.criteria.CriteriaQueryParam#NOT_CONTAINS_FILTER}
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
     * Value to use for query, 'myField.equals=XXX', where XXX is the returned value.
     * <p>
     * For multiple values, it will return all values separated by ','.
     * You can use {@link #getParamValues()} to get each values separately.
     *
     * @return value of a query param
     */
    String getParamValue();

    List<String> getParamValues();
}
