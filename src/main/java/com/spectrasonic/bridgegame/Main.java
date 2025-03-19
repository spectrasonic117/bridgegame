package com.spectrasonic.bridgegame;

import com.spectrasonic.bridgegame.Utils.MessageUtils;
import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.bridgegame.Commands.BridgeGameCommand;
import com.spectrasonic.bridgegame.Tasks.BridgeGameTask;
import com.spectrasonic.bridgegame.Tasks.TeleportTask;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class Main extends JavaPlugin {

    private PaperCommandManager commandManager;
    private BridgeGameTask gameTask;
    private TeleportTask teleportTask;

    @Override
    public void onEnable() {

        teleportTask = new TeleportTask(this);
        teleportTask.runTaskTimer(this, 0L, 10L);

        registerCommands();
        registerEvents();

        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {

        if (gameTask != null) {
            gameTask.cancel();
        }

        if (teleportTask != null) {
            teleportTask.cancel();
        }

        MessageUtils.sendShutdownMessage(this);
    }

    public void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new BridgeGameCommand(this));
    }

    public void registerEvents() {
        // Cancelar la tarea si el plugin se deshabilita
    }
}
