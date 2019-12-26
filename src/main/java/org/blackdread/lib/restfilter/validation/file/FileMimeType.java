/*
 * MIT License
 *
 * Copyright (c) 2019-2020 Yoann CAPLAIN
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
 * <p>Created on 2018/04/22</p>
 *
 * @author Yoann CAPLAIN
 * @since 2.2.1
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
