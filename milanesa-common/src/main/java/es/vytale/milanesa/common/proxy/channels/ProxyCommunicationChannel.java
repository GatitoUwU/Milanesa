package es.vytale.milanesa.common.proxy.channels;

import com.google.gson.Gson;
import es.vytale.milanesa.common.proxy.Proxy;
import es.vytale.milanesa.common.proxy.ProxyData;
import es.vytale.milanesa.common.proxy.ProxyManager;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ProxyCommunicationChannel extends MilanesaChannel {
    private final ProxyManager proxyManager;
    private final Gson gson;

    public ProxyCommunicationChannel(ProxyManager proxyManager) {
        super("proxy-communication");
        this.proxyManager = proxyManager;
        this.gson = new Gson();
    }

    @Override
    public void handle(MilanesaMessage milanesaMessage) {
        ProxyData proxyData = gson.fromJson(milanesaMessage.getData(), ProxyData.class);

        if (proxyManager.getSelf() == null || !proxyData.getName().equals(proxyManager.getSelf().getName())) {
            System.out.println("Handling proxy-communication for request: " + milanesaMessage.getData());
            Proxy proxy = proxyManager.getOrCreate(proxyData.getName());
            proxy.loadFromProxyData(proxyData);
        }
    }
}