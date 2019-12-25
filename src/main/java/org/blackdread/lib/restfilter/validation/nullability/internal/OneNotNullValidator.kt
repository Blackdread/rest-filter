/*
 * MIT License
 *
 * Copyright (c) 2018-2020 Yoann CAPLAIN
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
package org.blackdread.lib.restfilter.validation.nullability.internal

import org.blackdread.lib.restfilter.validation.nullability.OneNotNull
import org.slf4j.LoggerFactory
import org.springframework.util.ReflectionUtils
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * <p>Created on 2019/12/22.</p>
 *
 * @author Yoann CAPLAIN
 */
class OneNotNullValidator : ConstraintValidator<OneNotNull, Any?> {

    private var fieldNames: Array<String> = emptyArray()

    private companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(OneNotNullValidator::class.java)
    }

    override fun initialize(constraintAnnotation: OneNotNull) {
        fieldNames = constraintAnnotation.value
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }
        if (fieldNames.size < 2) {
            throw IllegalArgumentException("Require at least 2 field names passed")
        }
        var isOneNotNullFound = false
        for (fieldName in fieldNames) {
            val field = ReflectionUtils.findField(value.javaClass, fieldName) ?: throw IllegalArgumentException("Could not find field: $fieldName")
            try {
                field.isAccessible = true
                val fieldValue = field[value]
                if (fieldValue != null) {
                    if (isOneNotNullFound) return false
                    isOneNotNullFound = true
                }
            } catch (e: IllegalAccessException) {
                log.error("Failed to get field", e)
                throw IllegalStateException(e)
            }
        }

        return isOneNotNullFound
    }
}
