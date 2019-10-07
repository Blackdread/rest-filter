package org.blackdread.lib.restfilter.validation;

import org.blackdread.lib.restfilter.filter.StringFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class ContainsForbiddenValidator implements ConstraintValidator<ContainsForbidden, StringFilter> {

//    private static final Logger log = LoggerFactory.getLogger(ContainsForbiddenValidator.class);

    @Override
    public void initialize(ContainsForbidden constraintAnnotation) {
    }

    @Override
    public boolean isValid(StringFilter value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        return value.getContains() == null;
    }

}
