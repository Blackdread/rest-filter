package org.blackdread.lib.restfilter.spring.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.blackdread.lib.restfilter.spring.validation.FileSize.List;

/**
 * Asserts that the annotated {@link javax.servlet.http.Part}, {@link org.springframework.web.multipart.MultipartFile}, File or Path size is between the specified boundaries.
 * <p>Created on 2019/10/07.</p>
 *
 * @author Yoann CAPLAIN
 */
@Documented
@Constraint(validatedBy = {FileSizeValidatorForFile.class, FileSizeValidatorForPath.class, FileSizeValidatorForPart.class, FileSizeValidatorForMultipart.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Repeatable(List.class)
public @interface FileSize {

    String message() default "{org.blackdread.lib.restfilter.validation.FileSize.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 1 Byte = 8 bit
     *
     * @return minBytes file size accepted in <b>Bytes</b>, must be higher or equal to
     */
    long min() default 0;

    /**
     * 1 Byte = 8 bit
     *
     * @return maxBytes file size accepted in <b>Bytes</b>, must be lower or equal to
     */
    long max();

    /**
     * Defines several {@code @FileSize} constraints on the same element.
     *
     * @see FileSize
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        FileSize[] value();
    }
}
