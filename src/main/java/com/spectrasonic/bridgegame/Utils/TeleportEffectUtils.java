package com.spectrasonic.bridgegame.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Utility class for teleport-related visual effects
 */
public final class TeleportEffectUtils {

    private TeleportEffectUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Creates a DNA-like double helix particle effect at the teleport destination
     * f
     * 
     * @param plugin      The plugin instance
     * @param destination The teleport destination location
     * @param height      The height of the helix
     * @param duration    The duration of the animation in ticks
     */
    public static void createDNAHelix(JavaPlugin plugin, Location destination, double height, int duration) {
        Location location = destination.clone();
        World world = location.getWorld();

        new BukkitRunnable() {
            double y = 0;
            final double maxY = height;
            final double yIncrement = maxY / duration;

            @Override
            public void run() {
                if (y >= maxY) {
                    this.cancel();
                    return;
                }

                // Create DNA-like double helix with two strands
                createHelixStep(world, location, y, Particle.END_ROD, 0); // First strand (white)
                createHelixStep(world, location, y, Particle.DRAGON_BREATH, Math.PI); // Second strand (purple), offset
                                                                                      // by 180 degrees

                y += yIncrement;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    /**
     * Creates a single step of a DNA helix strand
     * 
     * @param world    The world to spawn particles in
     * @param center   The center location of the helix
     * @param y        The current height of the helix
     * @param particle The particle type to use
     * @param offset   The offset angle (to create separate strands)
     */
    private static void createHelixStep(World world, Location center, double y, Particle particle, double offset) {

        double radius = 0.8; // Wider radius for DNA-like appearance
        double rotationSpeed = 2.0; // Increased from 0.4 to 2.0 for more rotations

        // Calculate position on the helix with more rotations
        double angle = (y * rotationSpeed) + offset;
        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);

        // Spawn the main particle
        world.spawnParticle(
                particle,
                center.getX() + x,
                center.getY() + y,
                center.getZ() + z,
                1,
                0, 0, 0,
                0);

        // Add a few smaller particles around the main one to create a thicker strand
        for (int i = 0; i < 2; i++) {
            double smallOffset = 0.1;
            world.spawnParticle(
                    particle,
                    center.getX() + x + (Math.random() * smallOffset - smallOffset / 2),
                    center.getY() + y + (Math.random() * smallOffset - smallOffset / 2),
                    center.getZ() + z + (Math.random() * smallOffset - smallOffset / 2),
                    1,
                    0, 0, 0,
                    0);
        }
    }
}