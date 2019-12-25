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

import org.blackdread.lib.restfilter.criteria.parser.criteria.AliasAnnotationCriteria;
import org.blackdread.lib.restfilter.filter.LongFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.blackdread.lib.restfilter.criteria.parser.CriteriaFieldDataUtil.*;
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

        log.info("result: {}", result);

        assertEquals(8, result.getMethods().size());
        checkValue(result.getMethods(), 0, "getComputed1", "computed1", long.class, "getComputed1Alias", null);
        checkValue(result.getMethods(), 1, "getComputed2", "computed2", Long.class, "getComputed2Alias", null);
        checkFilter(result.getMethods(), 2, "getComputed3", "computed3", LongFilter.class, "getComputed3Alias", null);
        checkValue(result.getMethods(), 3, "specialName", "specialName", long.class, null, null);
        checkValue(result.getMethods(), 4, "specialNameExtra", "specialNameExtra", long.class, "specialNameAlias", null);
        checkValue(result.getMethods(), 5, "withDot", "withDot", long.class, "myFilter.equals", null);
        checkValue(result.getMethods(), 6, "withDotIn", "withDotIn", long.class, "myFilter2.in", null);
        checkList(result.getMethods(), 7, "withDotIn2", "withDotIn2", List.class, "myFilter3.in", Long.class);

        assertEquals(8, result.getFields().size());
        checkArray(result.getFields(), 0, "arrayLongFilter", LongFilter[].class, "arrayLongFilterAlias", null);
        checkArray(result.getFields(), 1, "arrayObjectLong", Long[].class, "arrayObjectLongAlias", null);
        checkArray(result.getFields(), 2, "arrayPrimitiveLong", long[].class, "arrayPrimitiveLongAlias", null);
        checkList(result.getFields(), 3, "listLong", List.class, "listLongAlias", Long.class);
        checkList(result.getFields(), 4, "listLongFilter", List.class, "listLongFilterAlias", null);
        checkFilter(result.getFields(), 5, "longFilter", LongFilter.class, "longFilterAlias", null);
        checkValue(result.getFields(), 6, "objectLong", Long.class, "objectLongAlias", null);
        checkValue(result.getFields(), 7, "primitiveLong", long.class, "primitiveLongAlias", null);
    }

}
