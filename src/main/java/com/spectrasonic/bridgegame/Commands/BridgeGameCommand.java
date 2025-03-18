package com.spectrasonic.bridgegame.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.spectrasonic.bridgegame.Main;
import com.spectrasonic.bridgegame.Tasks.BridgeGameTask;
import com.spectrasonic.bridgegame.Utils.MessageUtils;
import com.spectrasonic.bridgegame.Utils.ParticleUtils;
import org.bukkit.entity.Player;

@CommandAlias("bridgegame|bg")
public class BridgeGameCommand extends BaseCommand {

    private final Main plugin;

    public BridgeGameCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Subcommand("start")
    @CommandPermission("bridgegame.start")
    public void onStart(Player player) {
        if (plugin.getGameTask() != null) {
            player.sendMessage("El minijuego ya está en ejecución.");
            return;
        }
        player.sendMessage("Iniciando BridgeGame...");
        // Iniciar la tarea repetitiva del minijuego con el jugador como referencia
        BridgeGameTask task = new BridgeGameTask(plugin, player);
        plugin.setGameTask(task);
        task.runTaskTimer(plugin, 0L, 1L); // Se ejecuta cada tick
    }

    @Subcommand("stop")
    @CommandPermission("bridgegame.stop")
    public void onStop(Player player) {
        if (plugin.getGameTask() == null) {
            player.sendMessage("El minijuego no está en ejecución.");
            return;
        }
        // Cancelar la tarea y ejecutar la secuencia final de comandos
        plugin.getGameTask().cancel();
        plugin.setGameTask(null);
        player.sendMessage("Deteniendo BridgeGame...");
        // Usar el flag -q para suprimir los mensajes de FAWE
        player.performCommand("/schematic load puente_visible.schem");
        player.performCommand("/paste -o");
    }

    @Subcommand("debug sphere")
    @CommandPermission("bridgegame.debug")
    @CommandCompletion("5|10|15")
    public void onDebugSphere(Player player, @Single int size) {
        // Validate size
        if (size != 5 && size != 10 && size != 15) {
            MessageUtils.sendMessage(player, "<red>El tamaño debe ser 5, 10 o 15.</red>");
            return;
        }

        // Spawn the particle sphere
        ParticleUtils.spawnEndRodSphereAroundPlayer(player, size);

        // Send confirmation message
        MessageUtils.sendMessage(player,
                "<green>Partículas END_ROD generadas en forma de esfera con radio " + size + ".</green>");
    }
}