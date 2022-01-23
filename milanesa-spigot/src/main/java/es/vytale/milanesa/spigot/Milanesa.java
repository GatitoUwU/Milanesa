package es.vytale.milanesa.spigot;

import es.vytale.milanesa.common.executor.NekoExecutor;
import es.vytale.milanesa.common.storage.MilanesaMongoCredentials;
import es.vytale.milanesa.common.storage.MongoHandler;
import es.vytale.milanesa.common.user.User;
import es.vytale.milanesa.common.user.UserDataAccessor;
import es.vytale.milanesa.common.user.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Milanesa extends JavaPlugin {

    private NekoExecutor nekoExecutor;

    private UserManager<Player> userManager;
    private MongoHandler mongoHandler;
    private UserDataAccessor<User<Player>> userDataAccessor;

    @Override
    public void onEnable() {
        getDataFolder().mkdir();

        nekoExecutor = new NekoExecutor();

        mongoHandler = new MongoHandler(MilanesaMongoCredentials.fromFile(new File(getDataFolder(), "mongo-credentials.json")));

        userManager = new UserManager<>();
        userDataAccessor = new UserDataAccessor<>(mongoHandler.getDatabase());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
