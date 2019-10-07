package org.blackdread.lib.restfilter.spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.File;

/**
 * <p>Created on 2018/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public class FileSizeValidatorForFile extends FileSizeValidator implements ConstraintValidator<FileSize, File> {

    @Override
    public boolean isValid(final File value, final ConstraintValidatorContext context) {
        if (value == null)
            return true;
        final long fileSize = value.length();
        return fileSize >= getMinBytes() && fileSize <= getMaxBytes();
    }
}
