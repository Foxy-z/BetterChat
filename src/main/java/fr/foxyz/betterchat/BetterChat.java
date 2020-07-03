package fr.foxyz.betterchat;

import fr.foxyz.betterchat.listeners.PlayerListeners;
import fr.foxyz.betterchat.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterChat extends JavaPlugin {

    @Override
    public void onEnable() {
        // create default configurations
        FileUtils.checkDefaults(this);

        // register listeners
        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListeners(this), this);
    }

    @Override
    public void onDisable() {

    }
}
