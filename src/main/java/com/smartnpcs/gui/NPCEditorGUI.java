package com.smartnpcs.gui;

import com.smartnpcs.npc.NPCManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class NPCEditorGUI {

    private final Player player;
    private final NPCManager manager;

    public NPCEditorGUI(Player player, NPCManager manager) {
        this.player = player;
        this.manager = manager;
    }

    public void open() {
        Inventory inv = Bukkit.createInventory(null, 27, "SmartNPC Editor");
        player.openInventory(inv);
    }
}