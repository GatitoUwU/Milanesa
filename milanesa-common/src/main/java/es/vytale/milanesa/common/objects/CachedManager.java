package es.vytale.milanesa.common.objects;

import java.util.Collection;
import java.util.Optional;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public interface CachedManager<K, V> {
    void invalidate(K k);
    void put(K k, V v);

    Optional<V> get(K k);
    V getOrCreate(K k);
    Collection<V> getAll();
}