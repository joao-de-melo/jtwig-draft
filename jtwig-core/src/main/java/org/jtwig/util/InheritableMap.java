package org.jtwig.util;

import com.google.common.base.Optional;

import java.util.Map;

public class InheritableMap<K, V> {
    private final Map<K, V> parentMap;
    private final Map<K, V> localMap;

    public InheritableMap(Map<K, V> parentMap, Map<K, V> localMap) {
        this.parentMap = parentMap;
        this.localMap = localMap;
    }

    public V get(Object key) {
        return Optional.fromNullable(localMap.get(key))
            .or(parentMap.get(key));
    }

    public V put(K key, V value) {
        return localMap.put(key, value);
    }
}
