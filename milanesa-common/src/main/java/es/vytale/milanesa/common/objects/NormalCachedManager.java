package es.vytale.milanesa.common.objects;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public abstract class NormalCachedManager<K, V> implements CachedManager<K, V> {
    @Getter
    private final Map<K, V> values = new ConcurrentHashMap<>();

    @Override
    public void put(K k, V v) {
        values.put(k , v);
    }

    @Override
    public void invalidate(K k) {
        values.remove(k);
    }

    @Override
    public Optional<V> get(K k) {
        return Optional.ofNullable(values.get(k));
    }

    @Override
    public Collection<V> getAll() {
        return values.values();
    }
}