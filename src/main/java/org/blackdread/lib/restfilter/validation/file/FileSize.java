package org.blackdread.lib.restfilter.validation.file;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Asserts that the annotated {@link javax.servlet.http.Part}, {@link org.springframework.web.multipart.MultipartFile}, File or Path size is between the specified boundaries (included).
 * TODO Not sure if want to add an optional class of FileUploadValidation where it should be a single bean implementation so we can have dynamic {@code minBytes} and {@code maxBytes} allowed file size
 * <p>Created on 22/04/2018</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Repeatable(FileSize.List.class)
public @interface FileSize {

    String message() default "{org.blackdread.lib.restfilter.validation.FileSize.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
     * @return Service where we can use dynamic values provided by {@link FileUploadValidation#getMaxFileUploadSizeInByte()} and {@link FileUploadValidation#getMinFileUploadSizeInByte()}
     */
//    Class<? extends FileUploadValidation> service();

    /**
     * @return minBytes file size accepted in Bytes, must be higher or equal to
     */
    long min() default 0;

    /**
     * @return maxBytes file size accepted in Bytes, must be lower or equal to
     */
    long max();

    /**
     * Defines several {@link FileSize} annotations on the same element.
     *
     * @see FileSize
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        FileSize[] value();
    }
}
