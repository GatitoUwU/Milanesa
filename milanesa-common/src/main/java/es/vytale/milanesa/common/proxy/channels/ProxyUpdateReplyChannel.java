package es.vytale.milanesa.common.proxy.channels;

import com.google.gson.Gson;
import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.proxy.ProxyData;
import es.vytale.milanesa.common.proxy.ProxyManager;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ProxyUpdateReplyChannel extends MilanesaChannel {
    private final ProxyManager proxyManager;
    private final NekoExecutor nekoExecutor;
    private final MilanesaMessageHandler milanesaMessageHandler;
    private final Gson gson;

    public ProxyUpdateReplyChannel(ProxyManager proxyManager, NekoExecutor nekoExecutor, MilanesaMessageHandler milanesaMessageHandler) {
        super("proxy-update-request");
        this.proxyManager = proxyManager;
        this.nekoExecutor = nekoExecutor;
        this.milanesaMessageHandler = milanesaMessageHandler;
        gson = new Gson();
    }

    @Override
    public void handle(MilanesaMessage milanesaMessage) {
        System.out.println("Handling proxy-update-request for request: " + milanesaMessage.getData());

        if (!milanesaMessage.getData().equals(proxyManager.getActualProxy())) {
            String msg = gson.toJson(ProxyData.fromProxy(proxyManager.getSelf()));
            System.out.println("Replying proxy-update-request for request: " + milanesaMessage.getData() + " with " + msg);
            nekoExecutor.submit(() ->
                    milanesaMessageHandler.sendMessage("proxy-communication", msg)
            );
        }
    }
}
