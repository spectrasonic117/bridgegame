package com.spectrasonic.bridgegame.Tasks;

import com.spectrasonic.bridgegame.Main;
// import com.spectrasonic.bridgegame.Utils.MessageUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class BridgeGameTask extends BukkitRunnable {

    private final Main plugin;
    private final Player player;

    private int state = 0;
    private int tickCounter = 0;

    public BridgeGameTask(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
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