// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle (admin@true-og.net)

// Declare container package that this class resides in.
package plugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.trueog.utilitiesog.UtilitiesOG;

// Declare primary class for plugin.
public final class ToggleDeathCoordinatesOG extends JavaPlugin {

    // Declare plugin instance.
    private static ToggleDeathCoordinatesOG plugin;

    // Declare SimpleYaml object for player cache.
    YamlConfiguration playerCacheYaml;

    // Declare prefix for chat messages.
    static final String prefix = "&8[&2ToggleDeathCoordinates&4-OG&8] ";

    // Declare a container for the player cache in YAML form.
    private static File disabledPlayers;

    // Share chat prefix with other classes.
    public static String getPrefix() {

        // Pass constant String.
        return prefix;

    }

    // Tell bukkit to load the plugin.
    @Override
    public void onEnable() {

        // Set plugin instance.
        plugin = this;

        // Ensure data folder exists
        if (!getDataFolder().exists() && !getDataFolder().mkdirs()) {

            getLogger().severe("Could not create plugin data folder: " + getDataFolder().getAbsolutePath());
            getServer().getPluginManager().disablePlugin(this);
            return;

        }

        final File playerCacheFile = new File(getDataFolder(), "PlayerCache.yml");

        // Extract default PlayerCache.yml from jar.
        if (!playerCacheFile.exists()) {

            saveResource("PlayerCache.yml", false);

        }

        disabledPlayers = playerCacheFile;

        // Set YAML cache to contents of file.
        playerCacheYaml = YamlConfiguration.loadConfiguration(playerCacheFile);
        try {

            playerCacheYaml.save(playerCacheFile);

        } catch (IOException error) {

            UtilitiesOG.logToConsole(prefix, "ERROR: Failed to load player cache YAML file!");
            error.printStackTrace();

        }

        // Load listener class and pass this class to it.
        new Listeners(this);

        // Init commands.
        if (getCommand("tdc") != null) {

            getCommand("tdc").setExecutor(new CommandManager());

        } else {

            getLogger().severe("Command 'tdc' not found in plugin.yml!");

        }

        Bukkit.getConsoleSender().sendMessage("ToggleDeathCoordinates enabled.");

    }

    // Tell bukkit what to do when shutting the server down.
    @Override
    public void onDisable() {

        // Display shutdown message in console.
        Bukkit.getLogger().info("ToggleDeathCoordinates disabled.");

    }

    // Declare a function to share the player cache file with other class.
    public static File getDisabledPlayers() {

        // Pass the player cache file.
        return disabledPlayers;

    }

    // Accessory constructor so that the main class (this) can be referenced from
    // other classes.
    public static ToggleDeathCoordinatesOG getPlugin() {

        // Pass instance of main.
        return plugin;

    }

}