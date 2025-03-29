package com.spectrasonic.bridgegame;

import com.spectrasonic.bridgegame.Utils.MessageUtils;
import com.spectrasonic.bridgegame.Listeners.GameListener;
import com.spectrasonic.bridgegame.Utils.PointsManager;
import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.bridgegame.Commands.BridgeGameCommand;
import com.spectrasonic.bridgegame.Tasks.BridgeGameTask;
import com.spectrasonic.bridgegame.Tasks.TeleportTask;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public final class Main extends JavaPlugin {

    private Set<Player> playersWhoGotPoint = new HashSet<>();
    private PointsManager pointsManager;

    private PaperCommandManager commandManager;
    private BridgeGameTask gameTask;
    private TeleportTask teleportTask;

    @Override
    public void onEnable() {

        teleportTask = new TeleportTask(this);
        teleportTask.runTaskTimer(this, 0L, 10L);
        this.pointsManager = new PointsManager(this);

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
        getServer().getPluginManager().registerEvents(new GameListener(this), this);
        // Cancelar la tarea si el plugin se deshabilita
    }
}
