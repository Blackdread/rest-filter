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
package org.blackdread.lib.restfilter.criteria.parser;

import org.blackdread.lib.restfilter.criteria.parser.criteria.AliasAnnotationCriteria;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.blackdread.lib.restfilter.criteria.parser.CriteriaFieldDataUtil.checkFilter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
class CriteriaFieldParserUtilAliasAnnotationTest {

    private static final Logger log = LoggerFactory.getLogger(CriteriaFieldParserUtilAliasAnnotationTest.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void classesAreCached() {
        final CriteriaData result = CriteriaFieldParserUtil.getCriteriaData(AliasAnnotationCriteria.class);
        final CriteriaData result2 = CriteriaFieldParserUtil.getCriteriaData(AliasAnnotationCriteria.class);

        assertEquals(result, result2);
        assertSame(result, result2);
    }

    @Test
    void annotatedMethodsAndFieldsAreParsed() {
        final AliasAnnotationCriteria criteria = new AliasAnnotationCriteria();

        final CriteriaData result = CriteriaFieldParserUtil.getCriteriaData(criteria);

        assertEquals(6, result.getMethods().size());
        checkFilter(result.getMethods(), 0, "", "", null, null, null);

        assertEquals(1, result.getFields().size());
        CriteriaFieldDataUtil.checkFilter(result.getFields(), 0, "longFilter", LongFilter.class, null, null);
    }
}
