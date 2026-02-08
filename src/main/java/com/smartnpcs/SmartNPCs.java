package com.smartnpcs;

import com.smartnpcs.listeners.NPCListener;
import com.smartnpcs.npc.NPCManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartNPCs extends JavaPlugin {

    private static SmartNPCs instance;
    private NPCManager npcManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        npcManager = new NPCManager(this);
        npcManager.loadNPCs();

        getServer().getPluginManager().registerEvents(new NPCListener(npcManager), this);
        getCommand("smartnpc").setExecutor(npcManager);
        getLogger().info("SmartNPCs enabled!");
    }

    @Override
    public void onDisable() {
        npcManager.saveNPCs();
    }

    public static SmartNPCs getInstance() {
        return instance;
    }
}