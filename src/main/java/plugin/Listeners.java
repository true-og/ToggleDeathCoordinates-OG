// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle (admin@true-og.net)

// Declare container package that this class resides in.
package plugin;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.trueog.utilitiesog.UtilitiesOG;

// Declare listener class.
public class Listeners implements Listener {

    // Declare class constructor.
    public Listeners(ToggleDeathCoordinatesOG plugin) {

        // Receive instance of primary class.
        Bukkit.getPluginManager().registerEvents(this, plugin);

    }

    // Listen for a death event in-game.
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        // Get player from event.
        final Player player = event.getEntity();

        // Get player's death location.
        final Location eyeLocation = player.getEyeLocation();

        // Declare death message with formatting and coordinates.
        final String deathMsg = (" died at &eX = " + eyeLocation.getBlockX() + ", Y = " + eyeLocation.getBlockY()
                + ", Z = " + eyeLocation.getBlockZ() + "&6.");

        // Log player's death coordinates in console.
        UtilitiesOG.logToConsole(ToggleDeathCoordinatesOG.getPrefix(), player.getName() + deathMsg);

        // If death coordinates are enabled for the given player, do this...
        if (!deathCoordinatesAreDisabled(player)) {

            // Send the player their death coordinates in chat.
            UtilitiesOG.trueogMessage(player, ToggleDeathCoordinatesOG.getPrefix() + "&6You" + deathMsg);

        }

        // If the player does not have permission (ie. is not OG Master), do this.
        if (!player.hasPermission("toggledeathcoordinates.use")) {

            // Advertise OG Master to players who don't have it.
            UtilitiesOG.trueogMessage(player, ToggleDeathCoordinatesOG.getPrefix()
                    + " &4&lOG Master &6and above can toggle death coordinates being shown in chat with &e/tdc&6. You can upgrade with &B&l/buy&6.");

        }

    }

    // Declare a function to tell whether or not death coordinates are on for a
    // given player.
    public boolean deathCoordinatesAreDisabled(Player player) {

        final File playerFile = ToggleDeathCoordinatesOG.getDisabledPlayers();
        final YamlConfiguration playerCache = YamlConfiguration.loadConfiguration(playerFile);

        return playerCache.getBoolean(player.getUniqueId().toString(), false);

    }

}