package es.vytale.milanesa.common.proxy;

import es.vytale.milanesa.common.objects.CachedManager;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ProxyManager extends CachedManager<String, Proxy> {
    @Override
    public Proxy getOrCreate(String s) {
        return getValues().computeIfAbsent(s, ignored -> new Proxy(s));
    }
}