package com.smartnpcs.npc;

import com.smartnpcs.SmartNPCs;
import com.smartnpcs.gui.NPCEditorGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.*;

public class NPCManager implements CommandExecutor {

    private final SmartNPCs plugin;
    private final Map<UUID, NPCData> npcEntities = new HashMap<>();

    public NPCManager(SmartNPCs plugin) {
        this.plugin = plugin;
    }

    public void loadNPCs() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("npcs");
        if (section == null) return;

        for (String id : section.getKeys(false)) {
            String name = section.getString(id + ".name");
            List<String> dialogue = section.getStringList(id + ".dialogue");
            List<String> actions = section.getStringList(id + ".actions");
            String perm = section.getString(id + ".permission");

            double x = section.getDouble(id + ".location.x");
            double y = section.getDouble(id + ".location.y");
            double z = section.getDouble(id + ".location.z");
            String world = section.getString(id + ".location.world");

            Location loc = new Location(Bukkit.getWorld(world), x, y, z);

            spawnNPC(id, name, loc, dialogue, actions, perm);
        }
    }

    public void saveNPCs() {
        plugin.getConfig().set("npcs", null);
        for (NPCData npc : npcEntities.values()) {
            String path = "npcs." + npc.id;
            plugin.getConfig().set(path + ".name", npc.name);
            plugin.getConfig().set(path + ".dialogue", npc.dialogue);
            plugin.getConfig().set(path + ".actions", npc.actions);
            plugin.getConfig().set(path + ".permission", npc.permission);
            plugin.getConfig().set(path + ".location.x", npc.location.getX());
            plugin.getConfig().set(path + ".location.y", npc.location.getY());
            plugin.getConfig().set(path + ".location.z", npc.location.getZ());
            plugin.getConfig().set(path + ".location.world", npc.location.getWorld().getName());
        }
        plugin.saveConfig();
    }

    public void spawnNPC(String id, String name, Location loc, List<String> dialogue, List<String> actions, String permission) {
        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
        stand.setCustomNameVisible(true);
        stand.setCustomName(name);
        stand.setInvisible(true);
        stand.setInvulnerable(true);
        stand.setGravity(false);
        stand.setMarker(true);

        NPCData data = new NPCData(id, name, loc, dialogue, actions, permission);
        npcEntities.put(stand.getUniqueId(), data);
    }

    public NPCData getNPC(UUID uuid) {
        return npcEntities.get(uuid);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("smartnpcs.admin")) return true;

        if (args.length == 0) {
            player.sendMessage("§a/smartnpc create <id>");
            player.sendMessage("§a/smartnpc edit");
            return true;
        }

        if (args[0].equalsIgnoreCase("create") && args.length >= 2) {
            String id = args[1];
            spawnNPC(id, "§eNew NPC", player.getLocation(), List.of("Hello!"), List.of("none"), null);
            player.sendMessage("§aNPC created: " + id);
        }

        if (args[0].equalsIgnoreCase("edit")) {
            new NPCEditorGUI(player, this).open();
        }

        return true;
    }
}