package fr.foxyz.betterchat.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ChatColorUtils {

    public static final String CONFIG_NAME = "colors.yml";

    public static final ChatColor[] BLACKLISTED = new ChatColor[]{
            ChatColor.DARK_BLUE,
            ChatColor.BLACK,
            ChatColor.UNDERLINE,
            ChatColor.STRIKETHROUGH,
            ChatColor.ITALIC,
            ChatColor.BOLD,
            ChatColor.RESET,
    };

    public static final ChatColor[] ALLOWED;

    public static Map<UUID, ChatColor> PLAYER_COLORS = new HashMap<>();

    static {
        ALLOWED = new ChatColor[ChatColor.values().length - BLACKLISTED.length];
        ChatColor[] values = ChatColor.values();
        int c = 0;
        for (final ChatColor color : values) {
            if (Arrays.stream(BLACKLISTED).noneMatch(color::equals)) {
                ALLOWED[c++] = color;
            }
        }
    }

    /**
     * @noinspection ConstantConditions
     */
    public static void loadColor(Plugin plugin, UUID uuid) {
        final YamlConfiguration configuration = FileUtils.get(plugin, CONFIG_NAME);
        if (configuration.contains(uuid.toString())) {
            PLAYER_COLORS.put(
                    uuid,
                    ChatColor.getByChar(configuration.getString(uuid.toString()))
            );
        } else {
            saveColor(
                    configuration,
                    plugin,
                    uuid,
                    getRandom()
            );
        }
    }

    public static void saveColor(Plugin plugin, UUID uuid, ChatColor color) {
        saveColor(
                FileUtils.get(plugin, CONFIG_NAME),
                plugin,
                uuid,
                color
        );
    }

    public static void saveColor(YamlConfiguration configuration, Plugin plugin, UUID uuid, ChatColor color) {
        PLAYER_COLORS.put(uuid, color);
        configuration.set(uuid.toString(), color.getChar());
        FileUtils.save(plugin, configuration, CONFIG_NAME);
    }

    public static ChatColor getColor(UUID uuid) {
        return PLAYER_COLORS.getOrDefault(uuid, getRandom());
    }

    public static void clearCache(UUID uuid) {
        PLAYER_COLORS.remove(uuid);
    }

    public static ChatColor getRandom() {
        return Arrays.stream(ALLOWED)
                .skip(new Random().nextInt(ALLOWED.length))
                .findFirst()
                .orElse(ALLOWED[0]);
    }

    protected static void checkDefaultConfigs(Plugin plugin) {
        if (!FileUtils.exists(plugin, CONFIG_NAME)) {
            FileUtils.create(plugin, CONFIG_NAME);
        }
    }
}
