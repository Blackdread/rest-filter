package org.blackdread.lib.restfilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Just to keep code compatible with java 8, while trying same syntax as java 9+, and allow easy refactoring once drop java 8
 * <p>Created on 2019/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class List2 {

    public static <T> List<T> of(T... values) {
        final ArrayList<T> list = new ArrayList<>(values.length);
        for (final T value : values) {
            list.add(value);
        }
        return list;
    }

}
