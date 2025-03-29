package com.spectrasonic.bridgegame.Listeners;

import com.spectrasonic.bridgegame.Utils.MessageUtils;
import com.spectrasonic.bridgegame.Main;
import com.spectrasonic.bridgegame.Utils.PointsManager;
import com.spectrasonic.bridgegame.Utils.SoundUtils;
import org.bukkit.Sound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Location;

public class GameListener implements Listener {
    private final Main plugin;
    private final PointsManager pointsManager;

    public GameListener(Main plugin) {
        this.plugin = plugin;
        this.pointsManager = plugin.getPointsManager();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location to = event.getTo();
        if (to == null)
            return;

        // Check if game is active
        if (plugin.getGameTask() == null) {
            return;
        }

        // Check if standing on LIGHT_WEIGHTED_PRESSURE_PLATE
        Location blockBelow = to.clone().subtract(0, 0, 0);
        if (blockBelow.getBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            // Check if player hasn't received point yet
            if (!plugin.getPlayersWhoGotPoint().contains(player)) {
                SoundUtils.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.0f);
                pointsManager.addPoints(player, 1);
                MessageUtils.sendTitle(
                        player,
                        "<green><bold>Has Llegado",
                        "<bold>+1 Punto",
                        1,
                        1,
                        1);

                // Mark player as having received point
                plugin.getPlayersWhoGotPoint().add(player);
            }
        }
    }
}