package org.blackdread.lib.restfilter.spring.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <p>Created on 2018/4/22.</p>
 *
 * @author Yoann CAPLAIN
 */
public class FileSizeValidatorForPath extends FileSizeValidator implements ConstraintValidator<FileSize, Path> {

    @Override
    public boolean isValid(final Path value, final ConstraintValidatorContext context) {
        if (value == null)
            return true;
        final long fileSize;
        try {
            fileSize = Files.size(value);
        } catch (IOException e) {
            context.disableDefaultConstraintViolation();
            context
                .buildConstraintViolationWithTemplate("Failed to get file size: " + e.getMessage())
                .addConstraintViolation();
            return false;
        }
        return fileSize >= getMinBytes() && fileSize <= getMaxBytes();
    }
}
