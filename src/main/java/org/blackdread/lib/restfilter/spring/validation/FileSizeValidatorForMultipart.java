package org.blackdread.lib.restfilter.spring.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>Created on 2019/10/06.</p>
 *
 * @author Yoann CAPLAIN
 */
public class FileSizeValidatorForMultipart  extends FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        final long fileSize = value.getSize();
        return fileSize >= getMinBytes() && fileSize <= getMaxBytes();
    }

}
