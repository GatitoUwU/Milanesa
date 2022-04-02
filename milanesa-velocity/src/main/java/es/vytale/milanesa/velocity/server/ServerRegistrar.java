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
import es.vytale.milanesa.velocity.Milanesa;
import es.vytale.milanesa.velocity.balancer.BalancerServers;

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
    public ServerRegistrar(Milanesa milanesa) {
        MilanesaMessageHandler mmh = milanesa.getMilanesaMessageHandler();
        Gson gson = milanesa.getGson();
        ProxyServer ps = milanesa.getProxyServer();

        mmh.registerChannel(new MilanesaChannel("proxy::register::server") {
            @Override
            public void handle(MilanesaMessage msg) {
                ServerRegisterMessage srm = gson.fromJson(msg.getData(), ServerRegisterMessage.class);
                Optional<RegisteredServer> optionalRs = ps.getServer(srm.getServer());
                if (!optionalRs.isPresent()) {
                    RegisteredServer rs = ps.registerServer(
                            new ServerInfo(srm.getServer(), InetSocketAddress.createUnresolved(srm.getAddress(), srm.getPort()))
                    );
                    System.out.println("Registering server: "+srm.getServer() +" for section: "+srm.getSection());
                    if (!srm.getSection().isEmpty()) {
                        BalancerServers balancerServers = milanesa.getBalancerManager().getSections().get(srm.getSection());
                        if (balancerServers != null) {
                            milanesa.getBalancerManager().getServers().put(srm.getServer(), balancerServers);
                            balancerServers.getServers().add(rs);
                        }
                    }
                }
            }
        });
        mmh.registerChannel(new MilanesaChannel("proxy::unregister::server") {
            @Override
            public void handle(MilanesaMessage msg) {
                ServerUnregisterMessage sum = gson.fromJson(msg.getData(), ServerUnregisterMessage.class);
                ps.getServer(sum.getServer()).ifPresent(rs -> {
                            ps.unregisterServer(rs.getServerInfo());
                            BalancerServers balancerServers = milanesa.getBalancerManager().getServers().get(sum.getServer());
                            if (balancerServers != null) {
                                milanesa.getBalancerManager().getServers().remove(sum.getServer());
                                balancerServers.getServers().remove(rs);
                            }
                        }
                );
            }
        });
    }
}