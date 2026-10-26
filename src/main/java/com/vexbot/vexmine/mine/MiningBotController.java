package com.vexbot.vexmine.mine;

import com.vexbot.vexmine.VexMineAddon;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitTask;

public final class MiningBotController {

    private final VexMineAddon plugin;
    private final MiningTask task;
    private BukkitTask schedulerTask;

    public MiningBotController(VexMineAddon plugin, MiningTask task) {
        this.plugin = plugin;
        this.task = task;
    }

    public void start() {
        if (schedulerTask != null) {
            return;
        }

        schedulerTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, 1L, 1L);
    }

    public void cancel() {
        if (schedulerTask != null) {
            schedulerTask.cancel();
            schedulerTask = null;
        }
        task.setActive(false);
    }

    private void tick() {
        if (!task.isActive()) {
            cancel();
            return;
        }

        if (!mineNextBlock()) {
            plugin.getLogger().info("Mining task finished: " + task.getId());
            task.setActive(false);
            cancel();
        }
    }

    private boolean mineNextBlock() {
        MiningRegion region = task.getRegion();

        for (int y = region.getMinY(); y <= region.getMaxY(); y++) {
            for (int x = region.getMinX(); x <= region.getMaxX(); x++) {
                for (int z = region.getMinZ(); z <= region.getMaxZ(); z++) {
                    Block block = region.getWorld().getBlockAt(x, y, z);
                    if (isMineable(block)) {
                        block.breakNaturally();
                        task.incrementMinedBlocks();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isMineable(Block block) {
        Material type = block.getType();
        if (type.isAir()) {
            return false;
        }

        if (type == Material.BEDROCK || type == Material.WATER || type == Material.LAVA) {
            return false;
        }

        return true;
    }
}
