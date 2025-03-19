package com.spectrasonic.bridgegame.Tasks;

import com.spectrasonic.bridgegame.Main;
// import com.spectrasonic.bridgegame.Utils.MessageUtils;
import com.spectrasonic.bridgegame.Utils.ParticleUtils;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class BridgeGameTask extends BukkitRunnable {

    private final Main plugin;
    private final Player player;

    private int state = 0;
    private int tickCounter = 0;

    // Predefined positions for particle spheres
    private Location pos1;
    private Location pos2;
    private Location pos3;
    private Location pos4;
    private Location pos5;

    public BridgeGameTask(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        // Initialize the predefined positions
        World world = player.getWorld();
        pos1 = new Location(world, 100, 78, 65);
        pos2 = new Location(world, 75, 78, 60);
        pos3 = new Location(world, 55, 78, 60);
        pos4 = new Location(world, 72, 78, 93);
        pos5 = new Location(world, 75, 78, 35);
    }

    /**
     * Spawns particle spheres at the predefined positions
     * 
     * @param size The size of the spheres (5, 10, or 15)
     */
    private void spawnParticleSpheres(int size) {
        ParticleUtils.spawnParticleSphere(pos1, size);
        ParticleUtils.spawnParticleSphere(pos2, size);
        ParticleUtils.spawnParticleSphere(pos3, size);
        ParticleUtils.spawnParticleSphere(pos4, size);
        ParticleUtils.spawnParticleSphere(pos5, size);
    }

    /**
     * Spawns a particle sphere at a specific position
     * 
     * @param position The position number (1-5)
     * @param size     The size of the sphere (5, 10, or 15)
     */
    private void spawnParticleSphere(int position, int size) {
        switch (position) {
            case 1 -> ParticleUtils.spawnParticleSphere(pos1, size);
            case 2 -> ParticleUtils.spawnParticleSphere(pos2, size);
            case 3 -> ParticleUtils.spawnParticleSphere(pos3, size);
            case 4 -> ParticleUtils.spawnParticleSphere(pos4, size);
            case 5 -> ParticleUtils.spawnParticleSphere(pos5, size);
        }
    }

    @Override
    public void run() {
        // Si el jugador se desconecta se cancela la tarea.
        if (!player.isOnline()) {
            cancel();
            plugin.setGameTask(null);
            return;
        }

        // Verificar que el jugador sea operador
        if (!player.isOp()) {
            player.sendMessage("§cNecesitas ser operador para ejecutar esta tarea.");
            cancel();
            plugin.setGameTask(null);
            return;
        }

        switch (state) {
            case 0 -> {
                // Cargar y pegar puente_barriers.schem
                player.performCommand("/schematic load puente_barriers.schem");
                player.performCommand("/paste -o");

                // Spawn particles at position 1
                state = 1;
                tickCounter = 0;
            }
            case 1 -> {
                // Esperar 3 segundos (60 ticks)
                tickCounter++;
                if (tickCounter >= 60) {
                    state = 2;
                }
            }
            case 2 -> {
                // Cargar y pegar step_2.schem
                player.performCommand("/schematic load step_2.schem");
                player.performCommand("/paste -o");
                spawnParticleSphere(1, 5);
                spawnParticleSphere(2, 5);
                spawnParticleSphere(3, 5);
                state = 3;
                tickCounter = 0;
            }
            case 3 -> {
                // Esperar 2 segundos (40 ticks)
                tickCounter++;
                if (tickCounter >= 40) {
                    state = 4;
                }
            }
            case 4 -> {
                // Cargar y pegar step_1.schem
                player.performCommand("/schematic load step_1.schem");
                player.performCommand("/paste -o");
                spawnParticleSphere(1, 10);
                spawnParticleSphere(2, 10);
                spawnParticleSphere(3, 10);
                spawnParticleSphere(4, 5);
                spawnParticleSphere(5, 5);
                state = 5;
                tickCounter = 0;
            }
            case 5 -> {
                // Esperar 2 segundos (40 ticks)
                tickCounter++;
                if (tickCounter >= 40) {
                    state = 6;
                }
            }
            case 6 -> {
                // Cargar y pegar step_3.schem
                player.performCommand("/schematic load step_3.schem");
                player.performCommand("/paste -o");
                spawnParticleSphere(1, 15);
                spawnParticleSphere(2, 15);
                spawnParticleSphere(3, 15);
                spawnParticleSphere(4, 10);
                spawnParticleSphere(5, 10);
                state = 7;
                tickCounter = 0;
            }
            case 7 -> {
                // Esperar 5 segundos (100 ticks)
                tickCounter++;
                if (tickCounter >= 100) {
                    state = 8;
                }
            }
            case 8 -> {
                // Cargar y pegar puente_barriers.schem
                player.performCommand("/schematic load puente_barriers.schem");
                player.performCommand("/paste -o");
                state = 9;
                tickCounter = 0;
            }
            case 9 -> {
                // Esperar 2 segundos y reiniciar el ciclo
                tickCounter++;
                if (tickCounter >= 40) {
                    state = 0; // Reiniciar el ciclo
                }
            }
        }
    }
}