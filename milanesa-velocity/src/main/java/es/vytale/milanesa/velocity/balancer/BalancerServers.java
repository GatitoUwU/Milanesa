package es.vytale.milanesa.velocity.balancer;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerPing;
import es.vytale.milanesa.velocity.Milanesa;
import lombok.Getter;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
public class BalancerServers {
    private final Set<RegisteredServer> servers = ConcurrentHashMap.newKeySet();
    private final Map<RegisteredServer, ServerPing> pings = new ConcurrentHashMap<>();

    public BalancerServers(Milanesa milanesa, Set<String> servers) {
        for (String s : servers) {
            Optional<RegisteredServer> rs = milanesa.getProxyServer().getServer(s);
            rs.ifPresent(this.servers::add);
            if (!rs.isPresent()) {
                milanesa.getLogger().error("Balancer > " + s + " is not a registered server.");
            }
        }
        milanesa.getProxyServer().getScheduler().buildTask(milanesa, () ->
                this.servers.forEach(s -> s.ping().thenAccept(serverPing -> pings.put(s, serverPing)))
        ).repeat(500L, TimeUnit.MILLISECONDS).schedule();
    }

    public RegisteredServer getLeastUserServer() {
        return servers.stream().filter(rs -> {
            Optional<ServerPing> serverPingOptional = Optional.ofNullable(pings.get(rs));
            int players = serverPingOptional.map(serverPing -> serverPing.getPlayers().map(ServerPing.Players::getOnline).orElse(rs.getPlayersConnected().size())).orElse(rs.getPlayersConnected().size());
            int max = serverPingOptional.map(serverPing -> serverPing.getPlayers().map(ServerPing.Players::getMax).orElse(100)).orElse(100);
            return max > players; // ensures server is not full.
        }).min(Comparator.comparing(rs -> rs.getPlayersConnected().size())).orElse(null);
    }

    public RegisteredServer getLeastUserServer(int joining) {
        return servers.stream().filter(rs -> {
            Optional<ServerPing> serverPingOptional = Optional.ofNullable(pings.get(rs));
            int players = serverPingOptional.map(serverPing -> serverPing.getPlayers().map(ServerPing.Players::getOnline).orElse(rs.getPlayersConnected().size())).orElse(rs.getPlayersConnected().size()) + joining;
            int max = serverPingOptional.map(serverPing -> serverPing.getPlayers().map(ServerPing.Players::getMax).orElse(100)).orElse(100);
            return max > players; // ensures server is not full.
        }).min(Comparator.comparing(rs -> rs.getPlayersConnected().size())).orElse(null);
    }
}