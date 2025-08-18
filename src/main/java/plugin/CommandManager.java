// This is free and unencumbered software released into the public domain.
// Author: NotAlexNoyle (admin@true-og.net)

// Declare container package that this class resides in.
package plugin;

import java.io.File;
import java.io.IOException;
import net.trueog.utilitiesog.UtilitiesOG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

// Extends bukkit class to run commands.
public class CommandManager implements CommandExecutor {

    // Keep inheritance of command manager private so nothing else can hook into it
    // and run an unrelated command.
    private static CommandManager instance;

    // Share this instance of the Command Manager with other classes.
    public static CommandManager getInstance() {

        // Pass the instance of this class as an object.
        return instance;

    }

    // Command execution event handler extending bukkit's CommandManager
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // Takes over command execution if plugin is invoked.
        if (cmd.getName().equalsIgnoreCase("tdc")) {

            // Checks to make sure the command is being run in-game and not in the console.
            if (sender instanceof Player) {

                // Convert sender to player once it has been determined that they are one.
                Player player = (Player) sender;

                // Make sure the player has permission first.
                if (player.hasPermission("toggledeathcoordinates.use")) {

                    // Run the function to toggle death coordinates, since all qualifications have
                    // been met.
                    doDeathCoordinateToggle(player);

                } else {

                    // Advertise OG Master to players who don't have it.
                    UtilitiesOG.trueogMessage(player, ToggleDeathCoordinatesOG.getPrefix() + "&cERROR: &6Only"
                            + " &4&lOG Master &6and above can toggle death coordinates being shown in chat with &e/tdc&6. You can upgrade with &B&l/buy&6.");

                }

            }
            // Do nothing if run from console.
            else {

                // Send error message to console.
                UtilitiesOG.logToConsole(ToggleDeathCoordinatesOG.getPrefix(),
                        "ERROR: The console cannot execute that command!");

            }

        }

        // Healthy exit status.
        return true;

    }

    // Declare a function to hold the logic for toggling death coordinates.
    public void doDeathCoordinateToggle(Player player) {

        // Get the player file from the main class.
        File playerFile = ToggleDeathCoordinatesOG.getDisabledPlayers();

        // Convert the file to a YAML object for manipulation.
        YamlConfiguration playerCache = YamlConfiguration.loadConfiguration(playerFile);

        // Get the current state of the player's death coordinates from the YAML.
        boolean toggleState = playerCache.getBoolean((player).getUniqueId().toString());

        // Flip the true/false value to the opposite of what it currently is.
        playerCache.set((player).getUniqueId().toString(), !toggleState);

        // Attempt to run code that throws an error.
        try {

            // Save the YAML modifications to a file.
            playerCache.save(ToggleDeathCoordinatesOG.getDisabledPlayers());

        }
        // Catch a file read/write error if one is thrown.
        catch (IOException e) {

            // Send an error message to the player who tried to toggle their death
            // coordinates.
            UtilitiesOG.trueogMessage(player, ToggleDeathCoordinatesOG.getPrefix()
                    + "&cERROR: ToggleDeathCoordinates encountered a problem. Please report this issue to staff.");

        }

        // If the player's death coordinates are toggled off, do this...
        if (!toggleState) {

            // Send the player a message affirming that their death coordinates have been
            // toggled OFF.
            UtilitiesOG.trueogMessage(player,
                    ToggleDeathCoordinatesOG.getPrefix() + "&6Death Coordinates turned &cOFF&6.");

        }
        // If the player's death coordinates are toggled on, do this...
        else {

            // Send the player a message affirming that their death coordinates have been
            // toggled ON.
            UtilitiesOG.trueogMessage(player,
                    ToggleDeathCoordinatesOG.getPrefix() + "&aDeath Coordinates turned &2ON&a.");

        }

    }

}
