package com.spectrasonic.bridgegame.Tasks;

import com.spectrasonic.bridgegame.Main;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
public class BridgeGameTask extends BukkitRunnable {

    private final Main plugin;
    private final Player player;
    // Estado de la secuencia:
    // 0: Ejecutar primera secuencia de comandos.
    // 1: Esperar 5 segundos antes de la siguiente acción.
    // 2: Ejecutar segunda secuencia de comandos.
    // 3: Esperar 5 segundos antes de reiniciar el ciclo.
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
                // Ejecutar primera secuencia de comandos: cargar y pegar schematic.
                player.performCommand("/schematic loadall puente_barriers.schem");
                player.performCommand("/paste -o");
                state = 1;
                tickCounter = 0;
            }
            case 1 -> {
                // Contador para 5 segundos (100 ticks)
                tickCounter++;
                if (tickCounter >= 100) {
                    state = 2;
                }
            }
            case 2 -> {
                // Ejecutar segunda secuencia de comandos.
                player.performCommand("/schematic loadall puente_visible.schem");
                player.performCommand("/paste -o");
                state = 3;
                tickCounter = 0;
            }
            case 3 -> {
                // Esperar 5 segundos y reiniciar el ciclo.
                tickCounter++;
                if (tickCounter >= 100) {
                    state = 0;
                }
            }
        }
    }
}
