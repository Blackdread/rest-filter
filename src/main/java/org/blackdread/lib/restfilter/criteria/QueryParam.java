package org.blackdread.lib.restfilter.criteria;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface QueryParam {

    /**
     * Name of field that may have been defined inside a criteria.
     * <pre>
     *  class MyCriteria implements Criteria {
     *      // omitted functions/constructors/etc
     *      private long myFieldName;
     *      private boolean myFieldName2;
     *  }
     * </pre>
     *
     * @return field name
     */
    String getFieldName(); // todo too specific? remove this one?

    default String getParamName() {
        return getFieldName();
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
     * Value to use for query, 'paramName=XXX', where XXX is the returned value.
     * <p>
     * For multiple values, it will return all values separated by ','.
     * You can use {@link #getParamValues()} to get each values separately.
     *
     * @return value of a query param
     */
    @Nullable
    String getParamValue();

    /**
     * Value to use for query, 'paramName=XXX', where XXX is the returned value.
     * <p>
     * For multiple values, it will return all values separated by ','.
     * You can use {@link #getParamValues()} to get each values separately.
     *
     * @return value of a query param wrapped in Optional if none present
     */
    Optional<String> getParamValueOpt();

    /**
     * @return param values (may be empty)
     */
    List<String> getParamValues();

}
