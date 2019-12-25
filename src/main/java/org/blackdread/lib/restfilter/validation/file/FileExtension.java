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
 * Asserts that the annotated {@link javax.servlet.http.Part}, {@link org.springframework.web.multipart.MultipartFile}, File or Path has extensions defined or inverse.
 * TODO Not sure if want to add an optional class of FileUploadValidation where it should be a single bean implementation so we can have dynamic {@code value} array of allowed extensions
 * <p>Created on 22/04/2018</p>
 *
 * @author Yoann CAPLAIN
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
@Repeatable(FileExtension.List.class)
public @interface FileExtension {

    String message() default "{org.blackdread.lib.restfilter.validation.FileExtension.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /*
     * @return Service where we can use dynamic values provided by {@link FileUploadValidation#getAuthorizedFileExtensions()}
     */
    //Class<? extends FileUploadValidation> service();

    /**
     * Extensions without "."
     *
     * @return Array of allowed extensions
     */
    String[] value();

    /**
     * @return if true, result of isValid is inverse
     */
    boolean inverse() default false;

    boolean isIgnoreCase() default true;

    /**
     * Defines several {@link FileExtension} annotations on the same element.
     *
     * @see FileExtension
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        FileExtension[] value();
    }
}
