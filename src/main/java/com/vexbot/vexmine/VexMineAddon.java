package com.vexbot.vexmine;

import com.vexbot.vexmine.command.VexBotCommand;
import com.vexbot.vexmine.mine.MiningTaskManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class VexMineAddon extends JavaPlugin {

    private MiningTaskManager taskManager;

    @Override
    public void onEnable() {
        this.taskManager = new MiningTaskManager(this);

        if (getCommand("vexbot") == null) {
            getLogger().severe("The vexbot command is not registered in plugin.yml.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("vexbot").setExecutor(new VexBotCommand(this, taskManager));
        getLogger().info("VexMine addon enabled.");
    }

    @Override
    public void onDisable() {
        if (taskManager != null) {
            taskManager.shutdown();
        }
    }
}
