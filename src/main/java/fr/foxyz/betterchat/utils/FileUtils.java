package fr.foxyz.betterchat.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {

    public static void checkDefaults(Plugin plugin) {
        ChatColorUtils.checkDefaultConfigs(plugin);
    }

    public static YamlConfiguration get(Plugin plugin, String filename, String folder) {
        final YamlConfiguration configuration = new YamlConfiguration();
        final Path file = Paths
                .get(plugin.getDataFolder() + (folder.isEmpty() ? "" : "/") + folder)
                .resolve(filename + (filename.endsWith(".yml") ? "" : ".yml"));

        if (!Files.exists(file)) return null;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            configuration.load(reader);
            return configuration;
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static YamlConfiguration get(Plugin plugin, String filename) {
        return get(plugin, filename, "");
    }

    public static boolean save(Plugin plugin, FileConfiguration configuration, String filename, String folder) {
        final Path path = pathFromStrings(plugin, filename, folder);
        try (BufferedWriter out = Files.newBufferedWriter(
                path, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE
        )) {
            out.write(configuration.saveToString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean save(Plugin plugin, FileConfiguration config, String filename) {
        return save(plugin, config, filename, "");
    }

    public static boolean create(Plugin plugin, String filename) {
        return create(plugin, filename, "");
    }

    public static boolean create(Plugin plugin, String filename, String folder) {
        final Path path = pathFromStrings(plugin, filename, folder);
        try {
            Files.createDirectories(path.getParent());
            if (!Files.exists(path)) {
                Files.createFile(path);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean exists(Plugin plugin, String filename, String folder) {
        final Path file = Paths
                .get(plugin.getDataFolder() + (folder.isEmpty() ? "" : "/") + folder)
                .resolve(filename + (filename.endsWith(".yml") ? "" : ".yml"));

        return Files.exists(file);
    }

    public static boolean exists(Plugin plugin, String filename) {
        return exists(plugin, filename, "");
    }

    private static Path pathFromStrings(Plugin plugin, String filename, String folder) {
        return Paths
                .get(plugin.getDataFolder() + (folder.isEmpty() ? "" : "/") + folder)
                .resolve(filename + (filename.endsWith(".yml") ? "" : ".yml"));
    }
}
