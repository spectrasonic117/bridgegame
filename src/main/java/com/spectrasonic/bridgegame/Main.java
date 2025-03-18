package com.spectrasonic.bridgegame;

import com.spectrasonic.bridgegame.Utils.MessageUtils;
import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.bridgegame.Commands.BridgeGameCommand;
import com.spectrasonic.bridgegame.Tasks.BridgeGameTask;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class Main extends JavaPlugin {

    private PaperCommandManager commandManager;
    private BridgeGameTask gameTask; // Tarea del minijuego en ejecución

    @Override
    public void onEnable() {

        registerCommands();
        registerEvents();
        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {

        MessageUtils.sendShutdownMessage(this);
    }

    public void registerCommands() {
        commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new BridgeGameCommand(this));
    }

    public void registerEvents() {
        // Cancelar la tarea si el plugin se deshabilita
        if (gameTask != null) {
            gameTask.cancel();
        }
        getLogger().info("BridgeGame plugin deshabilitado.");
    }
}
