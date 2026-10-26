package com.vexbot.vexmine.mine;

import com.vexbot.vexmine.VexMineAddon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class MiningTaskManager {

    private final VexMineAddon plugin;
    private final Map<UUID, MiningTask> tasks = new HashMap<>();
    private final Map<UUID, MiningBotController> controllers = new HashMap<>();

    public MiningTaskManager(VexMineAddon plugin) {
        this.plugin = plugin;
    }

    public MiningTask createTask(MiningRegion region, String mode, String ownerName) {
        MiningTask task = new MiningTask(UUID.randomUUID(), region, mode, ownerName);
        tasks.put(task.getId(), task);

        MiningBotController controller = new MiningBotController(plugin, task);
        controllers.put(task.getId(), controller);
        controller.start();

        return task;
    }

    public void shutdown() {
        controllers.values().forEach(MiningBotController::cancel);
        controllers.clear();
        tasks.clear();
    }
}
