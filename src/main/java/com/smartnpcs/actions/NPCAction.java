package com.smartnpcs.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NPCAction {

    public static void execute(String action, Player player) {
        String[] parts = action.split(" ");
        switch (parts[0].toLowerCase()) {
            case "teleport":
                if (parts.length == 4) {
                    double x = Double.parseDouble(parts[1]);
                    double y = Double.parseDouble(parts[2]);
                    double z = Double.parseDouble(parts[3]);
                    player.teleport(player.getWorld().getBlockAt((int)x, (int)y, (int)z).getLocation());
                }
                break;

            case "command":
                String cmd = action.substring("command ".length()).replace("%player%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                break;

            case "message":
                player.sendMessage(action.substring("message ".length()));
                break;

            case "shop":
                player.sendMessage("§aShop feature coming soon!");
                break;

            case "quest":
                player.sendMessage("§aQuest feature coming soon!");
                break;
        }
    }
}