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
package org.blackdread.lib.restfilter.validation.nullability

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.ANNOTATION_CLASS
import kotlin.annotation.AnnotationTarget.CLASS
import kotlin.reflect.KClass

/**
 * Check that all fields passed are null or at maximum X fields are not null. Default max is 1 field not null.
 *
 * If any field is not found by reflection, it will throw an exception.
 *
 * At least two field names must be passed otherwise throw exception.
 *
 * Same behavior can be done with [org.hibernate.validator.constraints.ScriptAssert] but here is easier
 *
 * <p>Created on 2019/12/22.</p>
 *
 * @author Yoann CAPLAIN
 * @since 2.2.1
 */
@MustBeDocumented
@Constraint(validatedBy = [])
@Target(ANNOTATION_CLASS, CLASS)
@Retention(RUNTIME)
@Repeatable
annotation class NullOrMaxNotNull(

    val message: String = "{org.blackdread.lib.restfilter.validation.NullOrMaxNotNull.message}",

    val groups: Array<KClass<out Any>> = [],

    val payload: Array<KClass<out Payload>> = [],

    /**
     * Field names
     */
    val value: Array<String> = [],

    /**
     * Cannot be less than 1
     */
    val maxNotNull: Int = 1
)
