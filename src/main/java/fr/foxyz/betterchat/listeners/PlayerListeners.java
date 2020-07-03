package fr.foxyz.betterchat.listeners;

import fr.foxyz.betterchat.utils.ChatColorUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 * @noinspection unused
 */
public class PlayerListeners implements Listener {
    private final Plugin plugin;

    public PlayerListeners(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void on(AsyncPlayerPreLoginEvent event) {
        ChatColorUtils.loadColor(plugin, event.getUniqueId());
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        ChatColorUtils.clearCache(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void on(AsyncPlayerChatEvent event) {
        final Player sender = event.getPlayer();
        final String message = event.getMessage();

        final ChatColor color = ChatColorUtils.getColor(sender.getUniqueId());

        event.setFormat(color + sender.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
    }
}
