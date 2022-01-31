package es.vytale.milanesa.velocity.server;

import com.google.gson.Gson;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import es.vytale.milanesa.common.redis.MilanesaMessageHandler;
import es.vytale.milanesa.common.redis.data.MilanesaChannel;
import es.vytale.milanesa.common.redis.data.MilanesaMessage;
import es.vytale.milanesa.common.server.ServerRegisterMessage;
import es.vytale.milanesa.common.server.ServerUnregisterMessage;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
public class ServerRegistrar {
    public ServerRegistrar(Gson gson, ProxyServer proxyServer, MilanesaMessageHandler milanesaMessageHandler) {
        milanesaMessageHandler.registerChannel(new MilanesaChannel("proxy::register::server") {
            @Override
            public void handle(MilanesaMessage msg) {
                ServerRegisterMessage srm = gson.fromJson(msg.getData(), ServerRegisterMessage.class);
                Optional<RegisteredServer> optionalRs = proxyServer.getServer(srm.getServer());
                if (!optionalRs.isPresent()) {
                    proxyServer.registerServer(
                            new ServerInfo(srm.getServer(), InetSocketAddress.createUnresolved(srm.getAddress(), srm.getPort()))
                    );
                }
            }
        });
        milanesaMessageHandler.registerChannel(new MilanesaChannel("proxy::unregister::server") {
            @Override
            public void handle(MilanesaMessage msg) {
                ServerUnregisterMessage sum = gson.fromJson(msg.getData(), ServerUnregisterMessage.class);
                proxyServer.getServer(sum.getServer()).ifPresent(registeredServer ->
                        proxyServer.unregisterServer(registeredServer.getServerInfo())
                );
            }
        });
    }
}