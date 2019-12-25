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
package org.blackdread.lib.restfilter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Optional;

/**
 * <p>Created on 2019/10/27.</p>
 *
 * @author Yoann CAPLAIN
 */
public final class IterableUtil {

    private static final Logger log = LoggerFactory.getLogger(IterableUtil.class);

    private IterableUtil() {
    }

    /**
     * @param iterable iterable
     * @return true of iterator returns false for {@link Iterator#hasNext()}
     */
    public static boolean isEmpty(final Iterable iterable) {
        final Iterator iterator = iterable.iterator();
        return !iterator.hasNext();
    }

    /**
     * @param iterable iterable
     * @return true of iterator returns true for {@link Iterator#hasNext()}
     */
    public static boolean isNotEmpty(final Iterable iterable) {
        final Iterator iterator = iterable.iterator();
        return iterator.hasNext();
    }

    /**
     * Iterable passed should have not side-effect since it will be iterated for its first value if exist
     *
     * @param iterable iterable
     * @return first value if present and <b>not null</b>
     */
    public static <T> Optional<T> getFirstValue(final Iterable<T> iterable) {
        final Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            final T firstValue = iterator.next();
            if (firstValue == null) { // null are forbidden, no further test
                log.warn("Got null in an iterable");
                return Optional.empty();
            }
            return Optional.of(firstValue);
        }
        return Optional.empty();
    }

}
