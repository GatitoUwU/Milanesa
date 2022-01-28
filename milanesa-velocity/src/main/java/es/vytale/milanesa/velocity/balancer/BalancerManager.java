package es.vytale.milanesa.velocity.balancer;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import es.vytale.milanesa.velocity.Milanesa;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@Getter
public class BalancerManager {
    private final Milanesa milanesa;
    private final BalancerConfig balancerConfig;

    private final Map<String, BalancerServers> servers = new ConcurrentHashMap<>();
    private final Map<String, BalancerServers> serversSections = new ConcurrentHashMap<>();

    public BalancerManager(Milanesa milanesa) {
        this.milanesa = milanesa;
        this.balancerConfig = BalancerConfig.fromFile(milanesa.getFile("balancer.json"));

        Preconditions.checkNotNull(this.balancerConfig, "Balancer config is null!");
        Preconditions.checkNotNull(this.balancerConfig.getBalancers(), "Balancers on config are null!");

        this.balancerConfig.getBalancers().forEach(balancerData -> {
            BalancerServers balancerServers = new BalancerServers(milanesa, balancerData.getServers());
            servers.put(balancerData.getName(), balancerServers);
            balancerServers.getServers().forEach(registeredServer ->
                    serversSections.put(registeredServer.getServerInfo().getName(), balancerServers)
            );
        });
    }

    public RegisteredServer getBalancedServer(String server) {
        BalancerServers balancerServers = serversSections.get(server);
        if (balancerServers != null) {
            return balancerServers.getLeastUserServer();
        }
        return null;
    }

    public RegisteredServer getBalancedServer(String server, int joining) {
        BalancerServers balancerServers = serversSections.get(server);
        if (balancerServers != null) {
            return balancerServers.getLeastUserServer(joining);
        }
        return null;
    }

    public RegisteredServer getBalancedSection(String server) {
        BalancerServers balancerServers = servers.get(server);
        if (balancerServers != null) {
            return balancerServers.getLeastUserServer();
        }
        return null;
    }

    public RegisteredServer getBalancedSection(String server, int joining) {
        BalancerServers balancerServers = servers.get(server);
        if (balancerServers != null) {
            return balancerServers.getLeastUserServer(joining);
        }
        return null;
    }

    public RegisteredServer getDefaultServer() {
        return getBalancedSection(getBalancerConfig().getDefaultSection());
    }

    public RegisteredServer getDefaultServer(int joining) {
        return getBalancedSection(getBalancerConfig().getDefaultSection(), joining);
    }

}