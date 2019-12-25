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
 * Asserts that the annotated {@link javax.servlet.http.Part}, {@link org.springframework.web.multipart.MultipartFile}, File or Path has mime types defined or inverse.
 * TODO Not sure if want to add an optional class of FileUploadValidation where it should be a single bean implementation so we can have dynamic {@code value} array of allowed mime types
 * <p>Created on 22/04/2018</p>
 *
 * @author Yoann CAPLAIN
 * @deprecated not implemented, nor tested
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
// want to try to register validators via DataBinder in config
@Constraint(validatedBy = {})
@Documented
@Repeatable(FileMimeType.List.class)
@Deprecated
public @interface FileMimeType {

    String message() default "{org.blackdread.lib.restfilter.validation.FileMimeType.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
     * @return Service where we can use dynamic values provided by {@link FileUploadValidation#getAuthorizedMimeTypes()}
     */
    //Class<? extends FileUploadValidation> service();

    /**
     * @return Array of allowed mime types for the file
     */
    String[] value();

    /**
     * @return if true, result of isValid is inverse
     */
    boolean inverse() default false;

    /**
     * Defines several {@link FileMimeType} annotations on the same element.
     *
     * @see FileMimeType
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        FileMimeType[] value();
    }
}
