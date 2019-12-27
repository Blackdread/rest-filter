package org.blackdread.lib.restfilter.validation.file.internal;

import org.blackdread.lib.restfilter.validation.file.FilenamePattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.file.Path;

public class FilenamePatternValidatorForPath extends FilenamePatternValidator implements ConstraintValidator<FilenamePattern, Path> {

    @Override
    public boolean isValid(Path value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Path fileName = value.getFileName();
        if (fileName == null) {
            // todo check logic to apply
            return false;
        }
        return super.isValid(fileName.toString(), context);
    }

}
