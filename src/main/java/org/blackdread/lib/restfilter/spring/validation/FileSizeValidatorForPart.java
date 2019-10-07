package org.blackdread.lib.restfilter.spring.validation;

import javax.servlet.http.Part;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Created on 2018/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public class FileSizeValidatorForPart extends FileSizeValidator implements ConstraintValidator<FileSize, Part> {

    @Override
    public boolean isValid(final Part value, final ConstraintValidatorContext context) {
        if (value == null)
            return true;
        final long fileSize = value.getSize();
        return fileSize >= getMinBytes() && fileSize <= getMaxBytes();
    }
}
