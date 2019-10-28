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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>Created on 2019/10/26.</p>
 *
 * @author Yoann CAPLAIN
 */
public class CriteriaFieldDataUtil {

    private static final Logger log = LoggerFactory.getLogger(CriteriaFieldDataUtil.class);


    public static CriteriaFieldData checkValue(List<CriteriaFieldData> list, int index, String fieldName, Class fieldType, String formattedAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaFieldData::getFieldName));
        final CriteriaFieldData data = list.get(index);
        assertEquals(fieldName, data.getFieldName());
        assertEquals(fieldType, data.getFieldType());
        assertTrue(data.isValue());
        assertFalse(data.isFilter());
        assertFalse(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(formattedAliasName), data.getFormattedAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(formattedAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaFieldData checkFilter(List<CriteriaFieldData> list, int index, String fieldName, Class fieldType, String formattedAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaFieldData::getFieldName));
        final CriteriaFieldData data = list.get(index);
        assertEquals(fieldName, data.getFieldName());
        assertEquals(fieldType, data.getFieldType());
        assertFalse(data.isValue());
        assertTrue(data.isFilter());
        assertFalse(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(formattedAliasName), data.getFormattedAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(formattedAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaFieldData checkList(List<CriteriaFieldData> list, int index, String fieldName, Class fieldType, String formattedAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaFieldData::getFieldName));
        final CriteriaFieldData data = list.get(index);
        assertEquals(fieldName, data.getFieldName());
        assertEquals(fieldType, data.getFieldType());
        assertFalse(data.isValue());
        assertFalse(data.isFilter());
        assertTrue(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(formattedAliasName), data.getFormattedAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(formattedAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaFieldData checkArray(List<CriteriaFieldData> list, int index, String fieldName, Class fieldType, String formattedAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaFieldData::getFieldName));
        final CriteriaFieldData data = list.get(index);
        assertEquals(fieldName, data.getFieldName());
        assertEquals(fieldType, data.getFieldType());
        assertFalse(data.isValue());
        assertFalse(data.isFilter());
        assertFalse(data.isIterable());
        assertTrue(data.isArray());
        assertEquals(Optional.ofNullable(formattedAliasName), data.getFormattedAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(formattedAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }


    public static CriteriaMethodData checkValue(List<CriteriaMethodData> list, int index, String methodName, String formattedMethodName, Class methodReturnType, String methodAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaMethodData::getMethodName));
        final CriteriaMethodData data = list.get(index);
        assertEquals(methodName, data.getMethodName());
        assertEquals(formattedMethodName, data.getFormattedMethodName());
        assertEquals(methodReturnType, data.getMethodReturnType());
        assertTrue(data.isValue());
        assertFalse(data.isFilter());
        assertFalse(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(methodAliasName), data.getMethodAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(methodAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaMethodData checkFilter(List<CriteriaMethodData> list, int index, String methodName, String formattedMethodName, Class methodReturnType, String methodAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaMethodData::getMethodName));
        final CriteriaMethodData data = list.get(index);
        assertEquals(methodName, data.getMethodName());
        assertEquals(formattedMethodName, data.getFormattedMethodName());
        assertEquals(methodReturnType, data.getMethodReturnType());
        assertFalse(data.isValue());
        assertTrue(data.isFilter());
        assertFalse(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(methodAliasName), data.getMethodAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(methodAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaMethodData checkList(List<CriteriaMethodData> list, int index, String methodName, String formattedMethodName, Class methodReturnType, String methodAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaMethodData::getMethodName));
        final CriteriaMethodData data = list.get(index);
        assertEquals(methodName, data.getMethodName());
        assertEquals(formattedMethodName, data.getFormattedMethodName());
        assertEquals(methodReturnType, data.getMethodReturnType());
        assertFalse(data.isValue());
        assertFalse(data.isFilter());
        assertTrue(data.isIterable());
        assertFalse(data.isArray());
        assertEquals(Optional.ofNullable(methodAliasName), data.getMethodAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(methodAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }

    public static CriteriaMethodData checkArray(List<CriteriaMethodData> list, int index, String methodName, String formattedMethodName, Class methodReturnType, String methodAliasName, Class wrappedType) {
        list.sort(Comparator.comparing(CriteriaMethodData::getMethodName));
        final CriteriaMethodData data = list.get(index);
        assertEquals(methodName, data.getMethodName());
        assertEquals(formattedMethodName, data.getFormattedMethodName());
        assertEquals(methodReturnType, data.getMethodReturnType());
        assertFalse(data.isValue());
        assertFalse(data.isFilter());
        assertFalse(data.isIterable());
        assertTrue(data.isArray());
        assertEquals(Optional.ofNullable(methodAliasName), data.getMethodAliasName());
        assertEquals(Optional.ofNullable(wrappedType), data.getWrappedType());
        if(methodAliasName != null)
            assertTrue(data.getCriteriaAlias().isPresent());
        else
            assertFalse(data.getCriteriaAlias().isPresent());
        if(wrappedType != null)
            assertTrue(data.getWrappedType().isPresent());
        else
            assertFalse(data.getWrappedType().isPresent());
        return data;
    }
}
