/*
 * MIT License
 *
 * Copyright (c) 2019 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.spring.validation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>Created on 2019/10/07.</p>
 *
 * @author Yoann CAPLAIN
 */
class FileSizeValidatorTest {

    private FileSizeValidatorForPath fileSizeValidatorForPath;
    private FileSizeValidatorForFile fileSizeValidatorForFile;
    private FileSizeValidatorForPart fileSizeValidatorForPart;
    private FileSizeValidatorForMultipart fileSizeValidatorForMultipart;

    private final File notExistFile = new File("fake.txt");
    private final Path notExistPath = Path.of("fake.txt");

    private final File file1KB = new File("src/test/resources/1KB.txt");
    private final File file1MB = new File("src/test/resources/1MB.txt");

    private final Path path1KB = Path.of("src/test/resources/1KB.txt");
    private final Path path1MB = Path.of("src/test/resources/1MB.txt");

    private MyPojo myPojo;

    private Validator validator;

    @BeforeEach
    void setUp() throws IOException {
        Locale.setDefault(Locale.ENGLISH);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        fileSizeValidatorForPath = new FileSizeValidatorForPath();
        fileSizeValidatorForFile = new FileSizeValidatorForFile();
        fileSizeValidatorForPart = new FileSizeValidatorForPart();
        fileSizeValidatorForMultipart = new FileSizeValidatorForMultipart();

        myPojo = new MyPojo();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void file1KB_is1000bytes() {
        final long length = file1KB.length();
        assertTrue(file1KB.exists());
        assertEquals(1000, length);
    }

    @Test
    void file1MB_is1_000_000bytes() {
        final long length = file1MB.length();
        assertEquals(1_000_000, length);
    }

    @Test
    void path1KB_is1bytes() throws IOException {
        final long length = Files.size(path1KB);
        assertEquals(1000, length);
    }

    @Test
    void path1MB_is1_000_000bytes() throws IOException {
        final long length = Files.size(path1MB);
        assertEquals(1_000_000, length);
    }

    @Test
    void pathErrorMessageDifferOnFileNotExisting() {
        final Set<ConstraintViolation<MyPojo>> constraintViolations = validator.validate(myPojo, NotExist.class);

        final List<String> actualConstraints = constraintViolations.stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        assertEquals(List.of("Failed to get file size: fake.txt"), actualConstraints);
    }

    @Test
    void validatorAcceptNull() {
        assertTrue(fileSizeValidatorForPath.isValid(null, null));
        assertTrue(fileSizeValidatorForFile.isValid(null, null));
        assertTrue(fileSizeValidatorForPart.isValid(null, null));
        assertTrue(fileSizeValidatorForMultipart.isValid(null, null));
    }

    @Test
    void throwsIfMinLessThan0() {
        assertThrows(ValidationException.class, () -> validator.validate(myPojo, MinLessThan0.class));
    }

    @Test
    void throwsIfMaxLessThan0() {
        assertThrows(ValidationException.class, () -> validator.validate(myPojo, MaxLessThan0.class));
    }

    @Test
    void throwsIfMaxLessThanMin() {
        assertThrows(ValidationException.class, () -> validator.validate(myPojo, MaxLessThanMin.class));
    }

    @Test
    void minIsInclusive() {
        final Set<ConstraintViolation<MyPojo>> constraintViolations = validator.validate(myPojo, MinInclusive.class);

        assertEquals(Collections.emptySet(), constraintViolations);
    }

    @Test
    void maxIsInclusive() {
        final Set<ConstraintViolation<MyPojo>> constraintViolations = validator.validate(myPojo, MaxInclusive.class);

        assertEquals(Collections.emptySet(), constraintViolations);
    }

    @Test
    void fileTooSmall() {
        final Set<ConstraintViolation<MyPojo>> constraintViolations = validator.validate(myPojo, FileTooSmall.class);

        final List<String> actualConstraints = constraintViolations.stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        assertEquals(List.of("File size must be higher than 1000001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1000001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1000001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1000001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive).", "File size must be higher than 1001 bytes (inclusive) and lower than 9223372036854775807 bytes (inclusive)."), actualConstraints);
    }

    @Test
    void fileTooBig() {
        final Set<ConstraintViolation<MyPojo>> constraintViolations = validator.validate(myPojo, FileTooBig.class);

        final List<String> actualConstraints = constraintViolations.stream()
            .sorted(Comparator.comparing(ConstraintViolation::getMessage))
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
        assertEquals(List.of("File size must be higher than 0 byte (inclusive) and lower than 999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999999 bytes (inclusive).", "File size must be higher than 0 byte (inclusive) and lower than 999999 bytes (inclusive)."), actualConstraints);
    }

    @Test
    void aaaaa() {

    }

    private static interface NotExist {
    }

    private static interface MinLessThan0 {
    }

    private static interface MaxLessThan0 {
    }

    private static interface MaxLessThanMin {
    }

    private static interface MinInclusive {
    }

    private static interface MaxInclusive {
    }

    private static interface FileTooSmall {
    }

    private static interface FileTooBig {
    }

    private static class MyPojo {

        @FileSize(groups = NotExist.class, max = Long.MAX_VALUE)
        private final Path notExistPath = Path.of("fake.txt");

        @FileSize(groups = MinLessThan0.class, min = -1, max = Long.MAX_VALUE)
        @FileSize(groups = MaxLessThan0.class, max = -1)
        @FileSize(groups = MaxLessThanMin.class, min = 2, max = 1)
        @FileSize(groups = FileTooSmall.class, min = 1001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1000)
        private final File file1KB = new File("src/test/resources/1KB.txt");

        @FileSize(groups = FileTooSmall.class, min = 1_000_001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999_999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1_000_000)
        private final File file1MB = new File("src/test/resources/1MB.txt");


        @FileSize(groups = MinLessThan0.class, min = -1, max = Long.MAX_VALUE)
        @FileSize(groups = MaxLessThan0.class, max = -1)
        @FileSize(groups = MaxLessThanMin.class, min = 2, max = 1)
        @FileSize(groups = FileTooSmall.class, min = 1001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1000)
        private final Path path1KB = Path.of("src/test/resources/1KB.txt");

        @FileSize(groups = FileTooSmall.class, min = 1_000_001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999_999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1_000_000)
        private final Path path1MB = Path.of("src/test/resources/1MB.txt");


        @FileSize(groups = MinLessThan0.class, min = -1, max = Long.MAX_VALUE)
        @FileSize(groups = MaxLessThan0.class, max = -1)
        @FileSize(groups = MaxLessThanMin.class, min = 2, max = 1)
        @FileSize(groups = FileTooSmall.class, min = 1001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1000)
        private final MultipartFile multipartFile1KB;

        @FileSize(groups = FileTooSmall.class, min = 1_000_001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999_999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1_000_000)
        private final MultipartFile multipartFile1MB;


        @FileSize(groups = MinLessThan0.class, min = -1, max = Long.MAX_VALUE)
        @FileSize(groups = MaxLessThan0.class, max = -1)
        @FileSize(groups = MaxLessThanMin.class, min = 2, max = 1)
        @FileSize(groups = FileTooSmall.class, min = 1001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1000)
        private final Part partFile1KB;

        @FileSize(groups = FileTooSmall.class, min = 1_000_001, max = Long.MAX_VALUE)
        @FileSize(groups = FileTooBig.class, max = 999_999)
        @FileSize(groups = MinInclusive.class, min = 1000, max = Long.MAX_VALUE)
        @FileSize(groups = MaxInclusive.class, max = 1_000_000)
        private final Part partFile1MB;

        private MyPojo() throws IOException {
            this.multipartFile1KB = new MockMultipartFile("1KB.txt", new FileInputStream(file1KB));
            this.multipartFile1MB = new MockMultipartFile("1MB.txt", new FileInputStream(file1MB));
            this.partFile1KB = new MockPart("1KB.txt", Files.readAllBytes(path1KB));
            this.partFile1MB = new MockPart("1MB.txt", Files.readAllBytes(path1MB));
        }
    }
}
