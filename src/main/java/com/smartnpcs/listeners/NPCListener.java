package com.smartnpcs.listeners;

import com.smartnpcs.actions.NPCAction;
import com.smartnpcs.npc.NPCData;
import com.smartnpcs.npc.NPCManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class NPCListener implements Listener {

    private final NPCManager npcManager;

    public NPCListener(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        if (!(event.getRightClicked() instanceof ArmorStand stand)) return;

        NPCData npc = npcManager.getNPC(stand.getUniqueId());
        if (npc == null) return;

        Player player = event.getPlayer();

        if (npc.permission != null && !player.hasPermission(npc.permission)) {
            player.sendMessage("§cYou cannot use this NPC.");
            return;
        }

        // Dialogue
        if (!npc.dialogue.isEmpty()) {
            player.sendMessage("§e" + npc.name + "§f: " + npc.dialogue.get(0));
        }

        // Actions
        for (String action : npc.actions) {
            NPCAction.execute(action, player);
        }
    }
}