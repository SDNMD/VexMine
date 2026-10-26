package com.vexbot.vexmine.command;

import com.vexbot.vexmine.VexMineAddon;
import com.vexbot.vexmine.mine.MiningRegion;
import com.vexbot.vexmine.mine.MiningTask;
import com.vexbot.vexmine.mine.MiningTaskManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public final class VexBotCommand implements CommandExecutor {

    private final VexMineAddon plugin;
    private final MiningTaskManager taskManager;

    public VexBotCommand(VexMineAddon plugin, MiningTaskManager taskManager) {
        this.plugin = plugin;
        this.taskManager = taskManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /vexbot vexmine mine <team|individual> <x1> <y1> <z1> <x2> <y2> <z2>");
            return true;
        }

        if (!args[0].equalsIgnoreCase("vexmine")) {
            sender.sendMessage(ChatColor.RED + "Unknown VexBot subcommand. Try: /vexbot vexmine");
            return true;
        }

        return handleVexMine(sender, args);
    }

    private boolean handleVexMine(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /vexbot vexmine mine <team|individual> <x1> <y1> <z1> <x2> <y2> <z2>");
            return true;
        }

        if (!args[1].equalsIgnoreCase("mine")) {
            sender.sendMessage(ChatColor.RED + "Unknown vexmine subcommand. Use: mine");
            return true;
        }

        if (args.length != 9) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /vexbot vexmine mine <team|individual> <x1> <y1> <z1> <x2> <y2> <z2>");
            return true;
        }

        String mode = args[2].toLowerCase(Locale.ROOT);
        if (!mode.equals("team") && !mode.equals("individual")) {
            sender.sendMessage(ChatColor.RED + "Mode must be 'team' or 'individual'.");
            return true;
        }

        World world;
        if (sender instanceof Player player) {
            world = player.getWorld();
        } else {
            world = Bukkit.getWorlds().isEmpty() ? null : Bukkit.getWorlds().getFirst();
        }

        if (world == null) {
            sender.sendMessage(ChatColor.RED + "No world is loaded on this server.");
            return true;
        }

        try {
            int x1 = Integer.parseInt(args[3]);
            int y1 = Integer.parseInt(args[4]);
            int z1 = Integer.parseInt(args[5]);
            int x2 = Integer.parseInt(args[6]);
            int y2 = Integer.parseInt(args[7]);
            int z2 = Integer.parseInt(args[8]);

            MiningRegion region = new MiningRegion(world, x1, y1, z1, x2, y2, z2);
            MiningTask task = taskManager.createTask(region, mode, sender.getName());

            sender.sendMessage(ChatColor.GREEN + "Mining task created successfully. ID: " + task.getId() + " | Mode: " + mode + " | Region: " + region.toDisplayString());
            return true;
        } catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "All coordinates must be integers.");
            return true;
        }
    }
}
