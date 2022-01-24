package es.vytale.milanesa.spigot.commands;

import es.vytale.milanesa.spigot.Milanesa;
import lombok.RequiredArgsConstructor;
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

            user.getFriendProfile().getFollowing().forEach((s, uuid) -> {
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
            // TODO: suggestion of command
        } else {
            // TODO: add command
        }
    }
}