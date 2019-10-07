package org.blackdread.lib.restfilter.validation;

import org.blackdread.lib.restfilter.filter.StringFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class NotContainsForbiddenValidator implements ConstraintValidator<NotContainsForbidden, StringFilter> {

//    private static final Logger log = LoggerFactory.getLogger(NotContainsForbiddenValidator.class);

    @Override
    public void initialize(NotContainsForbidden constraintAnnotation) {
    }

    @Override
    public boolean isValid(StringFilter value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return value.getNotContains() == null;
    }

}
