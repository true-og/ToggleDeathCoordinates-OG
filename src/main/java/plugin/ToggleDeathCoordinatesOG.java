// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle (admin@true-og.net)

// Declare container package that this class resides in.
package plugin;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

// Declare primary class for plugin.
public final class ToggleDeathCoordinatesOG extends JavaPlugin {

    // Declare plugin instance.
    private static ToggleDeathCoordinatesOG plugin;

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

        File existingPlayerCache = null;
        try {

            existingPlayerCache = new File(this.getDataFolder(), "PlayerCache.yml");

            if (!existingPlayerCache.exists()) {

                existingPlayerCache.createNewFile();

            }

        } catch (IOException error) {

            this.getLogger().severe("Something went wrong when creating the player cache file!");

        }

        // Pass file to other classes.
        disabledPlayers = existingPlayerCache;
        // Set YAML cache to contents of file.
        YamlConfiguration.loadConfiguration(existingPlayerCache);

        // Load listener class and pass this class to it.
        new Listeners(this);

        // Run command when plugin event is triggered.
        this.getCommand("tdc").setExecutor(new CommandManager());

        // Show a startup message in the console.
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

    // Accessor constructor so that the main class (this) can be referenced from
    // other classes.
    public static ToggleDeathCoordinatesOG getPlugin() {

        // Pass instance of main.
        return plugin;

    }

}
