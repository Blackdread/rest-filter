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
