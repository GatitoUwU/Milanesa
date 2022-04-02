package es.vytale.milanesa.common.objects;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public abstract class ExpirableCachedManager<K, V> implements CachedManager<K, V> {
    @Getter
    private final Cache<K, V> values;

    public ExpirableCachedManager(Predicate<CacheBuilder<Object, Object>> cacheBuilderPredicate) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilderPredicate.test(cacheBuilder);

        values = cacheBuilder.build();
    }

    public ExpirableCachedManager(Duration duration) {
        this(cacheBuilder -> {
            cacheBuilder.expireAfterAccess(duration);
            return true;
        });
    }

    @Override
    public void invalidate(K k) {
        values.invalidate(k);
    }

    @Override
    public void put(K k, V v) {
        values.put(k , v);
    }

    @Override
    public Optional<V> get(K k) {
        return Optional.ofNullable(values.getIfPresent(k));
    }

    @Override
    public Collection<V> getAll() {
        return values.asMap().values();
    }
}
