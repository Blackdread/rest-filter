package org.blackdread.lib.restfilter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Just to keep code compatible with java 8, while trying same syntax as java 9+, and allow easy refactoring once drop java 8
 * <p>Created on 2019/10/28.</p>
 *
 * @author Yoann CAPLAIN
 */
public class Map2 {

    public static <K, V> Map<K, V> of(K k1, V v1) {
        final HashMap<K, V> map = new HashMap<>(1);
        map.put(k1, v1);
        return Collections.unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        final HashMap<K, V> map = new HashMap<>(2);
        map.put(k1, v1);
        map.put(k2, v2);
        return Collections.unmodifiableMap(map);
    }

}
