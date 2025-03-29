package com.spectrasonic.bridgegame.Tasks;

import com.spectrasonic.bridgegame.Main;
import com.spectrasonic.bridgegame.Utils.SoundUtils;
import com.spectrasonic.bridgegame.Utils.TeleportEffectUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class TeleportTask extends BukkitRunnable {

    private final Main plugin;
    private final Location teleportDestination;
    private boolean isActive = false;

    public TeleportTask(Main plugin) {
        this.plugin = plugin;

        // Set the teleport destination coordinates
        World world = plugin.getServer().getWorlds().get(0); // Get the main world
        this.teleportDestination = new Location(world, 117, 78, 65);
    }

    @Override
    public void run() {
        // If the task is not active, do nothing
        if (!isActive) {
            return;
        }

        // Check all online players
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            // Get the block the player is standing on
            Location playerLoc = player.getLocation();
            Location blockLoc = playerLoc.clone().subtract(0, 1, 0); // Block below player

            // Check if the block is black stained glass
            if (blockLoc.getBlock().getType() == Material.BLACK_STAINED_GLASS) {
                // Create the double spiral effect at the teleport destination
                TeleportEffectUtils.createDNAHelix(plugin, teleportDestination, 3.0, 20);

                // Play teleport sound
                SoundUtils.playerSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.0f);

                // Teleport the player to the destination
                player.teleport(teleportDestination);
                player.sendMessage("§6¡Has sido teletransportado!");
            }
        });
    }

    /**
     * Activates the teleport task
     */
    public void activate() {
        this.isActive = true;
    }

    /**
     * Deactivates the teleport task
     */
    public void deactivate() {
        this.isActive = false;
    }
}