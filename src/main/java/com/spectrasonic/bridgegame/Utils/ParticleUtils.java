package com.spectrasonic.bridgegame.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Utility class for generating particle effects
 */
public final class ParticleUtils {

    private ParticleUtils() {
        // Private constructor to prevent instantiation
    }

    /**
     * Spawns particles in a sphere shape
     * 
     * @param center   The center location of the sphere
     * @param radius   The radius of the sphere
     * @param particle The particle type to spawn
     * @param count    The number of particles to spawn
     */
    public static void spawnSphere(Location center, double radius, Particle particle, int count) {
        World world = center.getWorld();

        // Calculate how many particles to use based on the surface area
        // This ensures larger spheres have appropriate particle density
        int particleCount = (int) (count * (radius / 5.0));

        for (int i = 0; i < particleCount; i++) {
            // Generate random spherical coordinates
            double phi = Math.random() * Math.PI * 2; // Random angle around Y axis (0 to 2π)
            double theta = Math.random() * Math.PI; // Random angle from Y axis (0 to π)

            // Convert spherical coordinates to Cartesian coordinates
            double x = radius * Math.sin(theta) * Math.cos(phi);
            double y = radius * Math.sin(theta) * Math.sin(phi);
            double z = radius * Math.cos(theta);

            // Create the particle at the calculated position
            world.spawnParticle(
                    particle,
                    center.getX() + x,
                    center.getY() + y,
                    center.getZ() + z,
                    1, // Count per location
                    0, 0, 0, // Offset (0 for precise placement)
                    0 // Speed (0 for stationary particles)
            );
        }
    }

    /**
     * Spawns particles in a sphere shape around a player
     * 
     * @param player   The player to spawn particles around
     * @param radius   The radius of the sphere
     * @param particle The particle type to spawn
     * @param count    The number of particles to spawn
     */
    public static void spawnSphereAroundPlayer(Player player, double radius, Particle particle, int count) {
        spawnSphere(player.getLocation(), radius, particle, count);
    }

    /**
     * Spawns END_ROD particles in a sphere shape with predefined sizes
     * 
     * @param center The center location of the sphere
     * @param size   The size of the sphere (5, 10, or 15)
     */
    public static void spawnEndRodSphere(Location center, int size) {
        // Validate size
        int radius;
        int particleCount;

        switch (size) {
            case 5:
                radius = 5;
                particleCount = 100;
                break;
            case 10:
                radius = 10;
                particleCount = 200;
                break;
            case 15:
                radius = 15;
                particleCount = 300;
                break;
            default:
                radius = 5;
                particleCount = 100;
        }

        spawnSphere(center, radius, Particle.END_ROD, particleCount);
    }

    /**
     * Spawns END_ROD particles in a sphere shape around a player with predefined
     * sizes
     * 
     * @param player The player to spawn particles around
     * @param size   The size of the sphere (5, 10, or 15)
     */
    public static void spawnEndRodSphereAroundPlayer(Player player, int size) {
        spawnEndRodSphere(player.getLocation(), size);
    }
}