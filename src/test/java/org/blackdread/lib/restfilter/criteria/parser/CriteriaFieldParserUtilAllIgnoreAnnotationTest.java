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
package org.blackdread.lib.restfilter.criteria.parser;

import org.blackdread.lib.restfilter.List2;
import org.blackdread.lib.restfilter.criteria.parser.criteria.AllIgnoreAnnotationCriteria;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaFieldParserUtilAllIgnoreAnnotationTest {

    private static final Logger log = LoggerFactory.getLogger(CriteriaFieldParserUtilAllIgnoreAnnotationTest.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void classesAreCached() {
        final CriteriaData result = CriteriaFieldParserUtil.getCriteriaData(AllIgnoreAnnotationCriteria.class);
        final CriteriaData result2 = CriteriaFieldParserUtil.getCriteriaData(AllIgnoreAnnotationCriteria.class);

        assertEquals(result, result2);
        assertSame(result, result2);
    }

    @Test
    void emptyDataWhenAllIgnored() {
        final AllIgnoreAnnotationCriteria criteria = new AllIgnoreAnnotationCriteria();

        final CriteriaData result = CriteriaFieldParserUtil.getCriteriaData(criteria);

        assertEquals(List2.of(), result.getFields());
        assertEquals(List2.of(), result.getMethods());
    }
}
