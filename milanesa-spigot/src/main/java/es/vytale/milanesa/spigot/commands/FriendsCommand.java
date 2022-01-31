package es.vytale.milanesa.spigot.commands;

import es.vytale.milanesa.common.friends.FriendData;
import es.vytale.milanesa.common.friends.FriendProfile;
import es.vytale.milanesa.common.friends.FriendProfileAccessor;
import es.vytale.milanesa.common.user.User;
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
import java.util.Map;
import java.util.UUID;

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
                    //handleList(player);
                    return true;
                }
                if (args[0].equalsIgnoreCase("add")) {
                    //handleAdd(player, args);
                    return true;
                }
            }
        }
        return false;
    }

    public void handleList(Player player) {
        milanesa.getUserManager().get(player.getUniqueId()).ifPresent(user -> {
            if (user.isFriendsLock()) {
                player.sendMessage(MessageAPI.colorize("&bBackend &8> &7Ya estamos procesando una solicitud, espérate a que esta termine..."));
                return;
            }

            user.setFriendsLock(true);

            FriendProfile baseProfile = new FriendProfile(player.getUniqueId());
            milanesa.getFriendProfileAccessor().downloadAsync(milanesa.getNekoExecutor(), baseProfile).addCallback(profileResponse -> {
                user.setFriendsLock(false);
                if (profileResponse.wasSuccessfully()) {
                    System.out.println(baseProfile.getFriendData().toString());
                    System.out.println(baseProfile.getFriendData().getFollowing().getClass().getSimpleName());
                    System.out.println(baseProfile.getFriendData().getFollowers().getClass().getSimpleName());
                    Map<UUID, String> following = baseProfile.getFriendData().getFollowing();

                    if (following.isEmpty()) {
                        player.sendMessage(MessageAPI.colorize("&bAmigos &8> &cNo tienes amigos, triste, pero cierto :c"));
                        return;
                    }

                    List<String> connected = new ArrayList<>();
                    List<String> disconnected = new ArrayList<>();

                    following.forEach((uuid, s) -> {
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
                } else {
                    player.sendMessage(MessageAPI.colorize("&bBackend &8> &cError al procesar la petición..."));
                }
            }, milanesa.getNekoExecutor());
        });
    }

    public void handleAdd(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(MessageAPI.colorize("&bAmigos &8> &7Uso: &a/friend add <nombre>"));
            return;
        }
        Player target = Bukkit.getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(MessageAPI.colorize("&bAmigos &8> &cEl jugador debe estar en el mismo servidor que tú para poder agregarle."));
            return;
        }
        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(MessageAPI.colorize("&bAmigos &8> &cNo puedes agregarte a ti mismo. D:"));
            return;
        }

        User<Player> user = milanesa.getUserManager().get(player.getUniqueId()).orElse(null);

        if (user == null) {
            player.sendMessage(MessageAPI.colorize("&bBackend &8> &7Hubo un error mientras cargábamos tu información, sí el error persiste, reconéctate..."));
            return;
        }

        if (user.isFriendsLock()) {
            player.sendMessage(MessageAPI.colorize("&bBackend &8> &7Ya estamos procesando una solicitud, espérate a que esta termine..."));
            return;
        }

        user.setFriendsLock(true);
        milanesa.getNekoExecutor().submit(() -> {
            FriendProfile playerProfile = new FriendProfile(player.getUniqueId());
            FriendProfile targetProfile = new FriendProfile(target.getUniqueId());

            FriendProfileAccessor fpa = milanesa.getFriendProfileAccessor();
            fpa.downloadAsSync(playerProfile);
            fpa.downloadAsSync(targetProfile);

            FriendData playerFriendData = playerProfile.getFriendData();
            FriendData targetFriendData = targetProfile.getFriendData();


            if (playerFriendData.isFollowing(target.getUniqueId())) {
                player.sendMessage(MessageAPI.colorize("&aYa tienes a " + player.getName() + " en tu lista de amigos."));
            } else {
                player.sendMessage(MessageAPI.colorize("&aHas agregado correctamente a " + player.getName() + " a tu lista de amigos."));
                playerFriendData.getFollowing().put(target.getUniqueId(), target.getName());
                targetFriendData.getFollowers().put(player.getUniqueId(), player.getName());
                fpa.uploadData(playerProfile);
                fpa.uploadData(targetProfile);
            }
            user.setFriendsLock(false);
        });
    }
}