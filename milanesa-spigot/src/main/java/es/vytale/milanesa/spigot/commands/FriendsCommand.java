package es.vytale.milanesa.spigot.commands;

import es.vytale.milanesa.common.friends.FriendData;
import es.vytale.milanesa.spigot.Milanesa;
import es.vytale.milanesa.spigot.utils.MessageAPI;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This code has been created by
 * gatogamer#6666 A.K.A. gatogamer.
 * If you want to use my code, please
 * don't remove this messages and
 * give me the credits. Arigato! n.n
 */
@RequiredArgsConstructor
public class FriendsCommand implements CommandExecutor {

    private final Milanesa milanesa;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("list")) {
                    handleList(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    handleAdd(player, args);
                    return true;
                }
            }
        }
        return false;
    }

    public void handleList(Player player) {
        milanesa.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            List<String> connected = new ArrayList<>();
            List<String> disconnected = new ArrayList<>();

            user.getFriendProfile().getFollowing().forEach((uuid, s) -> {
                if (milanesa.getProxyManager().isConnected(s)) {
                    connected.add(s);
                } else {
                    disconnected.add(s);
                }
            });

            connected.forEach(connectedUser ->
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + connectedUser))
            );
            disconnected.forEach(disconnectedUser ->
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + disconnectedUser))
            );
        });
    }

    public void handleAdd(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(MessageAPI.colorize("&7Uso: &a/friend add <nombre>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(MessageAPI.colorize("&cEl jugador debe estar en el mismo servidor que tú para poder agregarle."));
            return;
        }
        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(MessageAPI.colorize("&cNo puedes agregarte a ti mismo. D:"));
            return;
        }
        milanesa.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            FriendData fp = user.getFriendProfile();
            if (fp.isFollowing(target.getUniqueId())) {
                player.sendMessage(MessageAPI.colorize("&aYa tienes a " + player.getName() + " de amigx."));
            } else {
                player.sendMessage(MessageAPI.colorize("&aHas agregado correctamente a " + player.getName() + " a tu lista de amigos."));
                user.getFriendProfile().getFollowing().put(target.getUniqueId(), target.getName());
                milanesa.getUserDataAccessor().uploadData(milanesa.getNekoExecutor(), user);
            }
        });
        milanesa.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            FriendData fp = user.getFriendProfile();
            if (!fp.isFollower(target.getUniqueId())) {
                fp.getFollowers().put(player.getUniqueId(), player.getName());
                milanesa.getUserDataAccessor().uploadData(milanesa.getNekoExecutor(), user);
            }
        });
    }
}