package es.vytale.milanesa.spigot.listeners;

import es.vytale.milanesa.common.friends.FriendProfile;
import es.vytale.milanesa.common.user.User;
import es.vytale.milanesa.spigot.Milanesa;
import es.vytale.milanesa.spigot.utils.MessageAPI;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class ConnectionListener implements Listener {
    private final Milanesa milanesa;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreConnection(AsyncPlayerPreLoginEvent event) {
        User<Player> user = milanesa.getUserManager().getOrCreate(event.getUniqueId());
        milanesa.getUserDataAccessor().downloadData(user);

        FriendProfile friendProfile = new FriendProfile(user.getUuid());
        milanesa.getFriendProfileAccessor().downloadData(friendProfile);
        user.setFriendProfile(friendProfile);
    }

    @EventHandler
    public void onConnection(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        User<Player> user = milanesa.getUserManager().getOrCreate(player.getUniqueId());
        if (!user.isDownloaded()) {
            player.kickPlayer(MessageAPI.colorize("&bBackend &8> &cHubo un error mientras tu información era cargada, sí esto persiste, contáctate en Discord."));
            return;
        }
        user.setPlayer(player);
    }
}