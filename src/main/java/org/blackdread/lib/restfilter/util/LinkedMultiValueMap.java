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
package org.blackdread.lib.restfilter.util;


import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple implementation of {@link MultiValueMap} that wraps a {@link LinkedHashMap},
 * storing multiple values in a {@link LinkedList}.
 * <p>
 * Not thread-safe.
 * <p>
 * Same as "org.springframework.util.LinkedMultiValueMap" but copied to limit dependency on Spring in some parts of library.
 *
 * <p>Created on 2019/10/20.</p>
 *
 * @param <K> the key type
 * @param <V> the value element type
 * @author Yoann CAPLAIN
 * @since 2.1
 */
public class LinkedMultiValueMap<K, V> implements MultiValueMap<K, V>, Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private final Map<K, List<V>> targetMap;


    /**
     * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}.
     */
    public LinkedMultiValueMap() {
        this.targetMap = new LinkedHashMap<>();
    }

    /**
     * Create a new LinkedMultiValueMap that wraps a {@link LinkedHashMap}
     * with the given initial capacity.
     *
     * @param initialCapacity the initial capacity
     */
    public LinkedMultiValueMap(int initialCapacity) {
        this.targetMap = new LinkedHashMap<>(initialCapacity);
    }

    /**
     * Copy constructor: Create a new LinkedMultiValueMap with the same mappings as
     * the specified Map. Note that this will be a shallow copy; its value-holding
     * List entries will get reused and therefore cannot get modified independently.
     *
     * @param otherMap the Map whose mappings are to be placed in this Map
     * @see #clone()
     * @see #deepCopy()
     */
    public LinkedMultiValueMap(Map<K, List<V>> otherMap) {
        this.targetMap = new LinkedHashMap<>(otherMap);
    }


    // MultiValueMap implementation

    @Override
    @Nullable
    public V getFirst(K key) {
        List<V> values = this.targetMap.get(key);
        return (values != null && !values.isEmpty() ? values.get(0) : null);
    }

    @Override
    public void add(K key, @Nullable V value) {
        List<V> values = this.targetMap.computeIfAbsent(key, k -> new LinkedList<>());
        values.add(value);
    }

    @Override
    public void addAll(K key, List<? extends V> values) {
        List<V> currentValues = this.targetMap.computeIfAbsent(key, k -> new LinkedList<>());
        currentValues.addAll(values);
    }

    @Override
    public void addAll(MultiValueMap<K, V> values) {
        for (Entry<K, List<V>> entry : values.entrySet()) {
            addAll(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void set(K key, @Nullable V value) {
        List<V> values = new LinkedList<>();
        values.add(value);
        this.targetMap.put(key, values);
    }

    @Override
    public void setAll(Map<K, V> values) {
        values.forEach(this::set);
    }

    @Override
    public Map<K, V> toSingleValueMap() {
        LinkedHashMap<K, V> singleValueMap = new LinkedHashMap<>(this.targetMap.size());
        this.targetMap.forEach((key, values) -> {
            if (values != null && !values.isEmpty()) {
                singleValueMap.put(key, values.get(0));
            }
        });
        return singleValueMap;
    }


    // Map implementation

    @Override
    public int size() {
        return this.targetMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.targetMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.targetMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.targetMap.containsValue(value);
    }

    @Override
    @Nullable
    public List<V> get(Object key) {
        return this.targetMap.get(key);
    }

    @Override
    @Nullable
    public List<V> put(K key, List<V> value) {
        return this.targetMap.put(key, value);
    }

    @Override
    @Nullable
    public List<V> remove(Object key) {
        return this.targetMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends List<V>> map) {
        this.targetMap.putAll(map);
    }

    @Override
    public void clear() {
        this.targetMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return this.targetMap.keySet();
    }

    @Override
    public Collection<List<V>> values() {
        return this.targetMap.values();
    }

    @Override
    public Set<Entry<K, List<V>>> entrySet() {
        return this.targetMap.entrySet();
    }


    /**
     * Create a deep copy of this Map.
     *
     * @return a copy of this Map, including a copy of each value-holding List entry
     * (consistently using an independent modifiable {@link LinkedList} for each entry)
     * along the lines of {@code MultiValueMap.addAll} semantics
     * @see #addAll(MultiValueMap)
     * @see #clone()
     */
    public LinkedMultiValueMap<K, V> deepCopy() {
        LinkedMultiValueMap<K, V> copy = new LinkedMultiValueMap<>(this.targetMap.size());
        this.targetMap.forEach((key, value) -> copy.put(key, new LinkedList<>(value)));
        return copy;
    }

    /**
     * Create a regular copy of this Map.
     *
     * @return a shallow copy of this Map, reusing this Map's value-holding List entries
     * (even if some entries are shared or unmodifiable) along the lines of standard
     * {@code Map.put} semantics
     * @see #put(Object, List)
     * @see #putAll(Map)
     * @see LinkedMultiValueMap#LinkedMultiValueMap(Map)
     * @see #deepCopy()
     */
    @Override
    public LinkedMultiValueMap<K, V> clone() {
        return new LinkedMultiValueMap<>(this);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.targetMap.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.targetMap.hashCode();
    }

    @Override
    public String toString() {
        return this.targetMap.toString();
    }

}
